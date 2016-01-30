package com.team1458.turtleshell;

/**
 * An interface to allow for easier passing of Calibration values. Each type of TurtleTheta should have its own implementation of this.
 * @author mehnaderd
 *
 */
public interface TurtleThetaCalibration {
	/**
	 * Gets the values associated with this TurtleThetaCalibration. the details are implementation-specific.
	 * @return An array of double values, implementation specific.
	 */
	public double[] getValues();
}
