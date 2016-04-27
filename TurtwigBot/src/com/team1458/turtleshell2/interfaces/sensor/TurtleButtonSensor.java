package com.team1458.turtleshell2.interfaces.sensor;

/**
 * Interface for sensors like limit switches, determines if something is pressed
 * @author mehnadnerd
 *
 */
public interface TurtleButtonSensor {
	/**
	 * 
	 * @return Whether or not it is pressed, true = pressed
	 */
	public boolean get();

}
