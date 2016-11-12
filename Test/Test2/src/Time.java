
/**
 * Unit-extending class representing an interval of time
 * @author mehnadnerd
 *
 */
public final class Time implements Unit {
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
