package com.team1458.turtleshell2.movement;

import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.sensor.fake.TurtleFakeDistanceEncoder;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Represents a full tank drive
 * @author asinghani
 */
public class TankDriveChassis implements DriveTrain {

	/**
	 * Drive
	 */
	private MotorSet leftDrive;
	private MotorSet rightDrive;

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


	public TankDriveChassis(MotorSet leftDrive, MotorSet rightDrive,
	                        TurtleDistanceSensor leftDistance, TurtleDistanceSensor rightDistance, TurtleRotationSensor rotationSensor) {
		this.leftDrive = leftDrive;
		this.rightDrive = rightDrive;
		this.leftDistance = leftDistance;
		this.rightDistance = rightDistance;
		this.rotationSensor = rotationSensor;
	}

	public TankDriveChassis(MotorSet leftDrive, MotorSet rightDrive, TurtleRotationSensor rotationSensor) {
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
								.mapToSpeed(turnSpeed);

				updateMotors(motorValue, motorValue.invert());
				System.out.println(motorValue.getValue()+" motor value");
				
				SmartDashboard.putNumber("PID MOTOR VALUE", motorValue.getValue());
			}
			return;
		} else if(drivingStraight) {
			MotorValue motorValue =
					new MotorValue(straightDrivePID.newValue(rotationSensor.getRotation().getDegrees()))
							.mapToSpeed(straightDriveSpeed);

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
		System.out.println("started turning: "+angle.getDegrees()+" speed="+speed.getValue());
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
		SmartDashboard.putNumber("Left Thingy", leftPower.getValue());
		SmartDashboard.putNumber("Right Thingy", rightPower.getValue());
		leftDrive.set(leftPower);
		rightDrive.set(rightPower);
	}

	/**
	 * Set all drive motors to zero speed
	 */
	@Override
	public void stopMotors() {
		updateMotors(MotorValue.zero, MotorValue.zero);
	}

	public void resetEncoders() {
		leftDistance.reset();
		rightDistance.reset();
		rotationSensor.reset();
	}
}