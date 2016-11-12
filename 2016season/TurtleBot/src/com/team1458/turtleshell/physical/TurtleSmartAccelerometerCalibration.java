package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.util.TurtleCalibration;

public class TurtleSmartAccelerometerCalibration implements TurtleCalibration {
	private final double pitch;
	private final double roll;
	public TurtleSmartAccelerometerCalibration(double pitch, double roll) {
		this.pitch=pitch;
		this.roll=roll;
	}
	/**
	 * Pitch, roll
	 */
	@Override
	public double[] getValues() {
		return new double[] {pitch,roll};
	}

}
