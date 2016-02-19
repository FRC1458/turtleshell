package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.movement.TurtleMotor;
import com.team1458.turtleshell.util.MotorValue;

import edu.wpi.first.wpilibj.CANTalon;

/**
 * A wrapper for CANTalon to get it to implement TurtleMotor.
 * @author mehnadnerd
 *
 */
public class TurtleCANTalon implements TurtleMotor {
	private CANTalon talon;
	private boolean isReversed = false;
	
	public TurtleCANTalon(int digitalID) {
		talon = new CANTalon(digitalID);
	}
	public TurtleCANTalon(int digitalID, boolean isReversed) {
		talon = new CANTalon(digitalID);
		this.isReversed=isReversed;
	}
	
	@Override
	public void set(MotorValue power) {
		talon.set((isReversed ? -1 : 1) * power.getValue());

	}

	@Override
	public MotorValue get() {
		return new MotorValue((isReversed ? -1 : 1) * talon.get());
	}

	@Override
	public boolean isReversed() {
		return isReversed;
	}
	@Override
	public void stop() {
		talon.set(0);
		
	}

}
