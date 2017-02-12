package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.core.AutoMode;
import com.team1458.turtleshell2.core.AutoModeHolder;
import com.team1458.turtleshell2.core.TestMode;
import com.team1458.turtleshell2.input.FlightStick;
import com.team1458.turtleshell2.input.XboxController;
import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.sensor.TurtleLIDARLite;
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
import org.usfirst.frc.team1458.robot.components.BlastoiseClimber;
import org.usfirst.frc.team1458.robot.components.BlastoiseIntake;
import org.usfirst.frc.team1458.robot.components.BlastoiseShooter;
import org.usfirst.frc.team1458.robot.constants.Constants;
import org.usfirst.frc.team1458.robot.constants.OldConstants;
import org.usfirst.frc.team1458.robot.vision.BlastoiseVision;

import java.util.ArrayList;

/**
 * This is the base robot
 * @author asinghani
 */
public class BlastoiseRobot implements AutoModeHolder {

	/**
	 * Sensors
	 */
	TurtleNavX navX = null;
	TurtleLIDARLite lidar = null;

	/**
	 * Input
	 */
	private BlastoiseInputManager inputManager;

	/**
	 * Robot Actuators
	 */
	private BlastoiseChassis chassis;

	private BlastoiseClimber climber;
	private BlastoiseIntake intake;

	private BlastoiseShooter shooterLeft;
	private BlastoiseShooter shooterRight;


	/**
	 * Vision
	 */
	BlastoiseVision vision = new BlastoiseVision(OldConstants.Shooter.Camera.CAMERA_URL);
	PID turnPid = new PID(OldConstants.Shooter.TurnPID.PID_CONSTANTS, 165, 0);

	private Logger logger;


	/**
	 * ----------------------- START INITIALIZATION CODE -----------------------
	 */

	public BlastoiseRobot(Logger logger) {
		this.logger = logger;
	}

	protected void robotInit() {
		// Setup Sensors
		setupSensors();

		// Setup Input
		setupInput();

		// Setup Robot Actuators
		setupActuators();

		// Setup UI Code
		setupUI();

		// Setup Robot Modes
		setupRobotModes();


		// Debug & Configuration Code
		if(Constants.DEBUG) {
			TurtleDashboard.enablePidTuning(Constants.ShooterVision.VisionPID.PID_CONSTANTS, "ShooterVisionPID");
			SmartDashboard.putNumber("ShooterVisionTurnSpeed", 0.5);

			TurtleDashboard.enablePidTuning(Constants.LeftShooter.PID_CONSTANTS, "LeftShooterPID");
			SmartDashboard.putNumber("LeftShooterSpeed", Constants.LeftShooter.SPEED_RPM);

			TurtleDashboard.enablePidTuning(Constants.RightShooter.PID_CONSTANTS, "RightShooterPID");
			SmartDashboard.putNumber("RightShooterSpeed", Constants.RightShooter.SPEED_RPM);
		}
	}

	private void setupSensors() {
		navX = TurtleNavX.getInstanceI2C();
		lidar = TurtleLIDARLite.getInstanceMXP();
	}

	private void setupInput() {
		BlastoiseController controller = new BlastoiseController();

		if(OldConstants.USE_XBOX_CONTROLLER){
			XboxController xboxController = new XboxController(OldConstants.UsbPorts.XBOX_CONTROLLER);
			inputManager = new BlastoiseInputManager(xboxController, controller);
		} else {
			FlightStick leftStick = new FlightStick(OldConstants.UsbPorts.LEFT_STICK);
			FlightStick rightStick = new FlightStick(OldConstants.UsbPorts.RIGHT_STICK);
			inputManager = new BlastoiseInputManager(leftStick, rightStick, controller);
		}
	}

