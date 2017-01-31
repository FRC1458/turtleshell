package com.team1458.turtleshell2.movement;

import com.team1458.turtleshell2.util.types.MotorValue;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Immutable set of motors. Allows motors to be treated as one or several motors.
 * @author asinghani
 */
public class MotorSet implements TurtleMotor {
	private final ArrayList<TurtleMotor> motorSet;
	private MotorValue value = MotorValue.zero;

	public MotorSet(TurtleMotor... motors) {
		motorSet = new ArrayList<>(Arrays.asList(motors));
	}

	@Override
	public void set(MotorValue val) {
		this.value = val;

		for(TurtleMotor motor : motorSet){
			motor.set(val);
		}
	}

	public void set(int motor, MotorValue val) {
		motorSet.get(motor).set(val);
	}

	@Override
	public MotorValue get() {
		return value;
	}

	public MotorValue get(int motor) {
		return motorSet.get(motor).get();
	}

	public TurtleMotor getMotor(int motor) {
		return motorSet.get(motor);
	}
}
