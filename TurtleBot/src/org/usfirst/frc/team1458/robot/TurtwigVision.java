package org.usfirst.frc.team1458.robot;

import java.util.ArrayList;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.team1458.turtleshell.TurtleVision;
import com.team1458.turtleshell.vision.Particle;
import com.team1458.turtleshell.vision.ScoreAnalyser;
import com.team1458.turtleshell.vision.Scores;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurtwigVision implements TurtleVision {
	int session;
	Image image;
	Image binaryFrame;
	int imaqError;

	// Constants
	NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(220, 14); // Default hue
	NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(249, 55); // Default
	NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(81, 255); // Default
	double AREA_MINIMUM = 0.5; // Default Area minimum for particle as a
	// percentage of total image area
	double LONG_RATIO = 2.22; // Tote long side = 26.9 / Tote height = 12.1 =
	// 2.22
	double SHORT_RATIO = 1.4; // Tote short side = 16.9 / Tote height = 12.1 =
	// 1.4
	double SCORE_MIN = 75.0; // Minimum score to be considered a tote
	double VIEW_ANGLE = 49.4; // View angle fo camera, set to Axis m1011 by
	// default, 64 for m1013, 51.7 for 206, 52 for
	// HD3000 square, 60 for HD3000 640x480
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0, 0, 1, 1);

	public void initRobot() {
		initCamera();
	}

	public void autonomous() {

	}

	public void operatorControl() {
		initCamera();
		testCamera();
		NIVision.IMAQdxStopAcquisition(session);
	}

	private void initCamera() {
		session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(session);

		// create images
		image = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
		binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
		criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA, AREA_MINIMUM,
				100.0, 0, 0);

		// Put default values to SmartDashboard so fields will appear
		SmartDashboard.putNumber("Tote hue min", TARGET_HUE_RANGE.minValue);
		SmartDashboard.putNumber("Tote hue max", TARGET_HUE_RANGE.maxValue);
		SmartDashboard.putNumber("Tote sat max", TARGET_SAT_RANGE.minValue);
		SmartDashboard.putNumber("Tote sat min", TARGET_SAT_RANGE.maxValue);
		SmartDashboard.putNumber("Tote val min", TARGET_VAL_RANGE.minValue);
		SmartDashboard.putNumber("Tote val max", TARGET_VAL_RANGE.maxValue);
		SmartDashboard.putNumber("Area min %", AREA_MINIMUM);
		SmartDashboard.putBoolean("use binary", false);
	}

	private void testCamera() {
		// SmartDashboard.putNumber("ImageAddress", image.getAddress());
		NIVision.IMAQdxGrab(session, image, 1);// problem righhere!!!!

		// Update threshold values from SmartDashboard. For performance reasons
		// it is recommended to remove this after calibration is finished.
		TARGET_HUE_RANGE.minValue = (int) SmartDashboard.getNumber("Tote hue min", TARGET_HUE_RANGE.minValue);
		TARGET_HUE_RANGE.maxValue = (int) SmartDashboard.getNumber("Tote hue max", TARGET_HUE_RANGE.maxValue);
		TARGET_SAT_RANGE.minValue = (int) SmartDashboard.getNumber("Tote sat min", TARGET_SAT_RANGE.minValue);
		TARGET_SAT_RANGE.maxValue = (int) SmartDashboard.getNumber("Tote sat max", TARGET_SAT_RANGE.maxValue);
		TARGET_VAL_RANGE.minValue = (int) SmartDashboard.getNumber("Tote val min", TARGET_VAL_RANGE.minValue);
		TARGET_VAL_RANGE.maxValue = (int) SmartDashboard.getNumber("Tote val max", TARGET_VAL_RANGE.maxValue);

		NIVision.imaqColorThreshold(binaryFrame, image, 255, NIVision.ColorMode.HSV, TARGET_HUE_RANGE, TARGET_SAT_RANGE,
				TARGET_VAL_RANGE);

		// Send particle count to dashboard
		int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Masked particles", numParticles);

		// Send masked image to dashboard to assist in tweaking mask.
		if (SmartDashboard.getBoolean("use binary")) {
			CameraServer.getInstance().setImage(binaryFrame);
		}

		SmartDashboard.putString("donuts", "101");

		// filter out small particles
		float areaMin = (float) SmartDashboard.getNumber("Area min %", AREA_MINIMUM);
		criteria[0].lower = areaMin;
		imaqError = NIVision.imaqParticleFilter4(binaryFrame, binaryFrame, criteria, filterOptions, null);

		// Send particle count after filtering to dashboard
		numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
		SmartDashboard.putNumber("Filtered particles", numParticles);

		if (numParticles > 0) {
			// Measure particles and sort by particle size
			ArrayList<Particle> particles = new ArrayList<Particle>();
			for (int particleIndex = 0; particleIndex < numParticles; particleIndex++) {
				Particle par = new Particle(binaryFrame, particleIndex);
				particles.add(par);
			}
			particles.sort(null);

			Particle particle = particles.get(0);

			Scores scores = new Scores(particle);
			SmartDashboard.putNumber("AreaScore", scores.areaToConvexArea);
			SmartDashboard.putNumber("PerimeterScore", scores.perimeterToConvexPerimeter);
			SmartDashboard.putNumber("PlenimeterScore", scores.plenimeter);
			SmartDashboard.putNumber("RectanglinessScore", scores.rectangliness);
			SmartDashboard.putBoolean("IsAcceptable", ScoreAnalyser.isAcceptable(scores));

			// NIVision.Rect rect = new NIVision.Rect(particle.left,
			// particle.top, particle.right - particle.left, particle.bottom -
			// particle.top);

			// NIVision.imaqDrawShapeOnImage(image, image, rect,
			// DrawMode.DRAW_VALUE, ShapeMode.SHAPE_RECT, 0.0f);
		}

		if (!SmartDashboard.getBoolean("use binary")) {
			CameraServer.getInstance().setImage(image);
		}
	}
}
