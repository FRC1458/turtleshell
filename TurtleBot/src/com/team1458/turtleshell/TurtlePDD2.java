package com.team1458.turtleshell;

import com.team1458.turtleshell.logging.TurtleLogger;

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
	 * @param consti TurtlePIDConstants object holding a p, d, and d2 value.
	 * @param target Target of PID loop, in ticks
	 * @param tolerence Tolerance, in ticks
	 */
	public TurtlePDD2(TurtlePIDConstants consti, double target, double tolerence) {
		this.kP = consti.getKP();
		this.kDD = consti.getKD2();
		this.kD = consti.getKD();
		this.target = target;
		this.tolerence = tolerence;
	}

	/**
	 * @param inputs p,d
	 */
	public MotorValue newValue(double[] inputs) {
		double newValue = kP * (target - inputs[0]) - kD * inputs[1] + kDD * (prevdValue - inputs[1]);
		prevdValue = inputs[1];
		savedpValue = inputs[0];
		//System.out.println("U"+newValue);
		return new MotorValue(newValue);
	}

	public boolean atTarget() {
		return Math.abs(savedpValue - target) < tolerence && prevdValue < tolerence;
	}

}