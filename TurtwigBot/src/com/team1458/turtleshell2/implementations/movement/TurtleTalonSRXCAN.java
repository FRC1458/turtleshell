package com.team1458.turtleshell2.implementations.movement;

import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.CANTalon;


public class TurtleTalonSRXCAN implements TurtleMotor {
	private final CANTalon v;
	private final boolean isReversed;

	public TurtleTalonSRXCAN(int id, boolean isReversed) {
		v = new CANTalon(id);
		this.isReversed = isReversed;
	}
	
	public TurtleTalonSRXCAN(int id) {
		this(id, false);
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
