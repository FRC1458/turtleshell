package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.drive.TankDrive;
import com.team1458.turtleshell2.implementations.drive.TurtleMotorSet;
import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.movement.TurtleFakeMotor;
import com.team1458.turtleshell2.implementations.movement.TurtleSpark;
import com.team1458.turtleshell2.implementations.movement.TurtleTalonSR;
import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.implementations.sensor.TurtleDistanceEncoder;
import com.team1458.turtleshell2.implementations.sensor.TurtleNavX;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.input.TurtleDigitalInput;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.TurtleMaths;
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

	private TurtleDigitalInput straightButton;
	private TurtleDigitalInput turnButton;

	private TurtleDigitalInput resetButton;

	private TurtleXboxController rumbleController; // Very experimental

	private TurtleLogger logger;

	/**
	 * Main constructor for BlastoiseChassis
	 * Accepts TurtleAnalogInputs and TurtleDigitalInputs
	 */
	public BlastoiseChassis(TurtleAnalogInput leftJoystick, TurtleAnalogInput rightJoystick,
			TurtleDigitalInput straightButton, TurtleDigitalInput turnButton, TurtleDigitalInput resetButton, TurtleLogger logger) {
		this.leftJoystick = leftJoystick;
		this.rightJoystick = rightJoystick;

		this.straightButton = straightButton;
		this.turnButton = turnButton;
		this.resetButton = resetButton;
		
		TurtleDashboard.logAxis(leftJoystick, rightJoystick);

		this.logger = logger;
	}


	/**
	 * Convenience method for instantiating with Xbox controller
	 * @param controller Xbox controller
	 */
	public BlastoiseChassis (TurtleXboxController controller, TurtleLogger logger) {
		this(
				controller.getAxis(TurtleXboxController.XboxAxis.LY),
				controller.getAxis(TurtleXboxController.XboxAxis.RY),
				controller.getButton(TurtleXboxController.XboxButton.RBUMP),
				controller.getButton(TurtleXboxController.XboxButton.LBUMP),
				controller.getButton(TurtleXboxController.XboxButton.A),
				logger
		);
		rumbleController = controller;
	}

	/**
	 * Convenience method for instantiating with two flight sticks
	 * @param leftFlightStick
	 * @param rightFlightStick
	 */
	public BlastoiseChassis (TurtleFlightStick leftFlightStick, TurtleFlightStick rightFlightStick, TurtleLogger logger) {
		this(
				leftFlightStick.getAxis(TurtleFlightStick.FlightAxis.PITCH),
				rightFlightStick.getAxis(TurtleFlightStick.FlightAxis.PITCH),
				rightFlightStick.getButton(TurtleFlightStick.FlightButton.TRIGGER),
				leftFlightStick.getButton(TurtleFlightStick.FlightButton.TRIGGER),
				rightFlightStick.getButton(TurtleFlightStick.FlightButton.TWO),
				logger
		);
	}

	@Override
	public void teleUpdate() {
		MotorValue leftPower = new MotorValue(TurtleMaths.deadband(leftJoystick.get(), RobotConstants.JOYSTICK_DEADBAND));
		MotorValue rightPower = new MotorValue(TurtleMaths.deadband(rightJoystick.get(), RobotConstants.JOYSTICK_DEADBAND));
		
		if(turnButton.get() == 1) {
			updateMotors(leftPower, leftPower.invert());
		} else if(straightButton.get() == 1){
			updateMotors(leftPower, leftPower);
		} else{
			updateMotors(leftPower, rightPower);
		}
		
		/*if(resetButton.get() == 1){
			leftDistance.reset();
			rightDistance.reset();
		}*/
		
		SmartDashboard.putNumber("LeftEncoder", tankDrive.getLeftDistance().getInches());
		SmartDashboard.putNumber("RightEncoder", tankDrive.getRightDistance().getInches());


		SmartDashboard.putNumber("Yaw", navX.getYaw().getDegrees());
		SmartDashboard.putNumber("CompassHeading", navX.getCompassHeading().getDegrees());
		SmartDashboard.putNumber("FusedHeading", navX.getFusedHeading().getDegrees());
		
		if(rumbleController != null && navX.isInCollision(RobotConstants.COLLISION_THRESHOLD)){
			rumbleController.rumble(1.0f, 250); // Very experimental
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
