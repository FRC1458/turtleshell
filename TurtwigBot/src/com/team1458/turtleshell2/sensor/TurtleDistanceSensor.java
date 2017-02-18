package com.team1458.turtleshell2.sensor;

import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.Rate;

/**
 * Interface for distance sensor.
 * @author mehnadnerd
 *
 */
public interface TurtleDistanceSensor {
	/**
	 * 
	 * @return Distance in decimal inches.
	 */
	public Distance getDistance();

	/**
	 * @return Signed velocity in inches/second.
	 */
	public Rate<Distance> getVelocity();

	/**
	 * Reset the distance sensor so the current position is zero.
	 */
	public void reset();
}
