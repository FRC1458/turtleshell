package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.MotorValue;
import com.team1458.turtleshell.TurtleMotor;

import edu.wpi.first.wpilibj.Talon;

public class TurtleTalon implements TurtleMotor {
	private Talon talon;
	private boolean isReversed = false;

	public TurtleTalon(int port) {
		talon = new Talon(port);
	}

	public TurtleTalon(int port, boolean isReversed) {
		this(port);
		this.isReversed = isReversed;
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

}
