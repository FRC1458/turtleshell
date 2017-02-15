package com.team1458.turtleshell2.movement;

import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.sensor.fake.TurtleFakeDistanceEncoder;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.RobotState;

/**
 * Represents a full tank drive
 * @author asinghani
 */
public class TankDriveChassis implements DriveTrain {

	// Drive
	private MotorSet leftDrive;
	private MotorSet rightDrive;

	// Encoders
	private TurtleDistanceSensor leftDistance;
	private TurtleDistanceSensor rightDistance;

	// Others
	private TurtleRotationSensor rotationSensor;

	// Driving
	MotorValue speed = MotorValue.zero;
	PID drivePID;
	Angle turnAngle;
	Distance moveDistance;

	private DriveState state = DriveState.NONE;

	public TankDriveChassis(MotorSet leftDrive, MotorSet rightDrive,
	                        TurtleDistanceSensor leftDistance, TurtleDistanceSensor rightDistance,
							TurtleRotationSensor rotationSensor) {
		this.leftDrive = leftDrive;
		this.rightDrive = rightDrive;
		this.leftDistance = leftDistance;
		this.rightDistance = rightDistance;
		this.rotationSensor = rotationSensor;
	}

	public TankDriveChassis(MotorSet leftDrive, MotorSet rightDrive, TurtleRotationSensor rotationSensor) {
		this(leftDrive, rightDrive, new TurtleFakeDistanceEncoder(), new TurtleFakeDistanceEncoder(), rotationSensor);
	}

	public void update() {
		if(state == DriveState.TURNING){
			if(drivePID.atTarget()){
				state = DriveState.MANUAL;
				stop();
			}

			MotorValue motorValue = new MotorValue(drivePID.newValue(rotationSensor.getRotation().getDegrees()))
							.mapToSpeed(speed);
			updateMotorsInternal(motorValue, motorValue.invert());

			return;
		} else if(state == DriveState.STRAIGHT_DRIVING) {
			MotorValue motorValue =
					new MotorValue(drivePID.newValue(rotationSensor.getRotation().getDegrees()))
							.mapToSpeed(speed);

			updateMotorsInternal(motorValue, motorValue);
		}
	}

	public void driveStraight(MotorValue speed, PIDConstants constants) {
		state = DriveState.STRAIGHT_DRIVING;
		this.speed = speed;
		this.drivePID = new PID(constants, rotationSensor.getRotation().getDegrees(), 0);

		if(isAutonomous()) {
			while(isAutonomous() && state == DriveState.STRAIGHT_DRIVING) {
				update();
			}
		}
	}

	public void turn(Angle angle, MotorValue speed, PIDConstants constants, int tolerance) {
		state = DriveState.TURNING;
		rotationSensor.reset();
		this.speed = speed;
		this.drivePID = new PID(constants, angle.getDegrees(), tolerance);

		if(isAutonomous()) {
			while(isAutonomous() && state == DriveState.TURNING) {
				update();
			}
		}
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

	/**
	 * Sends raw values directly to the drive system
	 */
	public void updateMotors(MotorValue leftPower, MotorValue rightPower) {
		updateMotorsInternal(leftPower, rightPower);
		state = DriveState.MANUAL;
	}

	/**
	 * Sends raw values directly to the drive system
	 */
	private void updateMotorsInternal(MotorValue leftPower, MotorValue rightPower) {
		leftDrive.set(leftPower);
		rightDrive.set(rightPower);
	}

	/**
	 * Set all drive motors to zero speed
	 */
	@Override
	public void stop() {
		updateMotors(MotorValue.zero, MotorValue.zero);
		state = DriveState.MANUAL;
	}

	public void reset() {
		leftDistance.reset();
		rightDistance.reset();
		rotationSensor.reset();
	}

	public static boolean isAutonomous() {
		return RobotState.isAutonomous();
	}

	public enum DriveState {
		MANUAL, TURNING, STRAIGHT_DRIVING, NONE
	}
}
