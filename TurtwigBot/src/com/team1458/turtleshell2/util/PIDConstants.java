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

	/**
	 *
	 * @param kP
	 * @param kI
	 * @param kD
	 */
	public PIDConstants(double kP, double kI, double kD) {
		this.kP = kP;
		this.kI = kI;
		this.kD = kD;
	}
}
