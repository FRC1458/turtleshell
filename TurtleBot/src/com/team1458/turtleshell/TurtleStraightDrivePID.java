package com.team1458.turtleshell;

public class TurtleStraightDrivePID implements TurtleDualPID {
	private final TurtlePID lPID;
	private final TurtlePID rPID;
	private final double kLR;

	public TurtleStraightDrivePID(double kP, double kD, double kDD, double target, double kLR) {
		lPID = new TurtlePDD2(kP, kD, kDD, target);
		rPID = new TurtlePDD2(kP, kD, kDD, target);
		this.kLR = kLR;
	}

	@Override
	public boolean atTarget() {
		return lPID.atTarget() && rPID.atTarget();
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
		double lValue = lPID.newValue(new double[] { inputs[0], inputs[2] }).getValue();
		double rValue = rPID.newValue(new double[] { inputs[1], inputs[3] }).getValue();
		double diff = lValue-rValue;
		return new MotorValue[] { new MotorValue(lValue-kLR*diff), new MotorValue(rValue+kLR*diff)};
	}

}
