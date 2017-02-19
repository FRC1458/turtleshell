package com.team1458.turtleshell2.movement;

import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.pid.SimpleTurnPID;
import com.team1458.turtleshell2.pid.StraightDrivePID;
import com.team1458.turtleshell2.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.sensor.fake.TurtleFakeDistanceEncoder;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;
import com.team1458.turtleshell2.util.types.Tuple;
import edu.wpi.first.wpilibj.RobotState;

/**
 * Represents a tank drive
 * @author asinghani
 */
public class TankDrive implements DriveTrain {

	protected TurtleMotor leftMotor;
	protected TurtleMotor rightMotor;

	protected TurtleDistanceSensor leftEncoder;
	protected TurtleDistanceSensor rightEncoder;

	// This must be a gyro or compass sensor
	protected TurtleRotationSensor rotationSensor;

	protected DriveState state = DriveState.NONE;

	private PIDConstants straightDriveConstants;
	private PIDConstants straightDriveTurnConstants;
	private PIDConstants turnConstants;
	private double kLR;

	public TankDrive(TurtleMotor leftMotor, TurtleMotor rightMotor,
					 TurtleDistanceSensor leftEncoder, TurtleDistanceSensor rightEncoder,
					 TurtleRotationSensor rotationSensor, PIDConstants straightDriveConstants,
					 PIDConstants straightDriveTurnConstants, PIDConstants turnConstants, double kLR) {
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.leftEncoder = leftEncoder;
		this.rightEncoder = rightEncoder;
		this.rotationSensor = rotationSensor;

		this.straightDriveConstants = straightDriveConstants;
		this.straightDriveTurnConstants = straightDriveTurnConstants;
		this.turnConstants = turnConstants;
		this.kLR = kLR;
	}

	public TankDrive(TurtleMotor leftMotor, TurtleMotor rightMotor,
					 TurtleRotationSensor rotationSensor, PIDConstants straightDriveConstants,
					 PIDConstants straightDriveTurnConstants, PIDConstants turnConstants, double kLR) {
		this(leftMotor, rightMotor,
				new TurtleFakeDistanceEncoder(), new TurtleFakeDistanceEncoder(),
				rotationSensor, straightDriveConstants,
				straightDriveTurnConstants, turnConstants, kLR);
	}

	/**
	 * Drive forward for given distance. Only to be used in autonomous, THIS WILL BLOCK THE MAIN THREAD.
	 * @param distance
	 * @param speed
	 * @param constants
	 * @param tolerance
	 */
	@Override
	public void driveForward(Distance distance, MotorValue speed, PIDConstants constants, Distance tolerance) {
		state = DriveState.STRAIGHT_DRIVING;
		StraightDrivePID straightDrivePID = new StraightDrivePID(distance, tolerance,
				straightDriveConstants, straightDriveConstants, straightDriveTurnConstants, kLR);

		while (isAutonomous() && !straightDrivePID.atTarget()) {
			Tuple<MotorValue, MotorValue> motorValues = straightDrivePID.newValue(getLeftDistance(), getRightDistance(),
					rotationSensor.getRotation());
			setMotors(motorValues, speed);
		}

		stop();
		state = DriveState.NONE;
	}

	/**
	 * Turn for given angle. Only to be used in autonomous, THIS WILL BLOCK THE MAIN THREAD.
	 * @param angle
	 * @param speed
	 * @param constants
	 * @param tolerance
	 */
	@Override
	public void turnRight(Angle angle, MotorValue speed, PIDConstants constants, Angle tolerance) {
		state = DriveState.TURNING;
		rotationSensor.reset();
		SimpleTurnPID turnPID = new SimpleTurnPID(angle, tolerance, constants);

		while(isAutonomous() && !turnPID.atTarget()) {
			Tuple<MotorValue, MotorValue> motorValues = turnPID.newValue(rotationSensor.getRotation());
			setMotors(motorValues, speed);
		}
	}

	@Override
	public void driveRight(Distance distance, MotorValue speed, PIDConstants constants, Distance tolerance) {
		throw new UnsupportedOperationException("Tank Drive cannot move sideways. Use a Mecanum or Swerve Drive.");
	}

	public Distance getLeftDistance() {
		return leftEncoder.getDistance();
	}

	public Distance getRightDistance() {
		return rightEncoder.getDistance();
	}

	/**
	 * Sends raw values directly to the drive system
	 */
	public void tankDrive(MotorValue leftPower, MotorValue rightPower) {
		setMotors(leftPower, rightPower);
		state = DriveState.MANUAL;
	}

	/**
	 * Sends raw values directly to the drive system
	 */
	private void setMotors(MotorValue leftPower, MotorValue rightPower) {
		leftMotor.set(leftPower);
		rightMotor.set(rightPower);
	}

	private void setMotors(Tuple<MotorValue, MotorValue> values, MotorValue speed) {
		setMotors(values.x.scale(speed), values.y.scale(speed));
	}

	/**
	 * Set all drive motors to zero speed
	 */
	@Override
	public void stop() {
		tankDrive(MotorValue.zero, MotorValue.zero);
		state = DriveState.MANUAL;
	}

	public void reset() {
		leftEncoder.reset();
		rightEncoder.reset();
		rotationSensor.reset();
	}

	public static boolean isAutonomous() {
		return RobotState.isAutonomous();
	}

	public enum DriveState {
		MANUAL, TURNING, STRAIGHT_DRIVING, NONE
	}
}
