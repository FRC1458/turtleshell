package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.core.AutoMode;
import com.team1458.turtleshell2.core.AutoModeHolder;
import com.team1458.turtleshell2.core.TestMode;
import com.team1458.turtleshell2.movement.*;
import com.team1458.turtleshell2.movement.TurtleSmartMotor.BrakeMode;
import com.team1458.turtleshell2.sensor.TurtleDistanceEncoder;
import com.team1458.turtleshell2.input.FlightStick;
import com.team1458.turtleshell2.input.XboxController;
import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.sensor.LIDARSerial;
import com.team1458.turtleshell2.sensor.PDP;
import com.team1458.turtleshell2.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.sensor.fake.TurtleFakeDistanceEncoder;
import com.team1458.turtleshell2.sensor.fake.TurtleFakeRotationEncoder;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1458.robot.autonomous.MiddleGear;
import org.usfirst.frc.team1458.robot.autonomous.TestAutonomous;
import org.usfirst.frc.team1458.robot.components.BlastoiseClimber;
import org.usfirst.frc.team1458.robot.components.BlastoiseIntake;
import org.usfirst.frc.team1458.robot.components.BlastoiseShooter;
import org.usfirst.frc.team1458.robot.components.TurtwigShooter;
import org.usfirst.frc.team1458.robot.vision.BlastoiseShooterVision;

import java.util.ArrayList;

/**
 * This is the base robot
 * 
 * @author asinghani
 */
public class Robot extends SampleRobot implements AutoModeHolder {
	private Timer heartbeat;

	// Sensors
	private TurtleNavX navX = null;
	private TurtleDistanceSensor lidar = null;

	// Input
	private BlastoiseInputManager inputManager;

	// Robot Actuators
	private TankDrive chassis;

	private BlastoiseClimber climber;
	private BlastoiseIntake intake;

	private TurtwigShooter shooterLeft;
	private TurtwigShooter shooterRight;

	private BlastoiseFluxStore store;

	private PowerDistributionPanel pdp;
	
