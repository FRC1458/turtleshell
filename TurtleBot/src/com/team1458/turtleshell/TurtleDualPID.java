package com.team1458.turtleshell;

public interface TurtleDualPID {
	/**
	 * Checks whether or not it is at a target
	 * @return True if at target and ready to execute
	 */
	public boolean atTarget();
	
	/**
	 * Calculate and return new value for pid
	 * @param inputs array of sensor values to input
	 * @return array storing motor values to use
	 */
	public MotorValue[] newValue(double[] inputs);
}
