package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.component.TurtleMotor;
import com.team1458.turtleshell.util.MotorValue;

import edu.wpi.first.wpilibj.Victor;

/**
 * A wrapper for a Victor to get it to implement TurtleMotor.
 * @author mehnadnerd
 *
 */
public class TurtleVictor implements TurtleMotor {
	private Victor victor;
	private boolean isReversed;

	public TurtleVictor(int port) {
		victor = new Victor(port);
	}

	public TurtleVictor(int port, boolean isReversed) {
		this(port);
		this.isReversed = isReversed;
	}

	@Override
	public void set(MotorValue power) {
		victor.set((isReversed ? -1 : 1) * power.getValue());
	}

	@Override
	public MotorValue get() {
		return new MotorValue((isReversed ? -1 : 1) * victor.get());
	}

	@Override
	public boolean isReversed() {
		return isReversed;
	}

	@Override
	public void stop() {
		victor.set(0);
		
	}
}
