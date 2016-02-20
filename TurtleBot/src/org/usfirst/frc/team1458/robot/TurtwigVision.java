package org.usfirst.frc.team1458.robot;

import java.util.ArrayList;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.sensor.TurtleDistance;
import com.team1458.turtleshell.sensor.TurtleTheta;
import com.team1458.turtleshell.sensor.TurtleVision;
import com.team1458.turtleshell.util.Output;
import com.team1458.turtleshell.util.TurtleSafeDriverStation;
import com.team1458.turtleshell.vision.Particle;
import com.team1458.turtleshell.vision.ScoreAnalyser;
import com.team1458.turtleshell.vision.Scores;
import com.team1458.turtleshell.vision.TurtleCameraServer;
import com.team1458.turtleshell.vision.VisionMaths;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurtwigVision implements TurtleVision, TurtleTheta, TurtleDistance {
	private static TurtwigVision instance;

	public static TurtwigVision getInstance() {
		if (instance == null) {
			instance = new TurtwigVision();
		}
		return instance;
	}

	private int session;
	private Image image;
	private Image binaryFrame;

	private Image sendImage;

	private int imaqError;

	// Constants
	private NIVision.Range TARGET_HUE_RANGE = new NIVision.Range(220, 14); // Default
	// hue
	NIVision.Range TARGET_SAT_RANGE = new NIVision.Range(249, 55); // Default
	NIVision.Range TARGET_VAL_RANGE = new NIVision.Range(81, 255); // Default
	double AREA_MINIMUM = 0.5; // Default Area minimum for particle as a
	// percentage of total image area
	NIVision.ParticleFilterCriteria2 criteria[] = new NIVision.ParticleFilterCriteria2[1];
	NIVision.ParticleFilterOptions2 filterOptions = new NIVision.ParticleFilterOptions2(0, 0, 1, 1);

	private double distance;
	private double angle;
	private boolean targetRecognised;

	private TurtwigVision() {
		initCamera();
	}

	private void initCamera() {
		try {
			session = NIVision.IMAQdxOpenCamera("cam0", NIVision.IMAQdxCameraControlMode.CameraControlModeController);
			NIVision.IMAQdxConfigureGrab(session);

			// create images
			image = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
			binaryFrame = NIVision.imaqCreateImage(ImageType.IMAGE_U8, 0);
			criteria[0] = new NIVision.ParticleFilterCriteria2(NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA,
					AREA_MINIMUM, 100.0, 0, 0);

			// Put default values to SmartDashboard so fields will appear
			SmartDashboard.putNumber("Hue min", TARGET_HUE_RANGE.minValue);
			SmartDashboard.putNumber("Hue max", TARGET_HUE_RANGE.maxValue);
			SmartDashboard.putNumber("Sat max", TARGET_SAT_RANGE.minValue);
			SmartDashboard.putNumber("Sat min", TARGET_SAT_RANGE.maxValue);
			SmartDashboard.putNumber("Val min", TARGET_VAL_RANGE.minValue);
			SmartDashboard.putNumber("Val max", TARGET_VAL_RANGE.maxValue);
			SmartDashboard.putNumber("Area min %", AREA_MINIMUM);
			SmartDashboard.putBoolean("use binary", false);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void update() {
		// SmartDashboard.putNumber("ImageAddress", image.getAddress());
		NIVision.IMAQdxGrab(session, image, 1);// problem righhere!!!!

		if (!SmartDashboard.getBoolean("use binary")) {
			sendImage = image;
			TurtleCameraServer.getInstance().setImage(image);
		}
		if (TurtleSafeDriverStation.canAuto()) {
			// Update threshold values from SmartDashboard. For performance
			// reasons
			// it is recommended to remove this after calibration is finished.
			TARGET_HUE_RANGE.minValue = (int) SmartDashboard.getNumber("Tote hue min", TARGET_HUE_RANGE.minValue);
			TARGET_HUE_RANGE.maxValue = (int) SmartDashboard.getNumber("Tote hue max", TARGET_HUE_RANGE.maxValue);
			TARGET_SAT_RANGE.minValue = (int) SmartDashboard.getNumber("Tote sat min", TARGET_SAT_RANGE.minValue);
			TARGET_SAT_RANGE.maxValue = (int) SmartDashboard.getNumber("Tote sat max", TARGET_SAT_RANGE.maxValue);
			TARGET_VAL_RANGE.minValue = (int) SmartDashboard.getNumber("Tote val min", TARGET_VAL_RANGE.minValue);
			TARGET_VAL_RANGE.maxValue = (int) SmartDashboard.getNumber("Tote val max", TARGET_VAL_RANGE.maxValue);

			NIVision.imaqColorThreshold(binaryFrame, image, 255, NIVision.ColorMode.HSV, TARGET_HUE_RANGE,
					TARGET_SAT_RANGE, TARGET_VAL_RANGE);
			if (SmartDashboard.getBoolean("use binary")) {
				sendImage = binaryFrame;
				TurtleCameraServer.getInstance().setImage(binaryFrame);
			}
			// Send particle count to dashboard
			int numParticles = NIVision.imaqCountParticles(binaryFrame, 1);
			SmartDashboard.putNumber("Masked particles", numParticles);
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
				targetRecognised = ScoreAnalyser.isAcceptable(scores);
				if (targetRecognised) {
					distance = (89 - TurtwigConstants.cameraHeight)
							* (Math.sin(
									Math.PI - (VisionMaths.yToTheta(particle.yCentre) - TurtwigConstants.cameraAngle)))
							/ (Math.sin((VisionMaths.yToTheta(particle.yCentre) - TurtwigConstants.cameraAngle)));
					angle = VisionMaths.xToTheta(particle.xCentre);
				}

			} else {
				TurtleLogger.verbose("No vision target found");
			}

			Output.outputNumber("Vision Distance", this.getDistance());
		}

	}

	@Override
	public double getDistance() {
		return distance;
	}

	@Override
	public double getContinousTheta() {
		return angle;
	}

	@Override
	public double getRate() {
		return 0;
	}

	@Override
	public void reset() {
		// Not applicable
	}

	@Override
	public boolean targetRecognised() {
		return targetRecognised;
	}

	@Override
	public Image getImage() {
		return sendImage;
	}
}
