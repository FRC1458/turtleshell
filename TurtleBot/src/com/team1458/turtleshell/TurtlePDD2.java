package com.team1458.turtleshell;

public class TurtlePDD2 implements TurtlePID {
	protected final double kP;
	protected final double kDD;
	protected final double kD;

	protected double target;
	protected double tolerence;

	protected double prevdValue;
	protected double savedpValue;

	public TurtlePDD2(TurtlePIDConstants consti, double target, double tolerence) {
		this.kP = consti.getKP();
		this.kDD = consti.getKD2();
		this.kD = consti.getKD();
		this.target = target;
		this.tolerence = tolerence;
	}

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