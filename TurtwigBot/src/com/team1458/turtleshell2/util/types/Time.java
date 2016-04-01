package com.team1458.turtleshell2.util.types;

public class Time implements Unit {
	private final double value;

	/**
	 * Create a time, with the amount in seconds
	 */
	public Time(double seconds) {
		value = seconds;
	}

	@Override
	public double getValue() {
		return value;
	}
}
