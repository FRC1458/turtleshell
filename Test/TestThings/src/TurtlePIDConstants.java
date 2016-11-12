
/**
 * Container class for moving around PID constants
 * @author mehnadnerd
 *
 */
public class TurtlePIDConstants {
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
	 * Double derivative constant
	 */
	public final double kDD;
	
	public TurtlePIDConstants(double kP, double kI, double kD, double kDD) {
		this.kP=kP;
		this.kI=kI;
		this.kD=kD;
		this.kDD=kDD;
	}

}
