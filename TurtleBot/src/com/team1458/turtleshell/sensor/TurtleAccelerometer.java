package com.team1458.turtleshell.sensor;

/**
 * Interface for Accelerometers
 * @author mehnadnerd
 *
 */
public interface TurtleAccelerometer {
	/**
	 * Get the acceleration measured by the accelerometer
	 * @return X, Y, Z. Y and Z not guaranteed to exist
	 */
	public double[] getAcceleration();
}
