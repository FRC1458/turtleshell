package com.team1458.turtleshell2.movement;

import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.TalonSRX;

/**
 * Implementation for control of a TalonSRX over PWM
 * @author mehnadnerd
 *
 */
public class TurtleTalonSRXPWM implements TurtleMotor {
	private final TalonSRX v;
	private final boolean isReversed;

	public TurtleTalonSRXPWM(int port, boolean isReversed) {
		v = new TalonSRX(port);
		this.isReversed = isReversed;
	}
	
	public TurtleTalonSRXPWM(int port) {
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
