package com.team1458.turtleshell2.implementations.drive;

import com.team1458.turtleshell2.implementations.pid.PID;
import com.team1458.turtleshell2.implementations.sensor.fake.TurtleFakeDistanceEncoder;
import com.team1458.turtleshell2.interfaces.DriveTrain;
import com.team1458.turtleshell2.interfaces.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.interfaces.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Represents a full tank drive
 * @author asinghani
 */
public class TankDrive implements DriveTrain {

	/**
	 * Drive
	 */
	private TurtleMotorSet leftDrive;
	private TurtleMotorSet rightDrive;

	/**
	 * Encoders
	 */
	private TurtleDistanceSensor leftDistance;
	private TurtleDistanceSensor rightDistance;

	/**
	 * Others
	 */
	private TurtleRotationSensor rotationSensor;

	/**
	 * Turning
	 */
	boolean turning = false;
	MotorValue turnSpeed = MotorValue.zero;
	PID turnPID;

	/**
	 * Straight Driving
	 */
	boolean drivingStraight = false;
	MotorValue straightDriveSpeed = MotorValue.zero;
	PID straightDrivePID;


	public TankDrive(TurtleMotorSet leftDrive, TurtleMotorSet rightDrive,
	                 TurtleDistanceSensor leftDistance, TurtleDistanceSensor rightDistance, TurtleRotationSensor rotationSensor) {
		this.leftDrive = leftDrive;
		this.rightDrive = rightDrive;
		this.leftDistance = leftDistance;
		this.rightDistance = rightDistance;
		this.rotationSensor = rotationSensor;
	}

	public TankDrive(TurtleMotorSet leftDrive, TurtleMotorSet rightDrive, TurtleRotationSensor rotationSensor) {
		this.leftDrive = leftDrive;
		this.rightDrive = rightDrive;
		this.leftDistance = new TurtleFakeDistanceEncoder();
		this.rightDistance = new TurtleFakeDistanceEncoder();
		this.rotationSensor = rotationSensor;
	}

	public void teleUpdate() {
		/**
		 * Turning during Teleop
		 */
		if(turning){
			if(turnPID.atTarget()){
				System.out.println(rotationSensor.getRotation().getDegrees()+" deg");
				stopMotors();
				turning = false;
				System.err.println("Finished Turning");
			} else {
				MotorValue motorValue =
						new MotorValue(turnPID.newValue(rotationSensor.getRotation().getDegrees()))
								.mapToSpeed(turnSpeed.getValue());

				updateMotors(motorValue, motorValue.invert());
				System.out.println(motorValue.getValue()+" motor value");
				
				SmartDashboard.putNumber("PID MOTOR VALUE", motorValue.getValue());
			}
			return;
		} else if(drivingStraight) {
			MotorValue motorValue =
					new MotorValue(straightDrivePID.newValue(rotationSensor.getRotation().getDegrees()))
							.mapToSpeed(straightDriveSpeed.getValue());

			updateMotors(motorValue, motorValue);
		}
	}

	public void driveStraight(MotorValue speed, PIDConstants constants) {
		this.drivingStraight = true;
		this.straightDriveSpeed = speed;
		this.straightDrivePID = new PID(constants, rotationSensor.getRotation().getDegrees(), 0);
	}

	public void stopDrivingStraight() {
		this.drivingStraight = false;
		stopMotors();
	}

	public void turn(Angle angle, MotorValue speed, PIDConstants constants) {
		this.turning = true;
		this.turnSpeed = speed;
		rotationSensor.reset();
		this.turnPID = new PID(constants, angle.getDegrees(), 5);
	}

	public void stopTurn() {
		this.turning = false;
		stopMotors();
	}

	public boolean isTurning() {
		return turning;
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
		leftDrive.set(leftPower);
		rightDrive.set(rightPower);
	}

	/**
	 * Set all drive motors to zero speed
	 */
	public void stopMotors() {
		updateMotors(MotorValue.zero, MotorValue.zero);
	}

	public void resetEncoders() {
		leftDistance.reset();
		rightDistance.reset();
		rotationSensor.reset();
	}
}
