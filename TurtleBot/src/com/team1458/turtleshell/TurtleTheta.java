package com.team1458.turtleshell;

/**
 * An interface for rotation sensors. Ex: gyro, magnetometer, etc.
 * @author mehnadnerd
 *
 */
public interface TurtleTheta extends TurtleSensor {
	/**
	 * Get Continuous theta,
	 * 
	 * @return Angle in degrees, continuous, clockwise = positive
	 */
	public double getContinousTheta();

	/**
	 * get the rate, in degrees/second
	 */
	public double getRate();

	/**
	 * Reset the measurement, so the robot will be facing towards zero
	 */
	public void reset();

	/**
	 * Gets the current calibration of the TurtleTheta, or null if it doesn't
	 * need to be calibrated.
	 * 
	 * @return
	 */
	public TurtleThetaCalibration getCalibration();

	/**
	 * Sets the calibration of the TurtleTheta
	 * 
	 * @param calibration
	 *            The calibration to be set
	 */
	public void setCalibration(TurtleThetaCalibration calibration);

	/**
	 * Generate a calibration from measured data. The TurtleTheta MUST be able
	 * to accept such a calibration without throwing errors, although accuracy
	 * with it is not guaranteed.
	 * 
	 * @return The generated calibration.
	 */
	public TurtleThetaCalibration generateCalibration();
}
