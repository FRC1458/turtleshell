package com.team1458.turtleshell.component;

public interface TurtleRobotComponent {
	/**
	 * Initialise a component. Will be called once.
	 */
	public abstract void init();
	
	public void teleUpdate();
	
	public void stop();
	
}
