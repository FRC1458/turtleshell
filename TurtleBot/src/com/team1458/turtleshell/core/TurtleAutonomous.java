package com.team1458.turtleshell.core;

/**
 * An interface that all autonomous programs should implement, robot control is handed over to autonomous.
 * @author mehnadnerd
 *
 */
public interface TurtleAutonomous {
	/**
	 * Give the autonomous the physicalRobot
	 * @param physicalRobot The physical robot
	 */
	public void giveRobot(TurtlePhysicalRobot physicalRobot);
	/**
	 * Do the auto.
	 */
	public void doAuto();
	
}
