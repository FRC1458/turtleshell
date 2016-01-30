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
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double biggerOf(double a, double b) {
		return (a > b ? a : b);
	}

	public static int biggerOf(int a , int b) {
		return (a > b ? a : b);
	}
	
	
	/**
	 * A class to help with moving values between different ranges, i.e. 0-100
	 * to 3-7
	 * 
	 * @author mehnadnerd
	 *
	 */
	public static class RangeShifter {
		private final double minA;
		private final double minB;
		private final double rngA;
		private final double rngB;

		/**
		 * Construct a new RangeShifter
		 * 
		 * @param minA
		 *            Minimum point of first range
		 * @param maxA
		 *            Maximum point of first range
		 * @param minB
		 *            Minimum point of second range
		 * @param maxB
		 *            Maximum point of second range
		 */
		public RangeShifter(double minA, double maxA, double minB, double maxB) {
			this.minA = minA;
			this.rngA = maxA - minB;
			this.minB = minB;
			this.rngB = maxB - minB;
		}

		/**
		 * Use the RangeShifter to actually shift numbers.
		 * 
		 * @param toShift
		 *            The number to shift
		 * @return The shifted value.
		 */
		public double shift(double toShift) {
			return minB + (rngB / rngA) * (toShift - minA);
		}
	}

	/**
	 * Rounds a double to a certain number of places past the decimal point
	 * 
	 * @param toRound
	 *            The double to round
	 * @param decimalPlaces
	 *            The number of digits past the decimal point to keep, negative
	 *            numbers are supported.
	 * @return
	 */
	public static double round(double toRound, int decimalPlaces) {
		toRound *= Math.pow(10, decimalPlaces);
		toRound = Math.round(Math.round(toRound));
		toRound /= Math.pow(10, decimalPlaces);
		return toRound;
	}

	/**
	 * Returns the smallest of two double values
	 * 
	 * @param a
	 * @param b
	 * @return The smaller of the two values, or b if they are equal or such
	 */
	public static double smallerOf(double a, double b) {
		return (a < b ? a : b);
	}

	public static int smallerOf(int a, int b){
		return (a < b ? a : b);
	}

	public static int makeReasonableMin (int i) {
		if (i > 60000) {
			return Integer.MIN_VALUE;
		}
		return i;
	}
	public static int makeReasonableMax (int i) {
		if (i < -60000) {
			return Integer.MAX_VALUE;
		}
		return i;

	}

}
