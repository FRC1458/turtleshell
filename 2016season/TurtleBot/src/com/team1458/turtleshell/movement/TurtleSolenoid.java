package com.team1458.turtleshell.movement;

/**
 * An interface for solenoids, whether electrical or pneumatic
 * @author mehnadnerd
 *
 */
public interface TurtleSolenoid extends TurtleMovable {
	/**
	 * Set whether the solenoid is extended or not.
	 * @param isExtended true if extended, false if not.
	 */
	public void set(boolean isExtended);
	/**
	 * Get whether the solenoid is extended
	 * @return true if extended, false if not
	 */
	public boolean get();
}
