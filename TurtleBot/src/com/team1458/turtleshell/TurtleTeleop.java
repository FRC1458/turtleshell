package com.team1458.turtleshell;

public interface TurtleTeleop {
	/**
	 * Give the physical robot to teleop, for it to do as it wishes.
	 * @param physicalRobot The physical robot to pass in
	 */
	public void giveRobot(TurtlePhysicalRobot physicalRobot);
	
	public void tick();
}
