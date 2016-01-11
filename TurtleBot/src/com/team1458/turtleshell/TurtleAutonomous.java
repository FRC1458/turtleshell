package com.team1458.turtleshell;

public interface TurtleAutonomous {
	
	/**
	 * Gets the motor powers in an array.
	 * @return An array of doubles for the motors. the first two values should
	 *         be the right and then left motors, but there is no standard for
	 *         the remaining.
	 */
	public double[] getMotors();

	/**
	 * Have the autonomous recalculate what to do. To get meaningful results, this might require sending in sensor values.
	 */
	public void calculate();
}
