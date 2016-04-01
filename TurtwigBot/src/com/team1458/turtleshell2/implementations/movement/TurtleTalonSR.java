package com.team1458.turtleshell2.implementations.movement;

import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.Talon;

public class TurtleTalonSR implements TurtleMotor {
	private final Talon v;
	private final boolean isReversed;

	public TurtleTalonSR(int port, boolean isReversed) {
		v = new Talon(port);
		this.isReversed = isReversed;
	}
	
	public TurtleTalonSR(int port) {
		this(port, false);
	}

	@Override
	public void set(MotorValue val) {
		v.set(TurtleMaths.reverseBool(isReversed)*val.getValue());

	}

	@Override
	public MotorValue get() {
		return new MotorValue(TurtleMaths.reverseBool(isReversed)*v.get());
	}

}
