package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.core.AutoMode;
import com.team1458.turtleshell2.core.AutoModeHolder;
import com.team1458.turtleshell2.core.TestMode;
import com.team1458.turtleshell2.input.FlightStick;
import com.team1458.turtleshell2.input.XboxController;
import com.team1458.turtleshell2.movement.FollowerMotorSet;
import com.team1458.turtleshell2.movement.TankDriveChassis;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.sensor.LIDARLite;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.RobotState;
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
public class BlastoiseRobot implements AutoModeHolder {

	/**
	 * Sensors
	 */
	TurtleNavX navX = null;
	LIDARLite lidar = null;

	/**
	 * Input
	 */
	private BlastoiseInputManager inputManager;

	/**
	 * Robot Actuators
	 */
	private TankDriveChassis chassis;

	private BlastoiseClimber climber;
	private BlastoiseIntake intake;

	private BlastoiseShooter shooterLeft;
	private BlastoiseShooter shooterRight;

	/**
	 * Vision
	 */
	BlastoiseShooterVision vision = new BlastoiseShooterVision(Constants.ShooterVision.Camera.URL);
	PID turnPid = new PID(Constants.ShooterVision.VisionPID.PID_CONSTANTS, 165, 0);

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
		if (Constants.DEBUG) {
			TurtleDashboard.enablePidTuning(Constants.ShooterVision.VisionPID.PID_CONSTANTS, "ShooterVisionPID");
			SmartDashboard.putNumber("ShooterVisionTurnSpeed", 0.5);

			TurtleDashboard.enablePidTuning(Constants.LeftShooter.PID_CONSTANTS, "LeftShooterPID");
			SmartDashboard.putNumber("LeftShooterSpeed", Constants.LeftShooter.SPEED_RPM);

			TurtleDashboard.enablePidTuning(Constants.RightShooter.PID_CONSTANTS, "RightShooterPID");
			SmartDashboard.putNumber("RightShooterSpeed", Constants.RightShooter.SPEED_RPM);
		}
	}

	private void setupAutoModes() {
		autoModes.add(new TestAutonomous(chassis, logger, navX));
		selectedAutoMode = 0;
	}
	
	private void setupSensors() {
		navX = new TurtleNavX(I2C.Port.kMXP);
		lidar = new LIDARLite(I2C.Port.kOnboard);
	}

	private void setupInput() {
		BlastoiseController controller = new BlastoiseController(5);
		XboxController xController = new XboxController(Constants.DriverStation.UsbPorts.XBOX_CONTROLLER);

		if (Constants.DriverStation.USE_XBOX_CONTROLLER) {
			XboxController xboxController = new XboxController(Constants.DriverStation.UsbPorts.XBOX_CONTROLLER);
			inputManager = new BlastoiseInputManager(xboxController, controller);
		} else {
			FlightStick leftStick = new FlightStick(Constants.DriverStation.UsbPorts.LEFT_STICK);
			FlightStick rightStick = new FlightStick(Constants.DriverStation.UsbPorts.RIGHT_STICK);
			inputManager = new BlastoiseInputManager(leftStick, rightStick, xController);
		}
	}

	private void setupActuators() {
		chassis = new TankDriveChassis(
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
				navX.getYawAxis()
		);

		climber = new BlastoiseClimber(Constants.Climber.MOTOR_PORT, Constants.Climber.SPEED, Constants.Climber.SPEED_LOW);
		intake = new BlastoiseIntake(Constants.Intake.MOTOR_PORT, Constants.Intake.SPEED);

		shooterLeft = new BlastoiseShooter(Constants.LeftShooter.MOTOR_PORT, Constants.LeftShooter.HALL_PORT,
				Constants.LeftShooter.PID_CONSTANTS, Constants.LeftShooter.SPEED_RPM,
				Constants.LeftShooter.MOTOR_REVERSED, Constants.LeftShooter.BASE_VALUE);

		shooterRight = new BlastoiseShooter(Constants.RightShooter.MOTOR_PORT, Constants.RightShooter.HALL_PORT,
				Constants.RightShooter.PID_CONSTANTS, Constants.RightShooter.SPEED_RPM,
				Constants.RightShooter.MOTOR_REVERSED, Constants.RightShooter.BASE_VALUE);
	}

	private void setupUI() {
		TurtleDashboard.setAutoModeHolder(this);
		TurtleDashboard.logAxis(inputManager.rightJoystick, inputManager.rightJoystick);
	}

	/**
	 * ----------------------- END INITIALIZATION CODE -----------------------
	 */

	/**
	 * ----------------------- START MANUAL CODE -----------------------
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
		 * a. If holding down shooter align button, use PID loop to align with high goal
		 * b. If holding down gear align button, use PID loop to align while also driving forward
		 * c. Else drive normal teleop mode
		 */

		SmartDashboard.putNumber("OtherLidar Distance", lidar.getDistance().getInches());
		//SmartDashboard.putNumber("OtherLidar Velocity", lidar.getVelocity().getValue());
		
		if(1 == 1) return; // TODO REMOVE THIS IS VERY BAD AND BREAKS EVERYTHING

		/**
		 * Panic Button Functionality
		 */
		if(inputManager.panicButton.getButton()) {
			climber.startReverse();
			intake.startReverse();
			shooterLeft.startReverse();
			shooterRight.startReverse();

		} else {
			/**
			 * Start climbing if switch is active
			 */
			climberUpdate();

			/**
			 * If robot is climbing, do nothing else
			 */
			if (climber.isClimbing()) {
				intake.stop();
				shooterLeft.stop();
				shooterRight.stop();
				chassis.stop();
				// Don't do the thing
			} else {
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
			}
		}

		/**
		 * Extra Info
		 */
		SmartDashboard.putNumber("Yaw", navX.getYawAxis().getRotation().getDegrees());

		double targetX = vision.getShooterTargetX();

		SmartDashboard.putBoolean("Shooter On Target", TurtleMaths.absDiff(targetX, 165) < 45);
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
		if (inputManager.shootButton.getButton()) {

			if (shooterLeft.getStatus() != BlastoiseShooter.ShooterStatus.SHOOTING) {
				if (Constants.DEBUG) {
					shooterLeft.setPIDConstants(TurtleDashboard.getPidConstants("LeftShooterPID"));
					shooterLeft.setSpeedTarget(SmartDashboard.getNumber("LeftShooterSpeed", 0));

					shooterRight.setPIDConstants(TurtleDashboard.getPidConstants("RightShooterPID"));
					shooterRight.setSpeedTarget(SmartDashboard.getNumber("RightShooterSpeed", 0));
				}
				shooterLeft.start();
				shooterRight.start();

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
			shooterLeft.stop();
			shooterRight.stop();
		}
		SmartDashboard.putString("Status", shooterLeft.getStatus().toString());
	}

	/**
	 * Shooter alignment. Only left/right for now.
	 */
	private void shooterAlignUpdate() {
		if (inputManager.alignShooterButton.getDown()) {
			turnPid = new PID(TurtleDashboard.getPidConstants("ShooterTurnPID"), 165, 0);
		}
		double targetX = vision.getShooterTargetX();

		if (targetX < 0) {
			return;
		}

		MotorValue motorValue = new MotorValue(turnPid.newValue(targetX))
				.mapToSpeed(new MotorValue(SmartDashboard.getNumber("ShooterTurnSpeed", 0.25)));

		chassis.updateMotors(motorValue.invert(), motorValue);

		SmartDashboard.putNumber("Shooter_TargetX", vision.getShooterTargetX());
		SmartDashboard.putNumber("Shooter_MotorValue", motorValue.getValue());
	}

	/**
	 * User-controlled
	 */
	private void driveUpdate() {
		/**
		 * If holding down shooter align button, use PID loop to align with high
		 * goal
		 */
		if (inputManager.alignShooterButton.getButton()) {
			if(inputManager.alignShooterButton.getDown()) {
				chassis.stop();
			}
			shooterAlignUpdate();
		} else {
			MotorValue leftPower = new MotorValue(
					TurtleMaths.deadband(inputManager.getLeft(), Constants.DriverStation.JOYSTICK_DEADBAND));
			MotorValue rightPower = new MotorValue(
					TurtleMaths.deadband(inputManager.getRight(), Constants.DriverStation.JOYSTICK_DEADBAND));

			PIDConstants turnConstants = TurtleDashboard.getPidConstants("TurnPID");

			/**
			 * Left/Right turn with buttons
			 */
			if (inputManager.right90button.getUp()) {
				chassis.turn(new Angle(90), new MotorValue(0.7), turnConstants, 5);
				return;
			}

			if (inputManager.left90button.getUp()) {
				chassis.turn(new Angle(-90), new MotorValue(0.7), turnConstants, 5);
				return;
			}

			/**
			 * Smoother control of the robot
			 */
			if (inputManager.slowButton.getButton()) {
				leftPower = leftPower.scale(Constants.Drive.slowSpeed);
				rightPower = rightPower.scale(Constants.Drive.slowSpeed);
			} else if (Constants.DriverStation.LOGISTIC_SCALE) {
				leftPower = new MotorValue(TurtleMaths.logisticStepScale(leftPower.getValue()));
				rightPower = new MotorValue(TurtleMaths.logisticStepScale(rightPower.getValue()));
			}

			if (inputManager.straightButton.getButton()) {
				chassis.updateMotors(rightPower, rightPower);
			} else if (inputManager.turnButton.getButton()) {
				chassis.updateMotors(leftPower, leftPower.invert());
			} else {
				chassis.updateMotors(leftPower, rightPower);
			}

			chassis.update(); // Needed for Turn PID
		}

	}

	/**
	 * ----------------------- END MANUAL CODE -----------------------
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

		if (autoMode == null) {
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
		if (testMode == null) {
			logger.warn("Test mode not implemented");
		} else {
			testMode.test();
		}
	}

	/**
	 * Handles Different Robot Modes (Auto & Test)
	 */
	private ArrayList<AutoMode> autoModes = new ArrayList<>();
	private int selectedAutoMode = 0;
	private TestMode testMode;

	private void setupRobotModes() {
		setupAutoModes();
		testMode = () -> {
			SmartDashboard.putString("Test", "Successful");
		};
	}

	/**
	 * Get the list of auto modes
	 */
	public ArrayList<AutoMode> getAutoModes() {
		return autoModes;
	}

	/**
	 * Set the index of the selected auto mode
	 * 
	 * @param index
	 */
	public void setSelectedAutoModeIndex(int index) {
		if (index < autoModes.size()) {
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
