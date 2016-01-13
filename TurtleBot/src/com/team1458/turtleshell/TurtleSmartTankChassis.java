package com.team1458.turtleshell;

public interface TurtleSmartTankChassis extends TurtleTankChassis {
	/*
	 * Drive forward (or backwards) a certain distance.
	 * @param distance Signed distance in decimal inches to drive forward.
	 */
	/*public void driveForward(double distance);*/
	
	/*
	 * Turn a specified amount of degrees clockwise
	 * @param degrees Signed amount of degrees to rotate to clockwise
	 */
	/*public void turn(double degrees);*/
	/**
	 * Get number of ticks the left encoder has gone
	 * @return Signed number of ticks
	 */
	public int getLeftEncoder();
	
	/**
	 * Get number of ticks the right encoder has gone
	 * @return Signed number of ticks
	 */
	public int getRightEncoder();
	
	/**
	 * Get number of degrees robot has rotated clockwise
	 * @return Signed rotation in degrees clockwise
	 */
	public double getTheta();
	
	/**
	 * Give the conversion factor to multiply ticks by to get inches.
	 * @return The conversion factor in the units of in/ticks.
	 */
	public double getTicksToInches();
}
