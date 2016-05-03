package com.team1458.turtleshell2.implementations.movement;

import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.Victor;

public class TurtleVictor888 implements TurtleMotor {
	private final Victor v;
	private final boolean isReversed;

	public TurtleVictor888(int port, boolean isReversed) {
		v = new Victor(port);
		this.isReversed = isReversed;
	}
	
	public TurtleVictor888(int port) {
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
