package com.team1458.turtleshell.base;

public interface TurtleTeleop {
	/**
	 * Give the physical robot to teleop, for it to do as it wishes.
	 * 
	 * @param physicalRobot
	 *            The physical robot to pass in
	 */
	public void giveRobot(TurtlePhysicalRobot physicalRobot);

	/**
	 * Tick the teleop, have it do any appropriate things it needs to do.
	 * Updating the physical robot is done parallel to this, so it doesn't need
	 * to do that update.
	 */
	public void tick();
}
