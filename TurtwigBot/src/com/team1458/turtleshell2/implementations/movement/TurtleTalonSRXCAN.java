package com.team1458.turtleshell2.implementations.movement;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team1458.turtleshell2.interfaces.movement.TurtleSmartMotor;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.MotorValue;
import com.team1458.turtleshell2.util.types.Rate;

public class TurtleTalonSRXCAN implements TurtleSmartMotor {
	private final double kRotationRate;
	private final CANTalon v;
	private final boolean isReversed;
	private boolean inDirectControlMode = true;

	public TurtleTalonSRXCAN(int id, boolean isReversed, BrakeMode brakeMode, double kRotationRate) {
		v = new CANTalon(id);
		this.setBrakeMode(brakeMode);
		this.isReversed = isReversed;
		this.brakeMode = brakeMode;
		this.kRotationRate = kRotationRate;
	}

	public TurtleTalonSRXCAN(int id) {
		this(id, false, BrakeMode.BRAKE, 1);
	}

	public TurtleTalonSRXCAN(int id, TurtleTalonSRXCAN master) {
		this(id, false, BrakeMode.BRAKE, 1);
		v.changeControlMode(TalonControlMode.Follower);
		v.set(master.getID());
		this.inDirectControlMode=false;

	}

	@Override
	public void set(MotorValue val) {
		if(!inDirectControlMode) {
			v.changeControlMode(TalonControlMode.PercentVbus);
		}
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
	@Override
	public void setRotationRate(Rate<Angle> rate) {
		v.changeControlMode(TalonControlMode.Speed);
		v.set(kRotationRate * rate.getValue());
		this.inDirectControlMode=false;
	}

	@Override
	public void setBrakeMode(BrakeMode brake) {
		if(brake==BrakeMode.BRAKE) {
			v.enableBrakeMode(true);
		} else if (brake==BrakeMode.COAST) {
			v.enableBrakeMode(false);
		}
	}

}
