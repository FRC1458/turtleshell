package com.team1458.turtleshell;

public interface TurtleAutonomous {
	
	/**
	 * Gets the motor powers in an array.
	 * @return An array of doubles for the motors. the first two values should
	 *         be the left and then right motors, but there is no standard for
	 *         the remaining.
	 */
	public double[] getMotors();

	/**
	 * Have the autonomous recalculate what to do. To get meaningful results, this might require sending in sensor values.
	 */
	public void calculate();
	
	/**
	 * Have autonomous recalculate, while sending in sensorvalues at the same time.
	 * @param sensorvalues A double array for all of the sensor values. Each autonomous needs to decide what the standard for it will be with this.
	 */
	public default void calculate(double[] sensorvalues) {
		calculate();
	}
	/**
	 * Initialise autonomous.
	 */
	public void init();
}
