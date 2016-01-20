package com.team1458.turtleshell;

public class TurtleTurnPID implements TurtleDualPID {
	private final TurtlePID lPID;
	private final TurtlePID rPID;
	private final double kLR;
	
	private double lDist;
	private double rDist;
	private double lRate;
	private double rRate;

	/**
	 * 
	 * @param kP
	 * @param kDD
	 * @param kD
	 * @param target
	 * @param kLR
	 * @param wheeldiameter
	 * @param wheelbase Distance between wheels, diameter of turning circle
	 */
	public TurtleTurnPID(double kP, double kD, double kDD, double target, double kLR, double wheeldiameter, double wheelbase) {
		lPID = new TurtlePDD2(kP, kD, kDD, target/360*(wheeldiameter/wheelbase));
		rPID = new TurtlePDD2(kP, kD, kDD, -target/360*(wheeldiameter/wheelbase));
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
		lDist = inputs[0];
		rDist = inputs[1];
		lRate = inputs[2];
		rRate = inputs[3];
		//lDist and rDist are added because lDist should equal -rDist
		return new MotorValue[] {
				new MotorValue(lPID.newValue(new double[] { lDist, lRate }).getValue() - kLR * (lDist + rDist)),
				new MotorValue(rPID.newValue(new double[] { rDist, rRate }).getValue() + kLR * (lDist + rDist)) };
	}

}