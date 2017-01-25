package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.drive.TankDrive;
import com.team1458.turtleshell2.implementations.drive.TurtleMotorSet;
import com.team1458.turtleshell2.implementations.input.InputManager;
import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleJoystickPOVSwitch;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.movement.TurtleFakeMotor;
import com.team1458.turtleshell2.implementations.movement.TurtleSpark;
import com.team1458.turtleshell2.implementations.movement.TurtleTalonSR;
import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.implementations.sensor.TurtleDistanceEncoder;
import com.team1458.turtleshell2.implementations.sensor.TurtleNavX;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.InputMapping;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * New BlastoiseRobot Chassis
 *
 * @author asinghani
 */
public class BlastoiseChassis implements TurtleComponent{

	/**
	 * Drive train
	 */
	private TankDrive tankDrive;

	private final TurtleNavX navX = TurtleNavX.getInstanceI2C(); //TurtleNavX.getInstanceUSB(RobotConstants.Sensors.NAVX_PORT);

	/**
	 * Chassis Specific Initialization
	 */
	{
		if(BlastoiseRobot.isPracticeRobot()) {
			// Turtwig Chassis
			tankDrive = new TankDrive(
					new TurtleMotorSet(
							new TurtleVictor888(TurtwigConstants.LeftDrive.MOTOR1),
							new TurtleFakeMotor()
					),
					new TurtleMotorSet(
							new TurtleVictor888(TurtwigConstants.RightDrive.MOTOR1, true),
							new TurtleFakeMotor()
					),
					new TurtleDistanceEncoder(
							TurtwigConstants.LeftDrive.ENCODER_A, TurtwigConstants.LeftDrive.ENCODER_B,
							TurtwigConstants.LeftDrive.ENCODER_RATIO, true
					),
					new TurtleDistanceEncoder(
							TurtwigConstants.RightDrive.ENCODER_A, TurtwigConstants.RightDrive.ENCODER_B,
							TurtwigConstants.RightDrive.ENCODER_RATIO, false
					),
					navX
			);
		} else {
			// Blastoise Chassis
			tankDrive = new TankDrive(
					new TurtleMotorSet(
							new TurtleSpark(BlastoiseConstants.LeftDrive.MOTOR1),
							new TurtleTalonSR(BlastoiseConstants.LeftDrive.MOTOR2)
					),
					new TurtleMotorSet(
							new TurtleSpark(BlastoiseConstants.RightDrive.MOTOR1, true),
							new TurtleTalonSR(BlastoiseConstants.RightDrive.MOTOR2, true)
					),
					navX
			);
		}
	}

	// Input joysticks and buttons

	private TurtleAnalogInput rightJoystick;
	private TurtleAnalogInput leftJoystick;

	/*
	private TurtleButtonInput straightButton;
	private TurtleButtonInput turnButton;

	private TurtleButtonInput resetButton;

	private TurtleXboxController rumbleController; // Very experimental
	*/

	private InputManager input;

	private TurtleLogger logger;

	/**
	 * Main constructor for BlastoiseChassis
	 * Accepts TurtleAnalogInputs and TurtleDigitalInputs
	 */
	public BlastoiseChassis(InputManager input, TurtleLogger logger) {
		this.input = input;

		leftJoystick = input.getAxis("LEFT_JOYSTICK");
		rightJoystick = input.getAxis("RIGHT_JOYSTICK");

		TurtleDashboard.logAxis(leftJoystick, rightJoystick);

		this.logger = logger;
	}


	/**
	 * Convenience method for instantiating with Xbox controller
	 * @param controller Xbox controller
	 */
	public BlastoiseChassis (TurtleXboxController controller, InputMapping mapping, TurtleLogger logger) {
		this(
				new InputManager(controller, mapping),
				logger
		);
	}

	/**
	 * Convenience method for instantiating with two flight sticks
	 * @param leftFlightStick
	 * @param rightFlightStick
	 */
	public BlastoiseChassis (TurtleFlightStick leftFlightStick, TurtleFlightStick rightFlightStick, InputMapping mapping, TurtleLogger logger) {
		this(
				new InputManager(leftFlightStick, rightFlightStick, mapping),
				logger
		);
	}

	@Override
	public void teleUpdate() {
		MotorValue leftPower = new MotorValue(TurtleMaths.deadband(leftJoystick.get(), RobotConstants.JOYSTICK_DEADBAND));
		MotorValue rightPower = new MotorValue(TurtleMaths.deadband(rightJoystick.get(), RobotConstants.JOYSTICK_DEADBAND));

		if(input.getButtonValue("TURN_BUTTON")) {
			updateMotors(leftPower, leftPower.invert());
		} else if(input.getButtonValue("STRAIGHT_DRIVE_BUTTON")){
			updateMotors(leftPower, leftPower);
		} else{
			updateMotors(leftPower, rightPower);
		}

		TurtleJoystickPOVSwitch.POVValue povValue = input.getPOVvalue("POV");
		if(povValue != TurtleJoystickPOVSwitch.POVValue.CENTER){
			int degrees = povValue.val;
			if(degrees > 180) degrees = (degrees - 360);

			while(input.getPOVvalue("POV") != TurtleJoystickPOVSwitch.POVValue.CENTER);

			tankDrive.turn(new Angle(degrees), new MotorValue(RobotConstants.TurnPID.TURN_SPEED),
					RobotConstants.TurnPID.PID_CONSTANTS);
		}
		
		SmartDashboard.putNumber("LeftEncoder", tankDrive.getLeftDistance().getInches());
		SmartDashboard.putNumber("RightEncoder", tankDrive.getRightDistance().getInches());


		SmartDashboard.putNumber("Yaw", navX.getYaw().getDegrees());
		SmartDashboard.putNumber("CompassHeading", navX.getCompassHeading().getDegrees());
		SmartDashboard.putNumber("FusedHeading", navX.getFusedHeading().getDegrees());
		
		if(navX.isInCollision(RobotConstants.COLLISION_THRESHOLD)){
			input.rumble(1.0f, 250); // Very experimental
		}
	}

	public Distance getLeftDistance() {
		return tankDrive.getLeftDistance();
	}

	public Distance getRightDistance() {
		return tankDrive.getRightDistance();
	}

	public void updateMotors(MotorValue leftPower, MotorValue rightPower) {
		tankDrive.updateMotors(leftPower, rightPower);
	}

	public void stopMotors() {
		tankDrive.stopMotors();
	}
}