	private TurtleTalonSRXCAN agitator = new TurtleTalonSRXCAN(20, false, BrakeMode.BRAKE, 1);

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
			SmartDashboard.putNumber("RightShooterOpenLoop", 0.0);
		}
	}

	private void setupAutoModes() {
		autoModes.add(new TestAutonomous(chassis, logger, navX));
		autoModes.add(new MiddleGear(chassis, logger, navX));
		selectedAutoMode = 0;
		SmartDashboard.putString("AutoModeSetting2", "set4");
	}

	private void setupSensors() {
		navX = new TurtleNavX(I2C.Port.kOnboard);
		lidar = new TurtleFakeDistanceEncoder();// snew LIDARSerial(SerialPort.Port.kUSB1);
		pdp = new PowerDistributionPanel();
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
					controller);
		}
		
	}

	private void setupActuators() {
		chassis = new TankDrive(
				new FollowerMotorSet(
						new TurtleTalonSRXCAN(Constants.LeftDrive.MOTOR1),
						new TurtleTalonSRXCAN(Constants.LeftDrive.MOTOR2),
						new TurtleTalonSRXCAN(Constants.LeftDrive.MOTOR3)
				),
				new FollowerMotorSet(
					new TurtleTalonSRXCAN(Constants.RightDrive.MOTOR1, true),
					new TurtleTalonSRXCAN(Constants.RightDrive.MOTOR2, true),
					new TurtleTalonSRXCAN(Constants.RightDrive.MOTOR3, true)
				),
				new TurtleDistanceEncoder(
						Constants.LeftDrive.ENCODER_A,
						Constants.LeftDrive.ENCODER_B,
						Constants.LeftDrive.ENCODER_RATIO
				),
				new TurtleDistanceEncoder(
						Constants.RightDrive.ENCODER_A,
						Constants.RightDrive.ENCODER_B,
						Constants.RightDrive.ENCODER_RATIO,
						true
				),
				navX.getYawAxis(),
				Constants.StraightDrivePID.PID_CONSTANTS,
				Constants.StraightDrivePID.TURN_PID_CONSTANTS,
				Constants.TurnPID.PID_CONSTANTS, Constants.StraightDrivePID.kLR
		);


		// TODO THIS WILL BREAK SOMETHING
		 
		// This is the chassis for the practice bot, DO NOT MOVE THIS INTO CONSTANTS CLASS
		// Also don't question it
		/*chassis = new TankDrive(
				new TurtleTalonSR(8),
				new TurtleTalonSR(7, true),
				new TurtleDistanceEncoder(2, 3, 0.0697777777, true),
				new TurtleDistanceEncoder(0, 1, 0.0697777777),
				navX.getYawAxis(),
				Constants.StraightDrivePID.PID_CONSTANTS,
				Constants.StraightDrivePID.TURN_PID_CONSTANTS,
				Constants.TurnPID.PID_CONSTANTS, Constants.StraightDrivePID.kLR
		);*/

		climber = new BlastoiseClimber();
		intake = new BlastoiseIntake();
		shooterLeft = new TurtwigShooter(false);
		shooterRight = new TurtwigShooter(true);

		/*
		 * shooterLeft = new BlastoiseShooter(Constants.LeftShooter.MOTOR_PORT,
		 * Constants.LeftShooter.HALL_PORT, Constants.LeftShooter.PID_CONSTANTS,
		 * Constants.LeftShooter.SPEED_RPM,
		 * Constants.LeftShooter.MOTOR_REVERSED,
		 * Constants.LeftShooter.BASE_VALUE, false);
		 * 
		 * shooterRight = new
		 * BlastoiseShooter(Constants.RightShooter.MOTOR_PORT,
		 * Constants.RightShooter.HALL_PORT,
		 * Constants.RightShooter.PID_CONSTANTS,
		 * Constants.RightShooter.SPEED_RPM,
		 * Constants.RightShooter.MOTOR_REVERSED,
		 * Constants.RightShooter.BASE_VALUE, true);
		 */
	}

	private void setupUI() {
		TurtleDashboard.logAxis(inputManager.rightJoystick,
				inputManager.rightJoystick);
	}

	protected void teleUpdate() {

		SmartDashboard.putNumber("LIDAR", lidar.getDistance().getInches());

		if (inputManager.panicButton.getButton()) {
			intake.startReverse();
			shooterLeft.startReverse();
			shooterRight.startReverse();

			shooterLeft.teleUpdate();
			shooterRight.teleUpdate();
			
			//climber.startReverse();

		} else {
			climberUpdate();

			// If robot is climbing, do nothing else
			if (store.isRobotClimbingRope()) {
				shooterLeft.stop();
				shooterRight.stop();
				driveUpdate();
			} else {
				shooterUpdate();
				driveUpdate();
			}
			intakeUpdate();
		}

		SmartDashboard.putNumber("Yaw", navX.getYawAxis().getRotation()
				.getDegrees());
		

		SmartDashboard.putNumber("LeftEncoder", chassis.getLeftDistance().getInches());
		SmartDashboard.putNumber("RightEncoder", chassis.getRightDistance ().getInches());

		double targetX = vision.getShooterTargetX();


		SmartDashboard.putNumber("TargetX", targetX);
		
		SmartDashboard.putBoolean("Shooter On Target",
				TurtleMaths.absDiff(targetX, 165) < 45);
		SmartDashboard.putBoolean("Target Visible", targetX > -1);
	}

	private void climberUpdate() {
		if (inputManager.climberSwitch.getDown()) {
			climber.start();

			intake.stop();
			// shooterRight.stop();
			// shooterLeft.stop();
			//chassis.stop();
		} else if (inputManager.climberSwitch.getUp()) {
			climber.stop();
		} else if (inputManager.climberSwitch.getButton() == false) {
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
		SmartDashboard.putNumber("PDP current shooter", pdp.getCurrent(11));
		SmartDashboard.putNumber("PDP current no breaker", pdp.getCurrent(5));
		SmartDashboard.putNumber("PDP current chassis", pdp.getCurrent(13));
		SmartDashboard.putNumber("PDP temperature", pdp.getTemperature());
		SmartDashboard.putNumber("PDP total current", pdp.getTotalCurrent());
		SmartDashboard.putNumber("PDP voltage", pdp.getVoltage());
		
		if (inputManager.agitateButton.getButton()) {
			agitator.set(Constants.Shooter.AGITATOR_SPEED);
		}
		else {
			agitator.set(new MotorValue(0));
		}

		if (inputManager.shootButton.getButton()) {
			if (inputManager.autoManualToggle.getButton()) {
				// This will also use PID now
				shooterLeft.setIsManualPower(false);
				shooterLeft.setRPMTarget(
						TurtleMaths.shift(inputManager.shooterSpeed.get(), 0, 11,
								Constants.LeftShooter.MIN_SPEED, Constants.LeftShooter.MAX_SPEED));

				shooterRight.setIsManualPower(false);
				shooterRight.setRPMTarget(
						TurtleMaths.shift(inputManager.shooterSpeed.get(), 0, 11,
								Constants.RightShooter.MIN_SPEED, Constants.RightShooter.MAX_SPEED));

				SmartDashboard.putBoolean("Auto Shooter Working", false);
			} else {
				// TODO make this work with lidar sensor
				double distance = lidar.getDistance().getInches();

				double visionDistance = vision.getShooterTargetDistance();

				if(distance > 25 && distance < 12*25 /*&& visionDistance > 20 && visionDistance < 12*25 &&
						TurtleMaths.absDiff(visionDistance, distance) < 20*/) {  // 25 inch min, 25 feet max
					
					double leftRPM = Constants.LeftShooter.RPM_SHIFTER.shift(distance);
					double rightRPM = Constants.RightShooter.RPM_SHIFTER.shift(distance);

					shooterLeft.setIsManualPower(false);
					shooterLeft.setRPMTarget(leftRPM);

					shooterRight.setIsManualPower(false);
					shooterRight.setRPMTarget(rightRPM);

					SmartDashboard.putBoolean("Auto Shooter Working", true);
				} else {
					//auto targeting failure, resort to manual
					shooterLeft.setIsManualPower(false);
					shooterLeft.setRPMTarget(inputManager.shooterSpeed.get() * (5000/11));

					shooterRight.setIsManualPower(false);
					shooterRight.setRPMTarget(inputManager.shooterSpeed.get() * (5000/11));

					SmartDashboard.putBoolean("Auto Shooter Working", false);
					
				}

			}
			/*if (inputManager.shootButton.getButton()) {
				shooterLeft.setPIDConstants(TurtleDashboard
						.getPidConstants("LeftShooterPID"));
				shooterRight.setPIDConstants(TurtleDashboard
						.getPidConstants("RightShooterPID"));
				System.out.println(TurtleDashboard
						.getPidConstants("LeftShooterPID"));
				shooterLeft.setTargetOpenLoop(new MotorValue(SmartDashboard
						.getNumber("LeftShooterOpenLoop", 0)));
				shooterRight.setTargetOpenLoop(new MotorValue(SmartDashboard
						.getNumber("RightShooterOpenLoop", 0)));
			}*/
			//disabled, code for adjusting constants

			shooterLeft.teleUpdate();
			shooterRight.teleUpdate();
		} else {
			shooterLeft.stop();
			shooterRight.stop();
		}

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
			MotorValue leftPower = new MotorValue(
					TurtleMaths.deadband(inputManager.getLeft(), Constants.DriverStation.JOYSTICK_DEADBAND));

			MotorValue rightPower = new MotorValue(
					TurtleMaths.deadband(inputManager.getRight(), Constants.DriverStation.JOYSTICK_DEADBAND));

			// Smoother control of the robot
			if (inputManager.slowButton.getButton() || store.isRobotClimbingRope() || inputManager.gearButton.getButton()) {
				leftPower = leftPower.scale(Constants.Drive.SLOW_SPEED);
				rightPower = rightPower.scale(Constants.Drive.SLOW_SPEED);

			} else if (Constants.DriverStation.LOGISTIC_SCALE) {
				leftPower = new MotorValue(
						TurtleMaths.logisticStepScale(leftPower.getValue()));
				rightPower = new MotorValue(
						TurtleMaths.logisticStepScale(rightPower.getValue()));
			}

			if(inputManager.gearButton.getButton()) {
				// This reverses motor directions while swapping sides
				MotorValue leftTemp = rightPower.invert();
				MotorValue rightTemp = leftPower.invert();

				leftPower = leftTemp;
				rightPower = rightTemp;

			}
			
			if (inputManager.straightButton.getButton()) {
				if(inputManager.gearButton.getButton()) {
					chassis.tankDrive(leftPower, leftPower);
				} else {
					chassis.tankDrive(rightPower, rightPower);
				}
			} else if (inputManager.turnButton.getButton()) {
				if(inputManager.gearButton.getButton()) {
					chassis.tankDrive(rightPower, rightPower.invert());
				} else {
					chassis.tankDrive(leftPower, leftPower.invert());
				}
			} else {
				chassis.tankDrive(leftPower, rightPower);
			}


			/*
			 * waggle code - gives robot seizure chassis.tankDrive( new
			 * MotorValue( Math.sin(System.currentTimeMillis() / 25.0) * 0.35),
			 * new MotorValue( -Math.sin(System.currentTimeMillis() / 25.0) *
			 * 0.35));
			 */
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
		SmartDashboard.putString("AutoModeSetting0", "set0");
		setupAutoModes();
		testMode = () -> {
			SmartDashboard.putString("Test", "Successful");
		};
		SmartDashboard.putString("AutoModeSetting1", "set1");
		TurtleDashboard.setAutoModeHolder(this);
		SmartDashboard.putString("AutoModeSetting2", "set2");
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
