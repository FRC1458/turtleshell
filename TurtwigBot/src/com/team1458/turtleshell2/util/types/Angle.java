package com.team1458.turtleshell2.util.types;

/**
 * An immutable class for moving around angles. Uses degrees.
 * 
 * @author mehnadnerd
 *
 */
public final class Angle implements Unit {
	private final double value;

	public static final Angle zero = new Angle(0);

	/**
	 * Create an angle with a certain amount of degrees
	 * 
	 * @param degrees
	 */
	public Angle(double degrees) {
		value = degrees;
	}
	
	public static Angle createRevolutions(double revolutions) {
		return new Angle(revolutions*360);
	}

	public static Angle createRadians(double radians) {
		return new Angle(Math.toDegrees(radians));
	}

	public static Angle createDegrees(double degrees) {
		return new Angle(degrees);
	}

	/**
	 * Get the value in degrees
	 * 
	 * @return
	 */
	@Override
	public double getValue() {
		return value;
	}

	/**
	 * Get the value in radians
	 * 
	 * @return
	 */
	public double getRadians() {
		return Math.toRadians(value);
	}

	/**
	 * Get the value in degrees
	 * 
	 * @return
	 */
	public double getDegrees() {
		return value;
	}
	
	public double getRevolutions() {
		return value/360;
	}

	/**
	 * Get the negative of the value. Useful for turning the robot the other way
	 */
	public Angle invert() {
		return new Angle(value * -1);
	}
}
