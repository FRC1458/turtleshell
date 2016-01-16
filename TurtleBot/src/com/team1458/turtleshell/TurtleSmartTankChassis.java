package com.team1458.turtleshell;

public interface TurtleSmartTankChassis extends TurtleTankChassis {

	/**
	 * Get number of ticks the left encoder has gone
	 * @return Signed number of ticks
	 */
	public abstract int getLeftEncoder();
	
	/**
	 * Get number of ticks the right encoder has gone
	 * @return Signed number of ticks
	 */
	public abstract int getRightEncoder();
	
	/**
	 * GEt number of ticks all encoders have gone
	 * @return left, right, other
	 */
	public abstract int[] getAllEncoders();
	
	/**
	 * Get number of degrees robot has rotated clockwise
	 * @return Signed rotation in degrees clockwise
	 */
	public abstract double getTheta();
	
	/**
	 * Give the conversion factor to multiply ticks by to get inches.
	 * @return The conversion factor in the units of in/ticks.
	 */
	public abstract double getTicksToInches();
}
