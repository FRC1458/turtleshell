package com.team1458.turtleshell;

public interface TurtleMotor {
	/**
	 * Sets the motor power.
	 * 
	 * @param power
	 *            double from -1 to 1, with 1 being full forward and -1 full
	 *            backward.
	 */
	public abstract void set(double power);

	/**
	 * Gets the most recently set motor power.
	 * 
	 * @return The most recently set motor power, from -1 to 1.
	 */
	public abstract double get();

	/**
	 * Checks whether or not this motor is reversed. Reversal should be done
	 * upon construction, so sides are the same.
	 * 
	 * @return Whether or not the motor is reversed.
	 */
	public abstract boolean isReversed();
}
