package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.core.AutoMode;
import com.team1458.turtleshell2.core.AutoModeHolder;
import com.team1458.turtleshell2.core.TestMode;
import com.team1458.turtleshell2.sensor.TurtleDistanceEncoder;
import com.team1458.turtleshell2.input.FlightStick;
import com.team1458.turtleshell2.input.XboxController;
import com.team1458.turtleshell2.movement.FollowerMotorSet;
import com.team1458.turtleshell2.movement.TankDrive;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.sensor.LIDARSerial;
import com.team1458.turtleshell2.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1458.robot.autonomous.TestAutonomous;
import org.usfirst.frc.team1458.robot.components.BlastoiseClimber;
import org.usfirst.frc.team1458.robot.components.BlastoiseIntake;
import org.usfirst.frc.team1458.robot.components.BlastoiseShooter;
import org.usfirst.frc.team1458.robot.vision.BlastoiseShooterVision;

import java.util.ArrayList;

/**
 * This is the base robot
 * 
 * @author asinghani
 */
public class Robot extends SampleRobot implements AutoModeHolder {

	// Sensors
	private TurtleNavX navX = null;
	private TurtleDistanceSensor lidar = null;

	// Input
	private BlastoiseInputManager inputManager;

	// Robot Actuators
	private TankDrive chassis;

	private BlastoiseClimber climber;
	private BlastoiseIntake intake;

	private BlastoiseShooter shooterLeft;
	private BlastoiseShooter shooterRight;

	private BlastoiseFluxStore store;

	private int last = -10;

	// Vision
	private BlastoiseShooterVision vision = new BlastoiseShooterVision(
			Constants.ShooterVision.Camera.URL);
	private PID turnPid = new PID(
			Constants.ShooterVision.VisionPID.PID_CONSTANTS, 165, 0);

	private Logger logger;

	public Robot() {
		this.logger = new Logger(Logger.LogFormat.PLAINTEXT);
		store = BlastoiseFluxStore.getInstance();
	}

	protected void robotInit() {
		setupSensors();
		setupInput();
		setupActuators();
		setupUI();
		setupRobotModes();

		// Debug & Configuration Code
		if (Constants.DEBUG) {
			TurtleDashboard.enablePidTuning(
					Constants.ShooterVision.VisionPID.PID_CONSTANTS,
					"ShooterVisionPID");
			SmartDashboard.putNumber("ShooterVisionTurnSpeed",
					Constants.ShooterVision.VisionPID.SPEED.getValue());

			TurtleDashboard.enablePidTuning(
					Constants.LeftShooter.PID_CONSTANTS, "LeftShooterPID");
			SmartDashboard.putNumber("LeftShooterSpeed",
					Constants.LeftShooter.SPEED_RPM);

			TurtleDashboard.enablePidTuning(
					Constants.RightShooter.PID_CONSTANTS, "RightShooterPID");
			SmartDashboard.putNumber("RightShooterSpeed",
					Constants.RightShooter.SPEED_RPM);
			SmartDashboard.putNumber("RightShooterOpenLoop", 0.7);
		}
	}

	private void setupAutoModes() {
		autoModes.add(new TestAutonomous(chassis, logger, navX));
		selectedAutoMode = 0;
	}

	private void setupSensors() {
		navX = new TurtleNavX(SerialPort.Port.kUSB);
		lidar = new LIDARSerial(SerialPort.Port.kMXP);
	}

	private void setupInput() {
		BlastoiseController controller = new BlastoiseController(5);
		XboxController xController = new XboxController(
				Constants.DriverStation.UsbPorts.DEBUG_XBOX_CONTROLLER);

		if (Constants.DriverStation.USE_XBOX_CONTROLLER) {
			XboxController xboxController = new XboxController(
					Constants.DriverStation.UsbPorts.XBOX_CONTROLLER);
			inputManager = new BlastoiseInputManager(xboxController,
					xController);
		} else {
			FlightStick leftStick = new FlightStick(
					Constants.DriverStation.UsbPorts.LEFT_STICK);
			FlightStick rightStick = new FlightStick(
					Constants.DriverStation.UsbPorts.RIGHT_STICK);
			inputManager = new BlastoiseInputManager(leftStick, rightStick,
					xController);
		}
	}

