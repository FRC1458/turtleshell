package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.movement.TurtleFakeMotor;
import com.team1458.turtleshell2.implementations.movement.TurtleSpark;
import com.team1458.turtleshell2.implementations.movement.TurtleTalonSR;
import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.implementations.sensor.TurtleDistanceEncoder;
import com.team1458.turtleshell2.implementations.sensor.fake.TurtleFakeDistanceEncoder;
import com.team1458.turtleshell2.interfaces.Chassis;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.input.TurtleDigitalInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.interfaces.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.interfaces.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Random;

/**
 * New BlastoiseRobot Chassis
 *
 * @author asinghani
 */
public class BlastoiseChassis implements Chassis, TurtleComponent{

	/**
	 * Drive motor(s)
	 */
    private final TurtleMotor leftDriveMotor1;
	private final TurtleMotor leftDriveMotor2;

	private final TurtleMotor rightDriveMotor1;
	private final TurtleMotor rightDriveMotor2;

	/**
	 * Encoders
	 */
	private final TurtleDistanceSensor leftDistance;
	private final TurtleDistanceSensor rightDistance;

	private final TurtleRotationSensor orientationSensor = null; //new TurtleAnalogGyro(RobotConstants.GYRO_PORT);

	/**
	 * Chassis Specific Initialization
	 */
	{
		if(BlastoiseRobot.isPracticeRobot()) {
			// Turtwig Chassis
			leftDriveMotor1 = new TurtleVictor888(TurtwigConstants.LeftDrive.MOTOR1);
			leftDriveMotor2 = new TurtleFakeMotor();

			rightDriveMotor1 = new TurtleVictor888(TurtwigConstants.RightDrive.MOTOR1, true);
			rightDriveMotor2 = new TurtleFakeMotor();

			leftDistance = new TurtleDistanceEncoder(
					TurtwigConstants.LeftDrive.ENCODER_A, TurtwigConstants.LeftDrive.ENCODER_B,
					TurtwigConstants.LeftDrive.ENCODER_RATIO, true
			);

			rightDistance = new TurtleDistanceEncoder(
					TurtwigConstants.RightDrive.ENCODER_A, TurtwigConstants.RightDrive.ENCODER_B,
					TurtwigConstants.RightDrive.ENCODER_RATIO, false
			);
		} else {
			// Blastoise Chassis
			leftDriveMotor1 = new TurtleSpark(BlastoiseConstants.LeftDrive.MOTOR1);
			leftDriveMotor2 = new TurtleTalonSR(BlastoiseConstants.LeftDrive.MOTOR2);

			rightDriveMotor1 = new TurtleSpark(BlastoiseConstants.RightDrive.MOTOR1, true);
			rightDriveMotor2 = new TurtleTalonSR(BlastoiseConstants.RightDrive.MOTOR2, true);

			leftDistance = new TurtleFakeDistanceEncoder();
			rightDistance = new TurtleFakeDistanceEncoder();
		}
	}

	// Fix for bugs with SmartDashboard
	Random r = new Random();

	private TurtleAnalogInput rightJoystick;
	private TurtleAnalogInput leftJoystick;

	private TurtleDigitalInput straightButton;
	private TurtleDigitalInput turnButton;

	private TurtleDigitalInput resetButton;
	

	private TurtleLogger logger;

	/**
	 * Main constructor for BlastoiseChassis
	 * Accepts two TurtleAnalogInputs
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


	/**
	 * @return Left distance
	 */
	public Distance getLeftDistance() {
		return leftDistance.getDistance();
	}

	/**
	 * @return Right distance
	 */
	public Distance getRightDistance() {
		return rightDistance.getDistance();
	}

	public TurtleRotationSensor getOrientationSensor() {
		return orientationSensor;
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
		
		if(resetButton.get() == 1){
			leftDistance.reset();
			rightDistance.reset();
		}
		
		SmartDashboard.putNumber("LeftEncoder", leftDistance.getDistance().getInches());
		SmartDashboard.putNumber("RightEncoder", rightDistance.getDistance().getInches());
	}

    /**
     * Sends raw values directly to the drive system
     */
    public void updateMotors(MotorValue leftPower, MotorValue rightPower) {
        leftDriveMotor1.set(leftPower);
		leftDriveMotor2.set(leftPower);

        rightDriveMotor1.set(rightPower);
		rightDriveMotor2.set(rightPower);

        SmartDashboard.putNumber("RightMotor", TurtleMaths.deadband(rightPower.getValue(), RobotConstants.MOTOR_DEADBAND));
        SmartDashboard.putNumber("LeftMotor", TurtleMaths.deadband(leftPower.getValue(), RobotConstants.MOTOR_DEADBAND));
    }

	/**
	 * Set all drive motors to zero speed
	 */
	public void stopMotors() {
	    updateMotors(MotorValue.zero, MotorValue.zero);
    }
}
