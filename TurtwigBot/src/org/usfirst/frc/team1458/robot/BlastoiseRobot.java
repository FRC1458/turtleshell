package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.core.AutoMode;
import com.team1458.turtleshell2.core.AutoModeHolder;
import com.team1458.turtleshell2.core.TestMode;
import com.team1458.turtleshell2.input.FlightStick;
import com.team1458.turtleshell2.input.XboxController;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.Logger;

import edu.wpi.cscore.HttpCamera;
import edu.wpi.cscore.HttpCamera.HttpCameraKind;
import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1458.robot.autonomous.TestAutonomous;
import org.usfirst.frc.team1458.robot.components.BlastoiseChassis;
import org.usfirst.frc.team1458.robot.components.BlastoiseTestBed;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;
import org.usfirst.frc.team1458.robot.vision.DetectTargetPipeline;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This is the base robot code.
 * @author asinghani
 */
public class BlastoiseRobot extends SampleRobot implements AutoModeHolder {

	// Robot Modes
	private ArrayList<BlastoiseAutoMode> autoModes = new ArrayList<>();
	private int selectedAutoMode = 0;
	private TestMode testMode;

	// Robot Components
	private BlastoiseChassis chassis;
	private BlastoiseTestBed testBed;
	
	// Misc
	private Logger logger;
	
	// Vision
	public Object lock = new Object();

	/**
	 * Constructor for robot
	 */
	public BlastoiseRobot() {
		logger = new Logger(RobotConstants.LOGGER_MODE);
		try {
			logger.attachServer(new Logger.ColoredLogServer(5802, "/log"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void robotInit() {
		// Setup controller and chassis
		TurtleNavX navX = TurtleNavX.getInstanceI2C();

		if(RobotConstants.USE_XBOX_CONTROLLER){
	        XboxController xboxController = new XboxController(RobotConstants.UsbPorts.XBOX_CONTROLLER);
	        chassis = new BlastoiseChassis(xboxController, navX, logger);
	        //testBed = new BlastoiseTestBed(logger, xboxController);

		} else {
			FlightStick leftStick = new FlightStick(RobotConstants.UsbPorts.LEFT_STICK);
	        FlightStick rightStick = new FlightStick(RobotConstants.UsbPorts.RIGHT_STICK);
	        chassis = new BlastoiseChassis(leftStick, rightStick, navX, logger);
	        //testBed = new BlastoiseTestBed(logger, leftStick, rightStick);
		}

		// Setup AutoMode
		autoModes.add(new TestAutonomous(chassis, logger, navX));

		selectedAutoMode = 0;

		// Setup TestMode
		testMode = () -> {}; // Creates a TestMode with empty test() function

		TurtleDashboard.setAutoModeHolder(this);
		TurtleDashboard.setup();
		
		// Vision
		//UsbCamera camera = new UsbCamera("camera0", 0);
		
		
		VideoSource camera = new HttpCamera("mjpegServer1",
				"http://localhost:5801/?action=stream",
				HttpCameraKind.kMJPGStreamer);
		camera.setResolution(480, 320);
		
		VisionThread visionThread = new VisionThread(camera, new DetectTargetPipeline(), pipeline -> {
	        if (!pipeline.filterContours0Output().isEmpty()) {
	            Rect r = Imgproc.boundingRect(pipeline.filterContours0Output().get(0));
	            synchronized (lock) {
	            	System.out.println(r.x);
	                double centerX = r.x + (r.width / 2.0);
	                SmartDashboard.putNumber("CENTER X", centerX);
	            }
	        }
	    });
	    visionThread.start();
	}

	@Override
	protected void disabled() {
		logger.info("Robot disabled");
		TurtleDashboard.disabled();
	}

	@Override
	public void autonomous() {
		logger.info("Entered autonomous control");
		TurtleDashboard.autonomous();
		
		AutoMode autoMode = autoModes.get(selectedAutoMode);
		
		if(autoMode == null) {
			logger.warn("Autonomous mode not implemented");
		} else {
			autoMode.auto();
		}
	}

	@Override
	public void operatorControl() {
		logger.info("Entered operator control");
		TurtleDashboard.teleop();

		while (isOperatorControl() && isEnabled()) {
			chassis.teleUpdate();
			//testBed.teleUpdate();
		}
	}

	@Override
	public void test() {
		logger.info("Entered test mode");
		TurtleDashboard.test();

		if(testMode == null) {
			logger.warn("Test mode not implemented");
		} else {
			testMode.test();
		}
	}

	public static boolean isPracticeRobot() {
		return RobotConstants.PRACTICE_ROBOT;
	}
	
	/**
	 * Get the list of auto modes
	 */
	public ArrayList<BlastoiseAutoMode> getAutoModes() {
		return autoModes;
	}

	/**
	 * Set the index of the selected auto mode
	 * @param index
	 */
	public void setSelectedAutoModeIndex(int index) {
		if(index < autoModes.size()){
			selectedAutoMode = index;
		}
	}

	/**
	 * Get index of selected AutoMode
	 */
	public int getSelectedAutoModeIndex() {
		return selectedAutoMode;
	}
}
