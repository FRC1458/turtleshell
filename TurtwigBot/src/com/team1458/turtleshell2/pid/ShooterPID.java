package com.team1458.turtleshell2.pid;

import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;

public class ShooterPID extends PID {
	/**
	 * Constant power to adjust with, should be whatever is approximately the
	 * target speed
	 */
	private final MotorValue openLoop;

	public ShooterPID(PIDConstants constants, double target, double deadband, MotorValue openLoop) {
		super(constants, target, deadband);
		this.openLoop = openLoop;
	}

	@Override
	public double newValue(double value) {//Cannot be less than zero, and openLoop is added.
		return new MotorValue(TurtleMaths.biggerOf(super.newValue(value) + openLoop.getValue(), 0)).getValue();

	}

}
