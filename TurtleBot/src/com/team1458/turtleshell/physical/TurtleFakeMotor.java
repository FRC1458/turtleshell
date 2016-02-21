package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.movement.TurtleMotor;
import com.team1458.turtleshell.util.MotorValue;

/**
 * A wrapper for a Victor to get it to implement TurtleMotor.
 * @author mehnadnerd
 *
 */
public class TurtleFakeMotor implements TurtleMotor {
	private double fakepower;
	private boolean isReversed;

	public TurtleFakeMotor(int port) {
		fakepower = 0;
	}

	public TurtleFakeMotor(int port, boolean isReversed) {
		this(port);
		this.isReversed = isReversed;
	}

	@Override
	public void set(MotorValue power) {
		fakepower=(isReversed ? -1 : 1) * power.getValue();
	}

	@Override
	public MotorValue get() {
		return new MotorValue((isReversed ? -1 : 1) * fakepower);
	}

	@Override
	public boolean isReversed() {
		return isReversed;
	}

	@Override
	public void stop() {
		fakepower=0;
		
	}
}