	private void setupActuators() {
		chassis = new BlastoiseChassis(navX, logger);

		climber = new BlastoiseClimber(0, Constants.Climber.SPEED, Constants.Climber.SPEED_LOW);
		intake = new BlastoiseIntake(0, Constants.Intake.SPEED);

		shooterLeft = new BlastoiseShooter(Constants.LeftShooter.MOTOR_PORT, Constants.LeftShooter.HALL_PORT,
				Constants.LeftShooter.PID_CONSTANTS, Constants.LeftShooter.SPEED_RPM);

		shooterRight = new BlastoiseShooter(Constants.RightShooter.MOTOR_PORT, Constants.RightShooter.HALL_PORT,
				Constants.RightShooter.PID_CONSTANTS, Constants.RightShooter.SPEED_RPM);
	}

	private void setupUI() {
		TurtleDashboard.setAutoModeHolder(this);
		TurtleDashboard.logAxis(inputManager.rightJoystick, inputManager.rightJoystick);
	}

	/**
	 * ----------------------- END INITIALIZATION CODE -----------------------
	 */

	/**
	 * ----------------------- START TELEOP CODE -----------------------
	 */

	/**
	 * Single source of control for the entire robot
	 */
	protected void teleUpdate() {
		/**
		 * Things that are done here:
		 *
		 * 1. Start climbing if switch is active
		 * 2. If robot is climbing, do nothing else
		 * 3. Update intake movement based on switch
		 * 4. Update shooter actions based on switch
		 * 5. Drive Code:
		 * 	a. If holding down shooter align button, use PID loop to align with high goal
		 * 	b. If holding down gear align button, use PID loop to align while also driving forward
		 * 	c. Else drive normal teleop mode
		 */

		/**
		 * Start climbing if switch is active
		 */
		climberUpdate();

		/**
		 * If robot is climbing, do nothing else
		 */
		if(climber.isClimbing()) {
			return;
		}

		/**
		 * Update intake movement based on switch
		 */
		intakeUpdate();

		/**
		 * Update shooter actions based on switch
		 */
		shooterUpdate();


		/**
		 * Drive Code
		 */
		driveUpdate();

		/**
		 * Extra Info
		 */
		SmartDashboard.putNumber("Yaw", navX.getYawAxis().getRotation().getDegrees());

		double targetX = vision.getShooterTargetX();

		SmartDashboard.putBoolean("On Target", TurtleMaths.absDiff(targetX, 165) < 30);
	}

	private void climberUpdate() {
		if(inputManager.climberSwitch.getDown()) {
			climber.start();

			intake.stop();
			shooterRight.stop();
			shooterLeft.stop();
			chassis.stopMotors();
		} else if(inputManager.climberSwitch.getUp()) {
			climber.stop();
		}
	}

	private void intakeUpdate() {
		if(inputManager.intakeSwitch.get() == 1) {
			intake.start();
		} else if(inputManager.intakeSwitch.get() == 2) {
			intake.startReverse();
		} else {
			intake.stop();
		}
	}

	private void shooterUpdate() {
		if(inputManager.shootButton.getButton()) {

			if(inputManager.shootButton.getDown()) {
				if(Constants.DEBUG) {
					shooterLeft.setPIDConstants(TurtleDashboard.getPidConstants("LeftShooterPID"));
					shooterLeft.setSpeedTarget(SmartDashboard.getNumber("LeftShooterSpeed", 0));

					shooterRight.setPIDConstants(TurtleDashboard.getPidConstants("RightShooterPID"));
					shooterRight.setSpeedTarget(SmartDashboard.getNumber("RightShooterSpeed", 0));
				}
				shooterLeft.start();
				shooterRight.start();

			} else if(inputManager.shootButton.getUp()) {
				shooterLeft.stop();
				shooterRight.stop();
			}

			shooterLeft.teleUpdate();
			shooterRight.teleUpdate();

			SmartDashboard.putNumber("Shooter Left RPM", shooterLeft.getSpeed());
			SmartDashboard.putNumber("Shooter Right RPM", shooterRight.getSpeed());
			SmartDashboard.putBoolean("Shooting", true);

		} else {
			SmartDashboard.putNumber("Shooter Left RPM", 0);
			SmartDashboard.putNumber("Shooter Right RPM", 0);
			SmartDashboard.putBoolean("Shooting", false);
		}
	}

