package com.team1458.turtleshell.vision;

public class VisionMaths {
	/**
	 * Constant for converting y values to theta
	 */
	public final static double yViewConstant = 289.27079243662;
	/**
	 * Constant for converting x values to theta
	 */
	public final static double xViewConstant = 479.1800255;
	
	public static double yToTheta(double y) {
		return Math.acos(y/yViewConstant);
	}
	public static double xToTheta(double x) {
		return Math.acos(x/xViewConstant);
	}
}
