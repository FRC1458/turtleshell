package com.team1458.turtleshell;

public interface TurtleRobotComponent extends TurtleUpdatable {
	/**
	 * Initialise a component. Will be called once.
	 */
	public void init();
	
	public void stop();
	
}
