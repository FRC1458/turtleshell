package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.sensor.TurtleNavX;
import com.team1458.turtleshell2.interfaces.AutoMode;
import com.team1458.turtleshell2.interfaces.AutoModeHolder;
import com.team1458.turtleshell2.interfaces.TestMode;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleLogger;

import edu.wpi.first.wpilibj.SampleRobot;

import org.usfirst.frc.team1458.robot.autonomous.TestAutonomous;
import org.usfirst.frc.team1458.robot.components.BlastoiseChassis;
import org.usfirst.frc.team1458.robot.components.BlastoiseTestBed;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

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
	private TurtleLogger logger;

	/**
	 * Constructor for robot
	 */
	public BlastoiseRobot() {
		logger = new TurtleLogger(RobotConstants.LOGGER_MODE);
		try {
			logger.attachServer(new TurtleLogger.ColoredLogServer(5802, "/log"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void robotInit() {
		// Setup controller and chassis
		TurtleNavX navX = TurtleNavX.getInstanceI2C();

		if(RobotConstants.USE_XBOX_CONTROLLER){
	        TurtleXboxController xboxController = new TurtleXboxController(RobotConstants.UsbPorts.XBOX_CONTROLLER);
	        chassis = new BlastoiseChassis(xboxController, navX, logger);
	        testBed = new BlastoiseTestBed(logger, xboxController);

		} else {
			TurtleFlightStick leftStick = new TurtleFlightStick(RobotConstants.UsbPorts.LEFT_STICK);
	        TurtleFlightStick rightStick = new TurtleFlightStick(RobotConstants.UsbPorts.RIGHT_STICK);
	        chassis = new BlastoiseChassis(leftStick, rightStick, navX, logger);
	        testBed = new BlastoiseTestBed(logger, leftStick, rightStick);
		}

		// Setup AutoMode
		autoModes.add(new TestAutonomous(chassis, logger, navX));

		selectedAutoMode = 0;

		// Setup TestMode
		testMode = () -> {}; // Creates a TestMode with empty test() function

		TurtleDashboard.setAutoModeHolder(this);
		TurtleDashboard.setup();
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
			testBed.teleUpdate();
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
