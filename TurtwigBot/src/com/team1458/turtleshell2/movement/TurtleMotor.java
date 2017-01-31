package com.team1458.turtleshell2.movement;

import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * Interface for all movable objects which are given a power.
 * @author mehnadnerd
 *
 */
public interface TurtleMotor {
	/**
	 * Set the motor to a certain MotorValue
	 * @param val
	 */
	public void set(MotorValue val);
	/**
	 * Get the most recently set MotorValue
	 * @return
	 */
	public MotorValue get();
}