	/**
	 * Shooter alignment. Only left/right for now.
	 */
	private void shooterAlignUpdate() {
		if(inputManager.alignShooterButton.getDown()) {
			turnPid = new PID(TurtleDashboard.getPidConstants("ShooterTurnPID"), 165, 0);
		}
		double targetX = vision.getShooterTargetX();

		if(targetX < 0){
			return;
		}

		MotorValue motorValue =
				new MotorValue(turnPid.newValue(targetX))
						.mapToSpeed(new MotorValue(SmartDashboard.getNumber("ShooterTurnSpeed", 0.25)));

		chassis.updateMotors(motorValue.invert(), motorValue);

		SmartDashboard.putNumber("Shooter_TargetX", vision.getShooterTargetX());
		SmartDashboard.putNumber("Shooter_MotorValue", motorValue.getValue());
	}

	/**
	 * User-controlled
	 */
	private void driveUpdate() {



		/*if (inputManager.alignShooterButton.getButton()){
			shooterAlignUpdate();
		} else {
			driveUpdate();
		}*/

		/**
		 * If holding down shooter align button, use PID loop to align with high goal
 		 */
		if(inputManager.alignShooterButton.getButton()) {
			shooterAlignUpdate();
			return;
		}

		MotorValue leftPower = new MotorValue(TurtleMaths.deadband(inputManager.getLeft(), OldConstants.JOYSTICK_DEADBAND));
		MotorValue rightPower = new MotorValue(TurtleMaths.deadband(inputManager.getRight(), OldConstants.JOYSTICK_DEADBAND));

		PIDConstants turnConstants = TurtleDashboard.getPidConstants("TurnPID");

		/**
		 * Left/Right turn with buttons
		 */
		if(inputManager.right90button.getUp()) {
			chassis.turn(new Angle(90), new MotorValue(0.7), turnConstants);
			return;
		}

		if(inputManager.left90button.getUp()) {
			chassis.turn(new Angle(-90), new MotorValue(0.7), turnConstants);
			return;
		}

		/**
		 * Smoother control of the robot
		 */
		if (inputManager.slowButton.getButton()) {
			leftPower = leftPower.halve();
			rightPower = rightPower.halve();
		} else if (OldConstants.LOGISTIC_SCALE) {
			leftPower = new MotorValue(TurtleMaths.logisticStepScale(leftPower.getValue()));
			rightPower = new MotorValue(TurtleMaths.logisticStepScale(rightPower.getValue()));
		}

		// TODO ask drivers for input on this scheme - Only need one turn button
		if(inputManager.straightButton.getButton()){
			chassis.updateMotors(leftPower, leftPower);
		}
		else if(inputManager.turnLeftButton.getButton()) {
			chassis.updateMotors(leftPower.invert(), leftPower);
		}
		else if(inputManager.turnRightButton.getButton()) {
			chassis.updateMotors(rightPower, rightPower.invert());
		}
		else {
			chassis.updateMotors(leftPower, rightPower);
		}

		chassis.getDriveTrain().teleUpdate();

		SmartDashboard.putNumber("Yaw", navX.getYawAxis().getRotation().getDegrees());
	}

	/**
	 * ----------------------- END TELEOP CODE -----------------------
	 */


	/**
	 * ----------------------- START ADDITIONAL CODE -----------------------
	 *
	 * Below methods are mostly wrappers and should NOT be edited or removed
	 */

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
	 * Handles Different Robot Modes (Auto & Test)
	 */
	private ArrayList<BlastoiseAutoMode> autoModes = new ArrayList<>();
	private int selectedAutoMode = 0;
	private TestMode testMode;

	private void setupRobotModes() {
		autoModes.add(new TestAutonomous(chassis, logger, navX));
		selectedAutoMode = 0;
		testMode = () -> {};
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

	/**
	 * ----------------------- END ADDITIONAL CODE -----------------------
	 */
}
