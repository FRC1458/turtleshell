package com.team1458.turtleshell2.implementations.movement;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team1458.turtleshell2.interfaces.movement.TurtleSmartMotor;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;

public class TurtleTalonSRXCAN implements TurtleSmartMotor {
	private final CANTalon v;
	private final boolean isReversed;
	private final boolean brakeMode;

	public TurtleTalonSRXCAN(int id, boolean isReversed, boolean brakeMode) {
		v = new CANTalon(id);
		v.enableBrakeMode(brakeMode);
		this.isReversed = isReversed;
		this.brakeMode = brakeMode;
	}

	public TurtleTalonSRXCAN(int id) {
		this(id, false, true);
	}

	public TurtleTalonSRXCAN(int id, TurtleTalonSRXCAN master) {
		this(id,false,true);
		v.changeControlMode(TalonControlMode.Follower);
		v.set(master.getID());

	}

	@Override
	public void set(MotorValue val) {
		v.set(TurtleMaths.reverseBool(isReversed) * val.getValue());
	}

	@Override
	public MotorValue get() {
		return new MotorValue(TurtleMaths.reverseBool(isReversed) * v.get());
	}

	public int getID() {
		return v.getDeviceID();
	}

	// TODO add stall detection using voltage detection

	@Override
	public boolean isStalling() {
		return false;
	}
}
