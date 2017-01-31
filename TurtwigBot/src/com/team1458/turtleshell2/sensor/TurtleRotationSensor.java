package com.team1458.turtleshell2.sensor;

import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Rate;

/**
 * Interface for sensors or other objects to determine theta (angle)
 * @author mehnadnerd
 *
 */
public interface TurtleRotationSensor {
	/**
	 * Get the continuous rotation measured by the sensor
	 * @return Angle, degrees clockwise
	 */
	public Angle getRotation();
	
	/**
	 * Get the angular rate
	 * @return
	 */
	public Rate<Angle> getRate();
	
	/**
	 * Reset the sensor so the sensor is now pointing towards zero.
	 */
	public void reset();
}