	private void setupActuators() {
		chassis = new TankDrive(new FollowerMotorSet(new TurtleTalonSRXCAN(
				Constants.LeftDrive.MOTOR1), new TurtleTalonSRXCAN(
				Constants.LeftDrive.MOTOR2), new TurtleTalonSRXCAN(
				Constants.LeftDrive.MOTOR3)), new FollowerMotorSet(
				new TurtleTalonSRXCAN(Constants.RightDrive.MOTOR1, true),
				new TurtleTalonSRXCAN(Constants.RightDrive.MOTOR2, true),
				new TurtleTalonSRXCAN(Constants.RightDrive.MOTOR3, true)),
				new TurtleDistanceEncoder(Constants.LeftDrive.ENCODER_A,
						Constants.LeftDrive.ENCODER_B,
						Constants.LeftDrive.ENCODER_RATIO),
				new TurtleDistanceEncoder(Constants.RightDrive.ENCODER_A,
						Constants.RightDrive.ENCODER_B,
						Constants.RightDrive.ENCODER_RATIO), navX.getYawAxis(),
				Constants.StraightDrivePID.PID_CONSTANTS,
				Constants.StraightDrivePID.TURN_PID_CONSTANTS,
				Constants.TurnPID.PID_CONSTANTS, Constants.StraightDrivePID.kLR);

		climber = new BlastoiseClimber();
		intake = new BlastoiseIntake();

		shooterLeft = new BlastoiseShooter(Constants.LeftShooter.MOTOR_PORT,
				Constants.LeftShooter.HALL_PORT,
				Constants.LeftShooter.PID_CONSTANTS,
				Constants.LeftShooter.SPEED_RPM,
				Constants.LeftShooter.MOTOR_REVERSED,
				Constants.LeftShooter.BASE_VALUE, false);

		shooterRight = new BlastoiseShooter(Constants.RightShooter.MOTOR_PORT,
				Constants.RightShooter.HALL_PORT,
				Constants.RightShooter.PID_CONSTANTS,
				Constants.RightShooter.SPEED_RPM,
				Constants.RightShooter.MOTOR_REVERSED,
				Constants.RightShooter.BASE_VALUE, true);
	}

	private void setupUI() {
		TurtleDashboard.setAutoModeHolder(this);
		TurtleDashboard.logAxis(inputManager.rightJoystick,
				inputManager.rightJoystick);
	}

	protected void teleUpdate() {

		SmartDashboard.putNumber("Yaw", navX.getYawAxis().getRotation()
				.getDegrees());

		if (inputManager.panicButton.getButton()) {
			intake.startReverse();
			shooterLeft.startReverse();
			shooterRight.startReverse();

			shooterLeft.teleUpdate();
			shooterRight.teleUpdate();

		} else {
			climberUpdate();

			// If robot is climbing, do nothing else
			if (store.isRobotClimbingRope()) {
				intake.stop();
				shooterLeft.stop();
				shooterRight.stop();
			} else {
				intakeUpdate();
				shooterUpdate();
				driveUpdate();
			}
		}

		SmartDashboard.putNumber("Yaw", navX.getYawAxis().getRotation()
				.getDegrees());

		double targetX = vision.getShooterTargetX();

		SmartDashboard.putBoolean("Shooter On Target",
				TurtleMaths.absDiff(targetX, 165) < 45);
		SmartDashboard.putBoolean("Target Visible", targetX > -1);
	}

	private void climberUpdate() {
		if (inputManager.climberSwitch.getDown()) {
			climber.start();

			intake.stop();
			shooterRight.stop();
			shooterLeft.stop();
			chassis.stop();
		} else if (inputManager.climberSwitch.getUp()) {
			climber.stop();
		}
	}

	private void intakeUpdate() {
		if (inputManager.intakeSwitch.get() == 1) {
			intake.start();
		} else if (inputManager.intakeSwitch.get() == 2) {
			intake.startReverse();
		} else {
			intake.stop();
		}
	}

