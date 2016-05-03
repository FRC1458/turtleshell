package com.team1458.turtleshell2.interfaces.movement;

import com.team1458.turtleshell2.util.types.Angle;

/**
 * Interface for servos which can be set to a certain rotation.
 * @author mehnadnerd
 *
 */
public interface TurtleServo {
	public void setAngle(Angle a);
	public Angle getAngle();
}
