package com.team1458.turtleshell.sensor;

public interface TurtleSmartAccelerometer extends TurtleAccelerometer {
	/**
	 * Returns the direction that is down
	 * 
	 * @return A double array with two values, pitch (with up being positive, in
	 *         degrees) and then roll (anticlockwise being positive, in degrees,
	 *         discontinous)
	 */
	public double[] getDown();
}
