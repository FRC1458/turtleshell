package com.team1458.turtleshell;

public class TurtleTurnPID implements TurtleDualPID {
	private final double target;
	private final TurtlePID lPID;
	private final TurtlePID rPID;

	private final TurtlePID gPID;

	private final double kGW;

	private double lDist;
	private double rDist;
	private double lRate;
	private double rRate;
	private double gyroTheta;

	/**
	 * 
	 * @param kP
	 * @param kD
	 * @param kDD
	 * @param target
	 *            In degrees
	 * @param kG
	 * @param wheeldiameter
	 *            Diameter of wheels
	 * @param wheelbase
	 *            Distance between wheels, diameter of turning circle
	 */
	public TurtleTurnPID(double kP, double kD, double kDD, double target,
			double kGW, double wheeldiameter, double wheelbase, double kGP,
			double kGD, double kGDD) {
		this.target = target;
		lPID = new TurtlePDD2(kP, kD, kDD, target * (wheelbase / wheeldiameter));
		rPID = new TurtlePDD2(kP, kD, kDD, -target
				* (wheelbase / wheeldiameter));

		gPID = new TurtlePDD2(kGP, kGD, kGDD, target);

		this.kGW = kGW;
		Output.outputNumber("target", target * (wheelbase / wheeldiameter));
	}

	@Override
	public boolean atTarget() {
		return lPID.atTarget() && rPID.atTarget();
	}

	/**
	 * 
	 * @param inputs
	 *            Left Encoder, Right Encoder, LeftEncoder Rate, Right Encoder
	 *            Rate, Gyro
	 * @return Left Motor Value, Right Motor Value
	 */
	@Override
	public MotorValue[] newValue(double[] inputs) {
		lDist = inputs[0];
		rDist = inputs[1];
		lRate = inputs[2];
		rRate = inputs[3];
		gyroTheta = inputs[4];
		Output.outputNumber("lDist", lDist);
		Output.outputNumber("rDist", rDist);
		// lDist and rDist are added because lDist should equal -rDist0
		return new MotorValue[] {
				new MotorValue((1 - kGW)
						* lPID.newValue(new double[] { lDist, lRate })
								.getValue() + kGW * (target - gyroTheta)),
				new MotorValue((1 - kGW)
						* rPID.newValue(new double[] { rDist, rRate })
								.getValue() - kGW * (target - gyroTheta)) };
	}

}