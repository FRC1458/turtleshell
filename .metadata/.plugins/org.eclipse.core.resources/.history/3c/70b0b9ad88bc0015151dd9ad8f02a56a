package com.team1458.turtleshell;

public abstract class TurtleChassis extends TurtleRobotComponent {
	/**
	 * Drive straight forward (or backwards)
	 * @param power signed double -1 to 1, -1 full backwards 1 full forwards
	 */
	public abstract void straightDrive(double power);
	/**
	 * Turn clockwise (or anticlockwise)
	 * @param power The power to turn at, signed double -1 to 1
	 */
	public abstract void turn(double power);
	
	/**
	 * Send commands to all chassis (drive) motors at once. Left, right, other (no standard)
	 * @param allMotorPowers
	 */
	public abstract void allDrive(double[] allMotorPowers);
}
