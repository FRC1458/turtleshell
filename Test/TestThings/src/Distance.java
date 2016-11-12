
/**
 * An immutable class for moving around distances. Uses inches :(
 * 
 * @author mehnadnerd
 *
 */
public final class Distance implements Unit {
	private final double value;

	/**
	 * Create an angle with a certain amount of degrees
	 * 
	 * @param degrees
	 */
	public Distance(double inches) {
		value = inches;
	}

	public static Distance createInches(double inches) {
		return new Distance(inches);
	}

	public static Distance createFeet(double feet) {
		return new Distance(feet/12);
	}
	
	public static Distance createCentimetres(double cm) {
		return new Distance(cm/2.54);
	}
	
	public static Distance createMetres(double metres) {
		return new Distance(metres/0.0254);
	}

	/**
	 * Get the value in inches
	 * 
	 * @return
	 */
	@Override
	public double getValue() {
		return value;
	}

	/**
	 * Get the value in inches
	 * 
	 * @return
	 */
	public double getInches() {
		return value;
	}

	/**
	 * Get the value in feet
	 * 
	 * @return
	 */
	public double getFeet() {
		return value*12;
	}
	
	/**
	 * Get the value in feet
	 * 
	 * @return
	 */
	public double getCentimetres() {
		return value*2.54;
	}
	
	/**
	 * Get the value in metres
	 * 
	 * @return
	 */
	public double getMetres() {
		return value*0.0254;
	}
}
