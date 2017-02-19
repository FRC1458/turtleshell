package com.team1458.turtleshell2.core;

import com.team1458.turtleshell2.movement.TankDrive;
import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.Timer;

/**
 * Command-based controller for autonomous mode. This is an abstract
 * implementation of AutoMode with some utility functions, but it must be
 * extended to be used.
 *
 * @author asinghani
 */
public abstract class SampleAutoMode implements AutoMode {
	protected TankDrive drive;
	protected Logger logger;

	protected TurtleRotationSensor rotationSensor;

	public SampleAutoMode(TankDrive drive, Logger logger, TurtleRotationSensor rotationSensor) {
		this.drive = drive;
		this.logger = logger;
		this.rotationSensor = rotationSensor;
	}

	@Override
	public String getName() {
		return "\"" + getClass().getSimpleName() + "\"";
	}

	@Override
	public String toString() {
		return getName();
	}

	/**
	 * The {@link #auto()} function must be implemented by the class extending
	 * SampleAutoMode
	 */
	@Override
	public abstract void auto();

	/**
	 * Move robot forward/backward for a certain amount of time at a certain
	 * speed. No PID controller or other stabilization methods are used.
	 *
	 * @param millis
	 *            Amount of time for the robot to drive
	 * @param speed
	 *            Speed for the robot, between -1 and 1
	 */
	public void moveMillis(long millis, double speed) {
		MotorValue motorValue = new MotorValue(speed);
		drive.tankDrive(motorValue, motorValue);

		Timer.delay(millis / 1000.0); // WPILib Timer, not java.util.Timer
		drive.stop();
	}

	/**
	 * Turn for a certain amount of time at a certain speed. Positive speeds
	 * represent right, negative represents left turning. No PID controller or
	 * other stabilization methods are used.
	 *
	 * @param millis
	 *            Amount of time for the robot to turn
	 * @param speed
	 *            Speed for the robot, between -1 and 1
	 */
	public void turnMillis(long millis, double speed) {
		MotorValue right = new MotorValue(speed);
		MotorValue left = new MotorValue(-1.0 * speed);
		drive.tankDrive(left, right);

		Timer.delay(millis / 1000.0); // WPILib Timer, not java.util.Timer
		drive.stop();
	}

	public void turnAngle(Angle angle, MotorValue speed, PIDConstants constants, int tolerance) {
		drive.turn(angle, speed, constants, tolerance);
	}

	public void driveStraight(MotorValue speed, Distance distance, PIDConstants constants, int tolerance) {
		PID right = new PID(constants, distance.getCentimetres(), tolerance);
		PID left = new PID(constants, distance.getCentimetres(), tolerance);

		while(!(right.atTarget() && left.atTarget())) {
			MotorValue rightValue = new MotorValue(right.newValue(drive.getRightDistance().getCentimetres()))
					.scale(speed.getValue());
			MotorValue leftValue = new MotorValue(left.newValue(drive.getRightDistance().getCentimetres()))
					.scale(speed.getValue());

			drive.tankDrive(leftValue, rightValue);
		}
		drive.stop();
	}
}
