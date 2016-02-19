package com.team1458.turtleshell.util;

/**
 * An interface to allow for easier passing of Calibration values. Each type of TurtleCalibratable should have its own implementation of this.
 * @author mehnaderd
 *
 */
public interface TurtleCalibration {
	/**
	 * Gets the values associated with this TurtleCalibration. the details are implementation-specific.
	 * @return An array of double values, implementation specific.
	 */
	public double[] getValues();
}
