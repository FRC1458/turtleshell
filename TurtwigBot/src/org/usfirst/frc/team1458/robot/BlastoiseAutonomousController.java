package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.pid.TurtleStraightDrivePID;
import com.team1458.turtleshell2.implementations.sensor.TurtleDistanceEncoder;
import com.team1458.turtleshell2.interfaces.pid.TurtleDualPID;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.Timer;

/**
 * Command-based controller for autonomous mode.
 * Does not actually do anything on it's own, rather it is a utility whose functions must be called in the AutoMode
 * Use only for the chassis drive, not for other mechanisms on the robot
 *
 * @author asinghani
 */
public class BlastoiseAutonomousController {
	BlastoiseChassis chassis;

	public BlastoiseAutonomousController(BlastoiseChassis chassis) {
		this.chassis = chassis;
	}

	/**
	 * Move robot forward/backward for a certain amount of time at a certain speed.
	 * No PID controller or other stabilization methods are used.
	 *
	 * @param millis Amount of time for the robot to drive
	 * @param speed Speed for the robot, between -1 and 1
	 */
	public void moveMillis(long millis, double speed) {
		MotorValue motorValue = new MotorValue(speed);
		chassis.updateMotors(motorValue, motorValue);

		Timer.delay(millis / 1000.0); // WPILib Timer, not java.util.Timer
		chassis.stopMotors();
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
				BlastoiseConstants.StraightDrivePID.PID_CONSTANTS, distance,
				BlastoiseConstants.StraightDrivePID.kLR, BlastoiseConstants.StraightDrivePID.TOLERANCE);

		TurtleDistanceEncoder leftEncoder = chassis.getLeftDistance();
		TurtleDistanceEncoder rightEncoder = chassis.getRightDistance();


		double leftSpeed = 0, rightSpeed = 0, leftDistance, rightDistance;
		MotorValue[] motors;

		while (!pid.atTarget()) {
			leftDistance = leftEncoder.getDistance().getInches();
			rightDistance = rightEncoder.getDistance().getInches();

			motors = pid.newValue(leftDistance, rightDistance, leftSpeed, rightSpeed);
			leftSpeed = .3 * motors[0].getValue() + .7 * speed;
			rightSpeed = .3 * motors[1].getValue() + .7 * speed;

			chassis.updateMotors(new MotorValue(leftSpeed), new MotorValue(rightSpeed));
		}

		chassis.stopMotors();
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
		chassis.updateMotors(left, right);

		Timer.delay(millis / 1000.0); // WPILib Timer, not java.util.Timer
		chassis.stopMotors();
	}

	/**
	 * Turn robot left/right for a certain amount of degrees
	 *
	 * @param degrees Degrees to turn, clockwise
	 * @param speed Speed for the robot, between -1 and 1
	 */
	public void turnDegrees(double degrees, double speed) {

		// Create PID
		//TurtleDualPID pid = new TurtleTurnPID();

		/*TurtleDistanceEncoder leftEncoder = chassis.getLeftDistance();
		TurtleDistanceEncoder rightEncoder = chassis.getRightDistance();


		double leftSpeed = 0, rightSpeed = 0, leftDistance, rightDistance;
		MotorValue[] motors;

		while (!pid.atTarget()) {
			leftDistance = leftEncoder.getDistance().getInches();
			rightDistance = rightEncoder.getDistance().getInches();

			motors = pid.newValue(leftDistance, rightDistance, leftSpeed, rightSpeed);
			leftSpeed = .3 * motors[0].getValue() + .7 * speed;
			rightSpeed = .3 * motors[1].getValue() + .7 * speed;

			chassis.updateMotors(new MotorValue(leftSpeed), new MotorValue(rightSpeed));
		}
*/
		chassis.stopMotors();
	}
}
