
/**
 * A class holding helpful static methods for maths-related things.
 * 
 * @author mehnadnerd
 *
 */
public class TurtleMaths {
	/**
	 * Fit the double to a specified range. Equivalent to:
	 * (toFit > max ? max : toFit < min ? min: toFit)
	 * 
	 * @param toFit
	 *            number to fit in range
	 * @param min
	 *            minimum value for toFit
	 * @param max
	 *            Maximum value for toFit
	 * @return
	 */
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
	 *            1st value
	 * @param b
	 *            2nd value
	 * @return The absolute difference of the two, equal to Math.abs(a-b)
	 */
	public static double absDiff(double a, double b) {
		return Math.abs(a - b);
	}

	/**
	 * Returns the bigger of the two double values.
	 * Equivalent to:
	 * (a > b ? a : b)
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static double biggerOf(double a, double b) {
		return (a > b ? a : b);
	}

	/**
	 * Returns the bigger of the two int values
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int biggerOf(int a, int b) {
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
			this.rngA = maxA - minA;
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

	/**
	 * Returns the smaller of the two int values
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	public static int smallerOf(int a, int b) {
		return (a < b ? a : b);
	}

	/**
	 * Normalises a slope to between zero and one. Used in arcade drive code
	 * 
	 * @param m
	 *            The slope to normalise
	 * @return The slope normalised to (0, 1], it will be absolute valued and
	 *         recipocaled as nessecary
	 */
	public static double normaliseM(double m) {
		m = Math.abs(m);
		if (m > 1) {
			m = 1 / m;
		}
		if (Double.isNaN(m) || Double.isInfinite(m)) {
			m = 0;
		}
		return m;
	}

	/**
	 * Calculates percent Error
	 * 
	 * @param actual
	 *            The ideal or "correct" value
	 * @param measured
	 *            The measured value
	 * @return
	 */
	public static double percentError(double actual, double measured) {
		return TurtleMaths.absDiff(actual, measured) / actual;
	}

	/**
	 * Mathematical way for joystick deadband, if |input| < deadband, returns
	 * zero, otherwise returns input
	 * 
	 * @param input
	 *            number to check against deadband
	 * @param deadbandRange
	 *            Range at which should round to zero
	 * @return Input or zero
	 */
	public static double deadband(double input, double deadbandRange) {
		if (Math.abs(input) < deadbandRange) {
			return 0;
		}

		return input;

	}

	/**
	 * Average a series of doubles
	 * 
	 * @param num
	 *            The doubles to average
	 * @return The arithmetic mean of the numbers
	 */
	public static double avg(double... num) {
		double sum = 0;
		for (double d : num) {
			sum += d;
		}
		return sum / num.length;
	}

	/**
	 * Scales a value quadratically while preserving signs, i.e 1 -> 1, .5 ->
	 * .25, and -1 -> -1
	 * 
	 * @param toScale
	 *            The value to be scaled
	 * @return The scaled value
	 */
	public static double quadraticScale(double toScale) {
		return toScale * Math.abs(toScale);
	}

	/**
	 * Scales a value in a logistic step format, the exact function approximates
	 * a linear function but has logistic-like steps in every interval of 1/2
	 *
	 * Function is:
	 * y = x - sin(4pi*x)/4pi
	 * 
	 * @param toScale
	 *            The number to be scaled
	 * @return The scaled number
	 */
	public static double logisticStepScale(double toScale) {
		return toScale - (Math.sin(4 * Math.PI * toScale) / (4 * Math.PI));
	}

	/**
	 * Convenience method that yields -1 if b is true, 1 otherwise
	 * 
	 * @return
	 */
	public static int reverseBool(boolean isReversed) {
		return isReversed ? -1 : 1;
	}

	/**
	 * Hiding the constructor so cannot be initialised
	 */
	private TurtleMaths() {
	};

}