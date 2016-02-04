package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.TurtleThetaCalibration;

/**
 * Calibration for the Xtrinsic Magnetometer. Implements TurtleThetaCalibration.
 * @author mehnadnerd
 *
 */
public class TurtleXtrinsicMagnetometerCalibration implements TurtleThetaCalibration {
	private double[] values;
	@Override
	public double[] getValues() {
		return values;
	}
	public TurtleXtrinsicMagnetometerCalibration(double xMin, double xMax, double yMin, double yMax) {
		values = new double[]{xMin,xMax,yMin,yMax};
	}

}
