package com.team1458.turtleshell2.implementations.movement;

import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;

public class TurtleFakeMotor implements TurtleMotor {
	private final boolean isReversed;
	private double value;

	public TurtleFakeMotor(int port, boolean isReversed) {
		this.isReversed = isReversed;
	}
	
	public TurtleFakeMotor(int port) {
		this(port, false);
	}

	public TurtleFakeMotor() {
		this(0, false);
	}

	@Override
	public void set(MotorValue val) {
		value = TurtleMaths.reverseBool(isReversed)*val.getValue();
	}

	@Override
	public MotorValue get() {
		return new MotorValue(TurtleMaths.reverseBool(isReversed)*value);
	}
}
