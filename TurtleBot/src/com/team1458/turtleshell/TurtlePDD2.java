package com.team1458.turtleshell;

public class TurtlePDD2 implements TurtlePID {
	protected final double kP;
	protected final double kDD;
	protected final double kD;

	protected double target;

	protected double prevdValue;
	protected double savedpValue;

	public TurtlePDD2(double kP, double kD, double kDD, double target) {
		this.kP = kP;
		this.kDD = kDD;
		this.kD = kD;
		this.target = target;
	}

	public MotorValue newValue(double[] inputs) {
		double newValue = kP * (target - inputs[0]) - kD * inputs[1] + kDD * (prevdValue - inputs[1]);
		prevdValue = inputs[1];
		savedpValue = inputs[0];
		//System.out.println("U"+newValue);
		return new MotorValue(newValue);
	}

	public boolean atTarget() {
		return TurtleMaths.absDiff(target,savedpValue) < 30 && prevdValue < 30;
	}

}