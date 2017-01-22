package com.team1458.turtleshell2.implementations.drive;

import com.team1458.turtleshell2.implementations.pid.TurtlePDD2;
import com.team1458.turtleshell2.implementations.sensor.fake.TurtleFakeDistanceEncoder;
import com.team1458.turtleshell2.interfaces.Chassis;
import com.team1458.turtleshell2.interfaces.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.interfaces.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.TurtlePIDConstants;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1458.robot.Robot;

/**
 * Represents a full tank drive
 * @author asinghani
 */
public class TankDrive implements Chassis {

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
	MotorValue turnSpeed = MotorValue.zero;
	Angle zeroAngle = Angle.zero;
	boolean turning = false;
	TurtlePDD2 turnPid;
	MotorValue motorSpeed = MotorValue.zero;


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
			if(turnPid.atTarget() && RobotState.isOperatorControl() && RobotState.isEnabled()){
				stopMotors();
				turning = false;
			} else {

				turnPid.newValue(rotationSensor.getRotation().getDegrees(), rotationSensor.getRate().getValue());

				leftSpeed = speed * motors[0].getValue();
				rightSpeed = speed * motors[1].getValue();

				SmartDashboard.putString("StatusOfPidLeft", leftDistance+"/"+distance);
				SmartDashboard.putString("StatusOfPidRight", rightDistance+"/"+distance);

				chassis.updateMotors(new MotorValue(leftSpeed), new MotorValue(rightSpeed));
			}

			double diff = (navX.getYaw().getDegrees() - turningBase);
			diff = 90-diff;
			if(Math.abs(Math.abs(diff)) > 4){
				double rotate = -0.8*(diff / 90.0);
				MotorValue rotateValue = new MotorValue(rotate);
				SmartDashboard.putNumber("RotateValue", rotate);
				updateMotors(rotateValue, rotateValue.invert());
				SmartDashboard.putNumber("Yaw", navX.getYaw().getDegrees());
				SmartDashboard.putNumber("CompassHeading", navX.getCompassHeading().getDegrees());
				SmartDashboard.putNumber("FusedHeading", navX.getFusedHeading().getDegrees());
			} else {
			}
			return;
		}
	}

	public void turn(Angle angle, MotorValue speed, TurtlePIDConstants constants) {
		this.turning = true;
		this.turnSpeed = speed;
		this.zeroAngle = rotationSensor.getRotation();
		turnPid = new TurtlePDD2(constants, angle.getDegrees(), 5);
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
}