	private void shooterUpdate() {
		/*if (inputManager.shootButton.getButton()) {
			if (inputManager.autoManualToggle.getButton()) {
				if (store.leftShooterStatus != BlastoiseFluxStore.ShooterStatus.SHOOTING
						|| inputManager.shooterSpeed.get() != last
						|| inputManager.autoManualToggle.getDown() || true) {
					last = inputManager.shooterSpeed.get();
					//shooterLeft.setPIDConstants(TurtleDashboard
							//.getPidConstants("LeftShooterPID"));
					//shooterLeft.setSpeedTargetRpm(inputManager.shooterSpeed
							//.get() * (6500.0 / 11.0));
					
					double motorNumber = 0.5 + ((double)inputManager.shooterSpeed.get()) * (0.01);
					shooterLeft.startManual(motorNumber);

					/*shooterRight.setPIDConstants(TurtleDashboard
							.getPidConstants("RightShooterPID"));*/
					//shooterRight.setSpeedTargetRpm(inputManager.shooterSpeed
							//.get() * (6500.0 / 11.0));
					//shooterRight.startManual(motorNumber);
					SmartDashboard.putNumber("shooter motor value manual", motorNumber);

					shooterLeft.start();
					shooterRight.start();
				}

				shooterLeft.teleUpdate();
				//shooterRight.teleUpdate();

