package com.team1458.turtleshell;

public class TurtleStraightDrivePID implements TurtleDualPID {
	private final TurtlePID lPID;
	private final TurtlePID rPID;
	private final double kLR;

	private double lDist;
	private double rDist;
	private double lRate;
	private double rRate;

	/**
	 * Constructs a new TurtleStraightDrivePID
	 * @param kP
	 * @param kD
	 * @param kDD
	 * @param target
	 * @param kLR
	 */
	public TurtleStraightDrivePID(TurtlePIDConstants constants, double target, double kLR) {
		Output.outputNumber("targetLinear", target);
		lPID = new TurtlePDD2(constants, target, 20);
		rPID = new TurtlePDD2(constants, target, 20);
		this.kLR = kLR;
	}

	@Override
	public boolean atTarget() {
		return lPID.atTarget() && rPID.atTarget() && TurtleMaths.absDiff(lDist, rDist)<20;
	}

	/**
	 * 
	 * @param inputs
	 *            Left Encoder, Right Encoder, LeftEncoder Rate, Right Encoder
	 *            Rate
	 * @return Left Motor Value, Right Motor Value
	 */
	@Override
	public MotorValue[] newValue(double[] inputs) {
		Output.outputNumber("lDist", inputs[0]);
		Output.outputNumber("rDist", inputs[1]);
		lDist = inputs[0];
		rDist = inputs[1];
		lRate = inputs[2];
		rRate = inputs[3];
		return new MotorValue[] {
				new MotorValue(lPID.newValue(new double[] { lDist, lRate }).getValue() - kLR * (lDist - rDist)),
				new MotorValue(rPID.newValue(new double[] { rDist, rRate }).getValue() + kLR * (lDist - rDist)) };
	}

}
