package com.team1458.turtleshell;

public class TurtleMaths {
	public static double fitRange(double toFit, double min, double max) {
		if (toFit > max) {
			return max;
		}
		if (toFit < min) {
			return min;
		}
		return toFit;
	}

	/**
	 * Returns the absolute difference between the two numbers
	 * 
	 * @param a
	 * @param b
	 * @return The absolute difference of the two, equal to Math.abs(a-b)
	 */
	public static double absDiff(double a, double b) {
		return Math.abs(a - b);
	}

	/**
	 * Returns the bigger of the two double values.
	 * @param a
	 * @param b
	 * @return
	 */
	public static double biggerOf(double a, double b) {
		return (a > b ? a : b);
	}
}
