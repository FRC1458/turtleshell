package com.team1458.turtleshell.core;

/**
 * Interface for giving the main robot things
 * @author mehnadnerd
 *
 */
public interface TurtleThingGiver {
	public TurtleTeleop giveTeleop();

	public TurtleAutonomous giveAutonomous();

	public TurtlePhysicalRobot givePhysicalRobot();
}
