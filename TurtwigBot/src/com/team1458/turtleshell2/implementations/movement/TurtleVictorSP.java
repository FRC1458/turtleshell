package com.team1458.turtleshell2.implementations.movement;

import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.VictorSP;

public class TurtleVictorSP implements TurtleMotor {
	private final VictorSP v;
	private final boolean isReversed;

	public TurtleVictorSP(int port, boolean isReversed) {
		v = new VictorSP(port);
		this.isReversed = isReversed;
	}
	
	public TurtleVictorSP(int port) {
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
