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

	/**
	 * A class to help with moving values between different ranges, i.e. 0-100
	 * to 3-7
	 * 
	 * @author mehnadnerd
	 *
	 */
	public class RangeShifter {
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
		 * @param toShift The number to shift
		 * @return The shifted value.
		 */
		public double shift(double toShift) {
			return minB + (rngB / rngA) * (toShift - minA);
		}
	}

}
