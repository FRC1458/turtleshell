package com.team1458.turtleshell.sensor;

public interface TurtleLimitSwitch extends TurtleSensor {
	/**
	 * 
	 * @return whether or not the limit switch is pressed
	 */
	public boolean isPressed();
}
