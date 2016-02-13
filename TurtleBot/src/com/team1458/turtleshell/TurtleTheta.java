package com.team1458.turtleshell;

/**
 * An interface for rotation sensors. Ex: gyro, magnetometer, etc.
 * @author mehnadnerd
 *
 */
public interface TurtleTheta extends TurtleSensor {
	/**
	 * Get Continuous theta,
	 * 
	 * @return Angle in degrees, continuous, clockwise = positive
	 */
	public double getContinousTheta();

	/**
	 * get the rate, in degrees/second
	 */
	public double getRate();

	/**
	 * Reset the measurement, so the robot will be facing towards zero
	 */
	public void reset();
}
