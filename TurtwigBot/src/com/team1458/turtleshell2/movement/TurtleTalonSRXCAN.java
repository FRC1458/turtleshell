package com.team1458.turtleshell2.movement;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.MotorValue;
import com.team1458.turtleshell2.util.types.Rate;

public class TurtleTalonSRXCAN implements TurtleSmartMotor, TurtleFollowerMotor {
	private final double kRotationRate;
	private final CANTalon v;
	private final boolean isReversed;
	private boolean inDirectControlMode = true;
	private double lastValue;

	/**
	 * Talon SRX implementation over CANbus
	 * 
	 * @param id
	 *            The CAN ID (0-63) for the Talon SRX
	 * @param isReversed
	 *            false is direct, true if should be reversed
	 * @param brakeMode
	 *            BRAKE or COAST
	 * @param kRotationRate
	 *            A constant for converting the rate (In Degrees/second) to the
	 *            units used in the Talon SRX
	 */
	public TurtleTalonSRXCAN(int id, boolean isReversed, BrakeMode brakeMode, double kRotationRate) {
		v = new CANTalon(id);
		this.setBrakeMode(brakeMode);
		this.isReversed = isReversed;
		this.kRotationRate = kRotationRate;
	}

	/**
	 * Talon SRX implementation over CANbus
	 * 
	 * @param id
	 *            The CAN ID (0-63) for the Talon SRX
	 * @param isReversed
	 *            false is direct, true if should be reversed
	 * @param brakeMode
	 *            BRAKE or COAST
	 */
	public TurtleTalonSRXCAN(int id, boolean isReversed) {
		this(id, isReversed, BrakeMode.BRAKE, 1);
	}

	/**
	 * Talon SRX implementation over CANbus
	 * 
	 * @param id
	 *            The CAN ID (0-63) for the Talon SRX
	 */
	public TurtleTalonSRXCAN(int id) {
		this(id, false, BrakeMode.BRAKE, 1);
	}

	/**
	 * Talon SRX implementation over CANbus; Constructor for a TalonSRX which
	 * should follow another
	 * 
	 * @param id
	 *            The CAN ID (0-63) for the Talon SRX
	 * @param master
	 *            The master TalonSRX which it should follow
	 */
	public TurtleTalonSRXCAN(int id, TurtleTalonSRXCAN master) {
		this(id, false, BrakeMode.BRAKE, 1);
		this.follow(master);
	}

	@Override
	public void follow(TurtleFollowerMotor master) {
		v.changeControlMode(TalonControlMode.Follower);
		v.set(master.getID());
		this.inDirectControlMode = false;
	}
	
	/**
	 * Making it so can be set with the ID, not just the object
	 * @param masterID
	 */
	public void follow(int masterID) {
		v.changeControlMode(TalonControlMode.Follower);
		v.set(masterID);
		this.inDirectControlMode = false;
	}

	@Override
	public void set(MotorValue val) {
		// TODO: See if this can be eliminated. This is to prevent a possible
		// error where it could be in a different control mode, leading to it
		// not responding to
		if (!inDirectControlMode) {
			v.changeControlMode(TalonControlMode.PercentVbus);
			this.inDirectControlMode = true;
		}

		lastValue = val.getValue();
		v.set(TurtleMaths.reverseBool(isReversed) * val.getValue());
	}

	@Override
	public MotorValue get() {
		return new MotorValue(TurtleMaths.reverseBool(isReversed) * v.get());
	}

	public double getRaw() {
		return lastValue;
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
	public double getCurrent() {
		return v.getOutputCurrent();
	}

	public double getOutputVoltage() {
		return v.getOutputVoltage();
	}

	@Override
	public void setRotationRate(Rate<Angle> rate) {
		v.changeControlMode(TalonControlMode.Speed);
		v.set(kRotationRate * rate.getValue());
		this.inDirectControlMode = false;
	}

	@Override
	public void setBrakeMode(BrakeMode brake) {
		if (brake == BrakeMode.BRAKE) {
			v.enableBrakeMode(true);
		} else if (brake == BrakeMode.COAST) {
			v.enableBrakeMode(false);
		}
	}

}
