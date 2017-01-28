package com.team1458.turtleshell2.implementations.pid;

import com.team1458.turtleshell2.interfaces.pid.TurtlePID;
import com.team1458.turtleshell2.util.TurtlePIDConstants;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * Implemntation of PID for a Proportional-Derivative-2nd Derivative controller.
 * @author mehnadnerd
 *
 */
public class TurtlePDD2 implements TurtlePID {
	protected final double kP;
	protected final double kDD;
	protected final double kD;

	protected double target;
	protected double tolerence;

	protected double prevdValue;
	protected double savedpValue;

	/**
	 * Create a PDD2 with the given constants, target, and tolerance
	 * @param constants TurtlePIDConstants object holding a p, d, and d2 value.
	 * @param target Target of PID loop, in ticks
	 * @param tolerence Tolerance, in ticks
	 */
	public TurtlePDD2(TurtlePIDConstants constants, double target, double tolerence) {
		this.kP = constants.kP;
		this.kDD = constants.kDD;
		this.kD = constants.kD;
		this.target = target;
		this.tolerence = tolerence;
	}

	/**
	 * @param inputs p, d
	 */
	public MotorValue newValue(double... inputs) {
		double newValue = kP * (target - inputs[0]) - kD * inputs[1] + kDD * (prevdValue - inputs[1]);
		//SmartDashboard.putNumber("kPart", kP * (target - inputs[0]));
		//SmartDashboard.putNumber("dPart", kD * (inputs[1]));
		//SmartDashboard.putNumber("ddPart", kDD * (prevdValue - inputs[1]));
		prevdValue = inputs[1];
		savedpValue = inputs[0];
		return new MotorValue(newValue);
	}

	public boolean atTarget() {
		return Math.abs(savedpValue - target) < tolerence && prevdValue < (tolerence/3);// TODO FIX
	}

}