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

import org.usfirst.frc.team1458.robot.autonomous.BlastoiseTestAutonomous;
import org.usfirst.frc.team1458.robot.autonomous.BlastoiseTestDistanceAutonomous;
import org.usfirst.frc.team1458.robot.autonomous.BlastoiseTestTimedAutonomous;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected void robotInit() {
		// Setup controller and chassis
		
		if(RobotConstants.USE_XBOX_CONTROLLER){
	        TurtleXboxController xboxController = new TurtleXboxController(RobotConstants.UsbPorts.XBOX_CONTROLLER);
	        chassis = new BlastoiseChassis(xboxController, logger);
	        testBed = new BlastoiseTestBed(logger, xboxController);
		} else {
			TurtleFlightStick leftStick = new TurtleFlightStick(RobotConstants.UsbPorts.LEFT_STICK);
	        TurtleFlightStick rightStick = new TurtleFlightStick(RobotConstants.UsbPorts.RIGHT_STICK);
	        chassis = new BlastoiseChassis(leftStick, rightStick, logger);
	        testBed = new BlastoiseTestBed(logger, leftStick, rightStick);
		}

		// Setup AutoModes
	    autoModes.add(new BlastoiseTestDistanceAutonomous(chassis, logger));
		autoModes.add(new BlastoiseTestTimedAutonomous(chassis, logger));
		autoModes.add(new BlastoiseTestAutonomous(chassis, logger, TurtleNavX.getInstanceUSB(RobotConstants.Sensors.NAVX_PORT)));

		selectedAutoMode = 2;

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
		//DigitalInput practiceRobot = new DigitalInput(RobotConstants.Sensors.PRACTICE_ROBOT_DIO);

		// Only trigger practice chassis when port PRACTICE_ROBOT_DIO is pulled to ground
		//return practiceRobot.get() == false;
		
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
