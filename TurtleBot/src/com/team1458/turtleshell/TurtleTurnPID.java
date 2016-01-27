package com.team1458.turtleshell;

public class TurtleTurnPID implements TurtleDualPID {
	//private final double target;
	private final TurtlePID lPID;
	private final TurtlePID rPID;

	private final TurtlePID gPID;

	private final double kGW;

	private double lDist;
	private double rDist;
	private double lRate;
	private double rRate;
	private double gyroTheta;
	private double gyroRate;
	private double kLR;

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
	public TurtleTurnPID(TurtlePIDConstants encoderConstants, double target,
			double kGW, double wheeldiameter, double wheelbase, TurtlePIDConstants gyroConstants, double kLR) {
		//this.target = target;
		lPID = new TurtlePDD2(encoderConstants, target * (wheelbase / wheeldiameter), 20);
		rPID = new TurtlePDD2(encoderConstants, -target* (wheelbase / wheeldiameter), 20);
		gPID = new TurtlePDD2(gyroConstants, target, 3);

		this.kLR = kLR;
		this.kGW = kGW;
		Output.outputNumber("targetTheta", target * (wheelbase / wheeldiameter));
	}

	@Override
	public boolean atTarget() {
		return gPID.atTarget();
	}

	/**
	 * 
	 * @param inputs
	 *            Left Encoder, Right Encoder, LeftEncoder Rate, Right Encoder
	 *            Rate, Gyro Theta, Gyro weight
	 * @return Left Motor Value, Right Motor Value
	 */
	@Override
	public MotorValue[] newValue(double[] inputs) {
		lDist = inputs[0];
		rDist = inputs[1];
		lRate = inputs[2];
		rRate = inputs[3];
		gyroTheta = inputs[4];
		gyroRate = inputs[5];
		Output.outputNumber("lDist", lDist);
		Output.outputNumber("rDist", rDist);
		Output.outputNumber("theta", gyroTheta);
		Output.outputNumber("gRate", gyroRate);
		
		MotorValue lPidValue = lPID.newValue(new double[] { lDist, lRate });
		MotorValue rPidValue = rPID.newValue(new double[] { rDist, rRate });
		MotorValue gPidValue = gPID.newValue(new double[] { gyroTheta, gyroRate });
		
		Output.outputNumber("lPidVal", lPidValue.getValue());
		Output.outputNumber("rPidVal", rPidValue.getValue());
		Output.outputNumber("gPidVal", gPidValue.getValue());
		
		MotorValue lMotor = new MotorValue(kLR * ((1 - kGW) * lPidValue.getValue() + kGW * gPidValue.getValue()));
		MotorValue rMotor = new MotorValue(((1 - kGW) * rPidValue.getValue() - kGW * gPidValue.getValue()) / kLR);
		
		// lDist and rDist are added because lDist should equal -rDist0
		return new MotorValue[] { lMotor, rMotor };
	}

}