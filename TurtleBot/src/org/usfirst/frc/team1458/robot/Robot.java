package org.usfirst.frc.team1458.robot;

import java.util.ArrayList;
import java.util.Vector;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.DrawMode;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.NIVision.ShapeMode;
import com.team1458.turtleshell.Output;
import com.team1458.turtleshell.Particle;
import com.team1458.turtleshell.Scores;
import com.team1458.turtleshell.TurtleRobot;
import com.team1458.turtleshell.TurtleSafeDriverStation;
import com.team1458.turtleshell.TurtleTheta;
import com.team1458.turtleshell.physical.TurtleXtrinsicMagnetometer;
import com.team1458.turtleshell.physical.TurtleXtrinsicMagnetometerCalibration;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Utility;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TurtleRobot {
	TurtleTheta maggie;
	
	int session;
    Image image;
    Image binaryFrame;
    int imaqError;

	//Constants
	NIVision.Range TOTE_HUE_RANGE = new NIVision.Range(101, 64);	//Default hue range for yellow tote
	NIVision.Range TOTE_SAT_RANGE = new NIVision.Range(88, 255);	//Default saturation range for yellow tote
	NIVision.Range TOTE_VAL_RANGE = new NIVision.Range(134, 255);	//Default value range for yellow tote
	double AREA_MINIMUM = 0.5; //Default Area minimum for particle as a percentage of total image area
	double LONG_RATIO = 2.22; //Tote long side = 26.9 / Tote height = 12.1 = 2.22
	double SHORT_RATIO = 1.4; //Tote short side = 16.9 / Tote height = 12.1 = 1.4
	double SCORE_MIN = 75.0;  //Minimum score to be considered a tote
	double VIEW_ANGLE = 49.4; //View angle fo camera, set to Axis m1011 by default, 64 for m1013, 51.7 for 206, 52 for HD3000 square, 60 for HD3000 640x480
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0,0,1,1);  
	
	public Robot() {
		
	}   

	public void initRobot() {
		initCamera();
		physicalRobot.addComponent("Chassis", new TurtwigSmartTankChassis());
	}

	public void autonomous() {
		// Put the code to initialise autonomous here.
		//auto = new TurtwigTestAutonomous();
		//auto.giveRobot(physicalRobot);
		//auto.doAuto();
	}

	public void operatorControl() {
		// Put the code to initialise operator control here.
		maggie = new TurtleXtrinsicMagnetometer(I2C.Port.kOnboard);
		maggie.setCalibration(new TurtleXtrinsicMagnetometerCalibration(-1614, -874, 763, 1649));
		tele = new TurtwigTestTeleop();
		tele.giveRobot(physicalRobot);

		while (TurtleSafeDriverStation.canTele()) {
			physicalRobot.teleUpdateAll();
			//tele.tick();
			maggie.update();
			if(Utility.getUserButton()) {
				maggie.setCalibration(maggie.generateCalibration());
			}
			Output.outputNumber("MagAngle", maggie.getContinousTheta());
			Output.outputNumber("MagRate", maggie.getRate());
			testCamera();
		}
        NIVision.IMAQdxStopAcquisition(session);
	}
	
	private void initCamera() {
		session = NIVision.IMAQdxOpenCamera("cam0",
                NIVision.IMAQdxCameraControlMode.CameraControlModeController);
        NIVision.IMAQdxConfigureGrab(session);
        
		 // create images
		image = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM, 100.0, 0, 0);

		//Put default values to SmartDashboard so fields will appear
		SmartDashboard.putNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
		SmartDashboard.putNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
		SmartDashboard.putNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
		SmartDashboard.putNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
		SmartDashboard.putNumber("Tote val min", TOTE_VAL_RANGE.minValue);
		SmartDashboard.putNumber("Tote val max", TOTE_VAL_RANGE.maxValue);
		SmartDashboard.putNumber("Area min %", AREA_MINIMUM);
	}
	
	private void testCamera() {
		NIVision.IMAQdxGrab(session, image, 1);

		//Update threshold values from SmartDashboard. For performance reasons it is recommended to remove this after calibration is finished.
		TOTE_HUE_RANGE.minValue = (int)SmartDashboard.getNumber("Tote hue min", TOTE_HUE_RANGE.minValue);
		TOTE_HUE_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote hue max", TOTE_HUE_RANGE.maxValue);
		TOTE_SAT_RANGE.minValue = (int)SmartDashboard.getNumber("Tote sat min", TOTE_SAT_RANGE.minValue);
		TOTE_SAT_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote sat max", TOTE_SAT_RANGE.maxValue);
		TOTE_VAL_RANGE.minValue = (int)SmartDashboard.getNumber("Tote val min", TOTE_VAL_RANGE.minValue);
		TOTE_VAL_RANGE.maxValue = (int)SmartDashboard.getNumber("Tote val max", TOTE_VAL_RANGE.maxValue);

		//Threshold the image looking for yellow (tote color)
		NIVision.imaqColorThreshold(binaryFrame, image, 255, NIVision.ColorMode.HSV, TOTE_HUE_RANGE, TOTE_SAT_RANGE, TOTE_VAL_RANGE);

		//Send particle count to dashboard
		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Masked particles", numParticles);

		//Send masked image to dashboard to assist in tweaking mask.
		CameraServer.getInstance().setImage(binaryFrame);

		//filter out small particles
		float areaMin = (float)SmartDashboard.getNumber("Area min %", AREA_MINIMUM);
		criteria[0].lower = areaMin;
		imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);

		//Send particle count after filtering to dashboard
		numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Filtered particles", numParticles);
		
		if(numParticles > 0)
		{
			//Measure particles and sort by particle size
			ArrayList<Particle> particles = new ArrayList<Particle>();
			for(int particleIndex = 0; particleIndex < numParticles; particleIndex++)
			{
			    double percentArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
				double area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
				double convexArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
				double top = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP);
				double left = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT);
				double bottom = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM);
				double right = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT);
				Particle par = new Particle(percentArea, area, convexArea, top, left, right, bottom);
				particles.add(par);
			}
			particles.sort(null);
			
			double trapezoid = TrapezoidScore(particles.get(0));
			SmartDashboard.putNumber("Trapezoid", trapezoid);
			double longAspect = LongSideScore(particles.get(0));
			SmartDashboard.putNumber("Long Aspect", longAspect);
			double shortAspect = ShortSideScore(particles.get(0));
			SmartDashboard.putNumber("Short Aspect", shortAspect);
			double areaToConvexArea = ConvexHullAreaScore(particles.get(0));
			Scores scores = new Scores(trapezoid, longAspect, shortAspect, areaToConvexArea);
		}
	}

	public void test() {
		// Code for testing mode
	}
	double ConvexHullAreaScore(Particle report)
	{
		return ratioToScore((report.area/report.convexArea)*1.18);
	}

	/**
	 * Method to score if the particle appears to be a trapezoid. Compares the convex hull (filled in) area to the area of the bounding box.
	 * The expectation is that the convex hull area is about 95.4% of the bounding box area for an ideal tote.
	 */
	double TrapezoidScore(Particle report)
	{
		return ratioToScore(report.convexArea/((report.right-report.left)*(report.bottom-report.top)*.954));
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the long side of a tote.
	 */
	double LongSideScore(Particle report)
	{
		return ratioToScore(((report.right-report.left)/(report.bottom-report.top))/LONG_RATIO);
	}

	/**
	 * Method to score if the aspect ratio of the particle appears to match the short side of a tote.
	 */
	double ShortSideScore(Particle report){
		return ratioToScore(((report.right-report.left)/(report.bottom-report.top))/SHORT_RATIO);
	}
	double ratioToScore(double ratio){
		return (Math.max(0, Math.min(100*(1-Math.abs(1-ratio)), 100)));
	}
}
