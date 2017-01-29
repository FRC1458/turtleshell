package com.team1458.turtleshell2.util;

/**
 * Container class for moving around PID constants
 * @author mehnadnerd
 */
public class PIDConstants {
	/**
	 * Proportional constant
	 */
	public final double kP;

	/**
	 * Integral Constant
	 */
	public final double kI;

	/**
	 * Derivative constant
	 */
	public final double kD;

	public static final PIDConstants zeroConstants = new PIDConstants(0, 0, 0);

	/**
	 * @param kP
	 * @param kI
	 * @param kD
	 */
	public PIDConstants(double kP, double kI, double kD) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
	}

	/**
	 * @param kP
	 */
	public PIDConstants(double kP) {
		this(kP, 0, 0);
	}
}
