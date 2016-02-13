package com.team1458.turtleshell;

public interface TurtleCalibratable {
	/**
	 * Gets the current calibration of the TurtleTheta, or null if it doesn't
	 * need to be calibrated.
	 * 
	 * @return
	 */
	public TurtleCalibration getCalibration();

	/**
	 * Sets the calibration of the TurtleTheta
	 * 
	 * @param calibration
	 *            The calibration to be set
	 */
	public void setCalibration(TurtleCalibration calibration);

	/**
	 * Generate a calibration from measured data. The TurtleTheta MUST be able
	 * to accept such a calibration without throwing errors, although accuracy
	 * with it is not guaranteed.
	 * 
	 * @return The generated calibration.
	 */
	public TurtleCalibration generateCalibration();
}
