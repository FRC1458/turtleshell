package com.team1458.turtleshell2.movement;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.MotorValue;
import com.team1458.turtleshell2.util.types.Rate;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurtleTalonSRXCAN implements TurtleSmartMotor, TurtleFollowerMotor {
	private final double kRotationRate;
	private final CANTalon v;
	private final boolean isReversed;
	private BrakeMode brakeMode;
	private boolean inDirectControlMode = true;

	public TurtleTalonSRXCAN(int id, boolean isReversed, BrakeMode brakeMode, double kRotationRate) {
		v = new CANTalon(id);
		this.setBrakeMode(brakeMode);
		this.isReversed = isReversed;
		this.brakeMode = brakeMode;
		this.kRotationRate = kRotationRate;
	}

	public TurtleTalonSRXCAN(int id, boolean isReversed) {
		this(id, isReversed, BrakeMode.BRAKE, 1);
	}
	
	public TurtleTalonSRXCAN(int id) {
		this(id, false, BrakeMode.BRAKE, 1);
	}

	public TurtleTalonSRXCAN(int id, TurtleTalonSRXCAN master) {
		this(id, false, BrakeMode.BRAKE, 1);
		v.changeControlMode(TalonControlMode.Follower);
		v.set(master.getID());
		this.inDirectControlMode = false;

	}

	@Override
	public void follow(int masterId) {
		v.changeControlMode(TalonControlMode.Follower);
		v.set(masterId);
		this.inDirectControlMode = false;
	}

	@Override
	public void set(MotorValue val) {
		System.out.println(val.getValue());
		SmartDashboard.putNumber("Thingy1234", val.getValue());
		if(!inDirectControlMode) {
			v.changeControlMode(TalonControlMode.PercentVbus);
		}
		v.set(TurtleMaths.reverseBool(isReversed) * val.getValue());
	}

	@Override
	public MotorValue get() {
		return new MotorValue(TurtleMaths.reverseBool(isReversed) * v.get());
	}

	@Override
	public int getID() {
		return v.getDeviceID();
	}

	@Override
	public boolean isStalling() {
		return false;
	}

	@Override
	public void setRotationRate(Rate<Angle> rate) {
		v.changeControlMode(TalonControlMode.Speed);
		v.set(kRotationRate * rate.getValue());
		this.inDirectControlMode = false;
	}

	@Override
	public void setBrakeMode(BrakeMode brake) {
		if(brake == BrakeMode.BRAKE) {
			v.enableBrakeMode(true);
		} else if (brake == BrakeMode.COAST) {
			v.enableBrakeMode(false);
		}
	}

}
