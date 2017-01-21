package com.team1458.turtleshell2.util.types;

/**
 * Unit-extending class representing an interval of time
 * @author mehnadnerd
 *
 */
public final class Time implements Unit {
	private final double value;

	public static final Time one = new Time(1);

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
