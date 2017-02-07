package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.core.AutoMode;
import com.team1458.turtleshell2.core.AutoModeHolder;
import com.team1458.turtleshell2.core.TestMode;
import com.team1458.turtleshell2.input.FlightStick;
import com.team1458.turtleshell2.input.XboxController;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1458.robot.autonomous.TestAutonomous;
import org.usfirst.frc.team1458.robot.components.BlastoiseChassis;
import org.usfirst.frc.team1458.robot.components.BlastoiseTestBed;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

import java.util.ArrayList;


/**
 * This is the base physical robot
 * @author asinghani
 */
public class BlastoiseRobot implements AutoModeHolder {

	// Robot Modes
	private ArrayList<BlastoiseAutoMode> autoModes = new ArrayList<>();
	private int selectedAutoMode = 0;
	private TestMode testMode;

	// Input
	private BlastoiseInputManager inputManager;

	// Misc
	private Logger logger;

	// Vision
	public Object lock = new Object();

	// Robot Components
	private BlastoiseChassis chassis;
	private BlastoiseTestBed testBed;

	// Sensors
	TurtleNavX navX = null;

	/**
	 * Constructor for robot
	 */
	public BlastoiseRobot(Logger logger) {
		this.logger = logger;
	}

	/**
	 * Single source of control for the entire robot
	 */
	protected void teleUpdate() {
		driveUpdate(); // Only run if not climbing, else stop motors
	}

	/**
	 * User-controlled
	 */
	private void driveUpdate() {
		MotorValue leftPower = new MotorValue(TurtleMaths.deadband(inputManager.getLeft(), RobotConstants.JOYSTICK_DEADBAND));
		MotorValue rightPower = new MotorValue(TurtleMaths.deadband(inputManager.getRight(), RobotConstants.JOYSTICK_DEADBAND));

		PIDConstants turnConstants = TurtleDashboard.getPidConstants("TurnPID");

		/**
		 * Left/Right turn with buttons
		 */
		if(inputManager.getRight90button().getUp()) {
			chassis.turn(new Angle(90), new MotorValue(0.7), turnConstants);
			return;//Having premature returns here is a bad idea
		}

		if(inputManager.getLeft90button().getUp()) {
			chassis.turn(new Angle(-90), new MotorValue(0.7), turnConstants);
			return;
		}

		/**
		 * Smoother control of the robot
		 */
		if (inputManager.getSlowButton().getButton()) {//Might want to move these into the next if statement, but it could be OK here
			leftPower = leftPower.halve();
			rightPower = rightPower.halve();
		} else if (RobotConstants.LOGISTIC_SCALE) {
			leftPower = new MotorValue(TurtleMaths.logisticStepScale(leftPower.getValue()));
			rightPower = new MotorValue(TurtleMaths.logisticStepScale(rightPower.getValue()));
		}

		// TODO ask drivers for input on this scheme - Only need one turn button
		if(inputManager.getStraightButton().getButton()){
			chassis.updateMotors(leftPower, leftPower);
		}
		else if(inputManager.getTurnLeftButton().getButton()) {
			chassis.updateMotors(leftPower.invert(), leftPower);
		}
		else if(inputManager.getTurnRightButton().getButton()) {
			chassis.updateMotors(rightPower, rightPower.invert());
		}
		else {
			chassis.updateMotors(leftPower, rightPower);
		}

		chassis.getDriveTrain().teleUpdate();

		SmartDashboard.putNumber("Yaw", navX.getYawAxis().getRotation().getDegrees());

		if(navX.isInCollision(RobotConstants.COLLISION_THRESHOLD)){
			inputManager.rumble(1.0f, 250);
		}
	}

	protected void robotInit() {
		navX = TurtleNavX.getInstanceI2C();

		if(RobotConstants.USE_XBOX_CONTROLLER){
	        XboxController xboxController = new XboxController(RobotConstants.UsbPorts.XBOX_CONTROLLER);
			inputManager = new BlastoiseInputManager(xboxController);
		} else {
			FlightStick leftStick = new FlightStick(RobotConstants.UsbPorts.LEFT_STICK);
	        FlightStick rightStick = new FlightStick(RobotConstants.UsbPorts.RIGHT_STICK);
			inputManager = new BlastoiseInputManager(leftStick, rightStick);
		}

		chassis = new BlastoiseChassis(inputManager, navX, logger);

		// Setup AutoMode
		autoModes.add(new TestAutonomous(chassis, logger, navX));

		selectedAutoMode = 0;

		// Setup TestMode
		testMode = () -> {};

		TurtleDashboard.setAutoModeHolder(this);

	}

	public void disabled() {

	}

	public void autonomous() {
		AutoMode autoMode = autoModes.get(selectedAutoMode);
		
		if(autoMode == null) {
			logger.warn("Autonomous mode not implemented");
		} else {
			autoMode.auto();
		}
	}

	public void operatorControl() {
		while (RobotState.isOperatorControl() && RobotState.isEnabled()) {
			teleUpdate();
		}
	}

	public void test() {
		if(testMode == null) {
			logger.warn("Test mode not implemented");
		} else {
			testMode.test();
		}
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
