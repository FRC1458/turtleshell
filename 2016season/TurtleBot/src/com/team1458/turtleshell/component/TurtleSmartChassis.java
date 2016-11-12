package com.team1458.turtleshell.component;

public interface TurtleSmartChassis extends TurtleSmartRobotComponent {
	/**
	 * Set the linear target the robot should drive
	 * @param target The distance in inches forwards or backwards to drive.
	 */
	public void setLinearTarget(double target);
	
	/**
	 * Set the theta target the robot should rotate
	 * @param target The angle in degrees the robot should rotate clockwise
	 */
	public void setThetaTarget(double target);
	
	/**
	 * Checks whether the robot is at its target (theta or linear)
	 * @return
	 */
	public boolean atTarget();
}
