package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.drive.TankDrive;
import com.team1458.turtleshell2.implementations.pid.TurtlePDD2;
import com.team1458.turtleshell2.implementations.pid.TurtleStraightDrivePID;
import com.team1458.turtleshell2.interfaces.AutoMode;
import com.team1458.turtleshell2.interfaces.pid.TurtleDualPID;
import com.team1458.turtleshell2.interfaces.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

/**
 * Command-based controller for autonomous mode.
 * This is an abstract implementation of AutoMode with some utility functions, but it must be extended to be used.
 *
 * @author asinghani
 */
public abstract class BlastoiseAutoMode implements AutoMode {
	protected TankDrive drive;
	protected TurtleLogger logger;

	protected TurtleRotationSensor rotationSensor;

	public BlastoiseAutoMode(TankDrive drive, TurtleLogger logger, TurtleRotationSensor rotationSensor) {
		this.drive = drive;
		this.logger = logger;
		this.rotationSensor = rotationSensor;
	}

	@Override
	public String getName() {
		return "\""+getClass().getSimpleName()+"\"";
	}

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * The {@link #auto()} function must be implemented by the class extending BlastoiseAutoMode
	 */
	@Override
	public abstract void auto();

	/**
	 * Move robot forward/backward for a certain amount of time at a certain speed.
	 * No PID controller or other stabilization methods are used.
	 *
	 * @param millis Amount of time for the robot to drive
	 * @param speed Speed for the robot, between -1 and 1
	 */
	public void moveMillis(long millis, double speed) {
		MotorValue motorValue = new MotorValue(speed);
		drive.updateMotors(motorValue, motorValue);

		Timer.delay(millis / 1000.0); // WPILib Timer, not java.util.Timer
		drive.stopMotors();
	}

	/**
	 * Move robot forward/backward for a certain amount of distance at a certain speed.
	 * Uses {@link TurtleStraightDrivePID} with PID controller but no Gyro Sensor
	 *
	 * @param distance Distance for the robot to drive, in inches
	 * @param speed Speed for the robot, between -1 and 1
	 */
	public void moveDistance(double distance, double speed) {

		// Create PID
		TurtleDualPID pid = new TurtleStraightDrivePID(
				RobotConstants.StraightDrivePID.PID_CONSTANTS, distance,
				RobotConstants.StraightDrivePID.kLR, RobotConstants.StraightDrivePID.TOLERANCE);


		double leftSpeed = 0, rightSpeed = 0, leftDistance, rightDistance;
		MotorValue[] motors;

		while (!pid.atTarget() && RobotState.isAutonomous()) {
			leftDistance = drive.getLeftDistance().getInches();
			rightDistance = drive.getRightDistance().getInches();

			motors = pid.newValue(leftDistance, rightDistance, leftSpeed, rightSpeed);
			leftSpeed = speed * motors[0].getValue();
			rightSpeed = speed * motors[1].getValue();
			
			drive.updateMotors(new MotorValue(leftSpeed), new MotorValue(rightSpeed));
		}

		drive.stopMotors();
	}

	/**
	 * Turn for a certain amount of time at a certain speed.
	 * Positive speeds represent right, negative represents left turning.
	 * No PID controller or other stabilization methods are used.
	 *
	 * @param millis Amount of time for the robot to turn
	 * @param speed Speed for the robot, between -1 and 1
	 */
	public void turnMillis(long millis, double speed) {
		MotorValue right = new MotorValue(speed);
		MotorValue left = new MotorValue(-1.0 * speed);
		drive.updateMotors(left, right);

		Timer.delay(millis / 1000.0); // WPILib Timer, not java.util.Timer
		drive.stopMotors();
	}

	/**
	 * Turn robot left/right for a certain amount of degrees using gyro
	 *
	 * @param degrees Degrees to turn
	 * @param speed Speed for the robot, between 0 and 1
	 */
	public void turnDegrees(double degrees, double speed) {

		MotorValue turnSpeed = new MotorValue(TurtleMaths.fitRange(speed, 0, 1));

		TurtlePDD2 turnPID = new TurtlePDD2(RobotConstants.TurnPID.PID_CONSTANTS,
				degrees + rotationSensor.getRotation().getDegrees(), RobotConstants.TurnPID.TOLERANCE);

		while(!turnPID.atTarget()){
			MotorValue motorValue =
					turnPID.newValue(rotationSensor.getRotation().getDegrees(), rotationSensor.getRate().getValue())
							.mapToSpeed(turnSpeed.getValue());

			drive.updateMotors(motorValue, motorValue.invert());
		}

		drive.stopMotors();
	}
}
