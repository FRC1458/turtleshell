package com.team1458.turtleshell;

public interface TurtleTheta extends TurtleSensor {
	/**
	 * Get Continous theta, 
	 * @return Angle in degrees, continous
	 */
	public double getContinousTheta();
	
	/**
	 * Reset the measurement
	 */
	public void reset();
}
