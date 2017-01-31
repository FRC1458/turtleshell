package com.team1458.turtleshell2.movement;

import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.Spark;

public class TurtleSpark implements TurtleMotor {
	private final Spark v;
	private final boolean isReversed;

	public TurtleSpark(int port, boolean isReversed) {
		v = new Spark(port);
		this.isReversed = isReversed;
	}
	
	public TurtleSpark(int port) {
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
