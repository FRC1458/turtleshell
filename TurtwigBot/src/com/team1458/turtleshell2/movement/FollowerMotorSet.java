package com.team1458.turtleshell2.movement;

import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * Immutable set of motors which follow each other.
 * @author asinghani
 */
public class FollowerMotorSet extends MotorSet {
	private TurtleFollowerMotor masterMotor;
	private MotorValue value = MotorValue.zero;

	/**
	 * First motor is the master, other motors follow it.
	 * @param motors
	 */
	public FollowerMotorSet(TurtleFollowerMotor... motors) {
		masterMotor = motors[0];
		for(TurtleFollowerMotor motor : motors) {
			if(motor == masterMotor) continue;
			motor.follow(masterMotor.getID());
		}
	}

	@Override
	public void set(MotorValue val) {
		this.value = val;
		masterMotor.set(val);
	}

	@Override
	public MotorValue get() {
		return value;
	}

	@Override
	public void set(int motor, MotorValue val) {
		throw new UnsupportedOperationException("Motors cannot be accessed individually with FollowerMotorSet");
	}

	@Override
	public MotorValue get(int motor) {
		throw new UnsupportedOperationException("Motors cannot be accessed individually with FollowerMotorSet");
	}

	@Override
	public TurtleMotor getMotor(int motor) {
		throw new UnsupportedOperationException("Motors cannot be accessed individually with FollowerMotorSet");
	}

}