				SmartDashboard.putNumber("Shooter Left RPM",
						shooterLeft.getSpeed());
				/*SmartDashboard.putNumber("Shooter Right RPM",
						shooterRight.getSpeed());*/
				SmartDashboard.putBoolean("Shooting", true);
			} else {
				if (store.leftShooterStatus != BlastoiseFluxStore.ShooterStatus.SHOOTING
						|| inputManager.autoManualToggle.getUp()) {
					
					//double distanceInches = lidar.getDistance().getInches();
					
					//double leftRPM = Constants.LeftShooter.RPM_SHIFTER.shift(distanceInches);
					//double leftOpenLoop = Constants.LeftShooter.OPENLOOP_SHIFTER.shift(distanceInches);
					
					//double rightRPM = Constants.RightShooter.RPM_SHIFTER.shift(distanceInches);
					//double rightOpenLoop = Constants.RightShooter.OPENLOOP_SHIFTER.shift(distanceInches);
					

					shooterLeft.setPIDConstants(TurtleDashboard
							.getPidConstants("RightShooterPID"));
					
					shooterLeft.setSpeedTargetRpm(SmartDashboard.getNumber(
							"RightShooterSpeed",
							Constants.RightShooter.SPEED_RPM));

					shooterLeft.setOpenLoop(SmartDashboard.getNumber(
							"RightShooterOpenLoop",
							Constants.RightShooter.SPEED_RPM));

					
					shooterRight.setPIDConstants(TurtleDashboard
							.getPidConstants("RightShooterPID"));
					
					shooterRight.setSpeedTargetRpm(SmartDashboard.getNumber(
							"RightShooterSpeed",
							Constants.LeftShooter.SPEED_RPM));

					shooterRight.setOpenLoop(SmartDashboard.getNumber(
							"RightShooterOpenLoop", 0.7));
					
					
					/*shooterLeft.setPIDConstants(Constants.LeftShooter.PID_CONSTANTS);
					
					shooterLeft.setSpeedTargetRpm(leftRPM);

					shooterLeft.setOpenLoop(leftOpenLoop);

					
					shooterRight.setPIDConstants(Constants.RightShooter.PID_CONSTANTS);
					
					shooterRight.setSpeedTargetRpm(rightRPM);

					shooterRight.setOpenLoop(rightOpenLoop);*/

					shooterLeft.start();
					shooterRight.start();
				}

				shooterLeft.teleUpdate();
				shooterRight.teleUpdate();

				SmartDashboard.putNumber("Shooter Left RPM",
						shooterLeft.getSpeed());
				SmartDashboard.putNumber("Shooter Right RPM",
						shooterRight.getSpeed());
				SmartDashboard.putBoolean("Shooting", true);
			}
			//waggle code/shimmy
			chassis.tankDrive(
					new MotorValue(
							Math.sin(System.currentTimeMillis() / 100.0) * 0.1),
					new MotorValue(
							-Math.sin(System.currentTimeMillis() / 100.0) * 0.1));

		} else {
			SmartDashboard.putNumber("Shooter Left RPM", 0);
			SmartDashboard.putNumber("Shooter Right RPM", 0);
			SmartDashboard.putBoolean("Shooting", false);
			shooterLeft.stop();
			shooterRight.stop();
		}*/
	}

	// Shooter alignment. Only left/right for now.
	private void shooterAlignUpdate() {
		if (inputManager.alignShooterButton.getDown()) {
			turnPid = new PID(
					TurtleDashboard.getPidConstants("ShooterTurnPID"), 165, 0);
		}
		double targetX = vision.getShooterTargetX();

		if (targetX < 0) {
			return;
		}

		MotorValue motorValue = new MotorValue(turnPid.newValue(targetX))
				.scale(Constants.ShooterVision.VisionPID.SPEED);

		chassis.tankDrive(motorValue.invert(), motorValue);

		SmartDashboard.putNumber("Shooter_TargetX", vision.getShooterTargetX());
		SmartDashboard.putNumber("Shooter_MotorValue", motorValue.getValue());
	}

	// User-controlled
	private void driveUpdate() {
		// If holding down shooter align button, use PID loop to align with high
		// goal
		if (inputManager.alignShooterButton.getButton()) {
			if (inputManager.alignShooterButton.getDown()) {
				chassis.stop();
			}
			shooterAlignUpdate();
		} else {
			MotorValue leftPower = new MotorValue(TurtleMaths.deadband(
					inputManager.getLeft(),
					Constants.DriverStation.JOYSTICK_DEADBAND));
			MotorValue rightPower = new MotorValue(TurtleMaths.deadband(
					inputManager.getRight(),
					Constants.DriverStation.JOYSTICK_DEADBAND));

			// Smoother control of the robot
			if (inputManager.slowButton.getButton()
					|| store.isRobotClimbingRope()) {
				leftPower = leftPower.scale(Constants.Drive.SLOW_SPEED);
				rightPower = rightPower.scale(Constants.Drive.SLOW_SPEED);
			} else if (Constants.DriverStation.LOGISTIC_SCALE) {
				leftPower = new MotorValue(
						TurtleMaths.logisticStepScale(leftPower.getValue()));
				rightPower = new MotorValue(
						TurtleMaths.logisticStepScale(rightPower.getValue()));
			}

			if (inputManager.straightButton.getButton()) {
				chassis.tankDrive(rightPower, rightPower);
			} else if (inputManager.turnButton.getButton()) {
				chassis.tankDrive(leftPower, leftPower.invert());
			} else {
				chassis.tankDrive(leftPower, rightPower);
			}
		}
	}

	// Below methods are mostly wrappers and should NOT be edited or removed

	public void disabled() {
		TurtleDashboard.disabled();
	}

	public void autonomous() {
		TurtleDashboard.autonomous();
		AutoMode autoMode = autoModes.get(selectedAutoMode);

		if (autoMode == null) {
			logger.warn("Autonomous mode not implemented");
		} else {
			autoMode.auto();
		}
	}

	public void operatorControl() {
		TurtleDashboard.teleop();
		while (RobotState.isOperatorControl() && RobotState.isEnabled()) {
			teleUpdate();
		}
	}

	public void test() {
		TurtleDashboard.test();
		if (testMode == null) {
			logger.warn("Test mode not implemented");
		} else {
			testMode.test();
		}
	}

	// Handles Different Robot Modes (Auto & Test)
	private ArrayList<AutoMode> autoModes = new ArrayList<>();
	private int selectedAutoMode = 0;
	private TestMode testMode;

	private void setupRobotModes() {
		setupAutoModes();
		testMode = () -> {
			SmartDashboard.putString("Test", "Successful");
		};
	}

	public ArrayList<AutoMode> getAutoModes() {
		return autoModes;
	}

	public void setSelectedAutoModeIndex(int index) {
		if (index < autoModes.size()) {
			selectedAutoMode = index;
		}
	}

	public int getSelectedAutoModeIndex() {
		return selectedAutoMode;
	}
}
