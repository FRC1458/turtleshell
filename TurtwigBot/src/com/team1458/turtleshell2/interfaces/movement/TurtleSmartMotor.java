package com.team1458.turtleshell2.interfaces.movement;

import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Rate;

public interface TurtleSmartMotor extends TurtleMotor {
	
	/**
	 * Set the motor to rotate at a particular rate.
	 * This differs from the standard power in that is specifies a rotation rate, in degrees per second.
	 * The smartmotor should have a method to configure the inputs and constants, etc for this to work.
	 * @param rate Rate to hold at, in degrees per second.
	 */
	public void setRotationRate(Rate<Angle> rate);
	
	/**
	 * Set whether the SmartMotor should be in brake or coast mode.
	 * @param brake A BrakeMode enum, either BRAKE or COAST
	 */
	public void setBrakeMode(BrakeMode brake);
	
	public static enum BrakeMode {
		BRAKE, COAST;
	}
	
	
	
	

}
