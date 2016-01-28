package com.team1458.turtleshell;

public interface TurtleTheta extends TurtleSensor {
	/**
	 * Get Continuous theta, 
	 * @return Angle in degrees, continuous
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
