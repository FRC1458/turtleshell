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
	 * @param kD
	 * @param kDD
	 * @param target In degrees
	 * @param kLR
	 * @param wheeldiameter Diameter of wheels
	 * @param wheelbase Distance between wheels, diameter of turning circle
	 */
	public TurtleTurnPID(double kP, double kD, double kDD, double target, double kLR, double wheeldiameter, double wheelbase) {
		lPID = new TurtlePDD2(kP, kD, kDD, target*(wheelbase/wheeldiameter));
		rPID = new TurtlePDD2(kP, kD, kDD, -target*(wheelbase/wheeldiameter));
		this.kLR = kLR;
		Output.outputNumber("target", target*(wheelbase/wheeldiameter));
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
		Output.outputNumber("lDist", lDist);
		Output.outputNumber("rDist", rDist);
		//lDist and rDist are added because lDist should equal -rDist
		return new MotorValue[] {
				new MotorValue(lPID.newValue(new double[] { lDist, lRate }).getValue() - kLR * (lDist + rDist)),
				new MotorValue(rPID.newValue(new double[] { rDist, rRate }).getValue() + kLR * (lDist + rDist)) };
	}

}