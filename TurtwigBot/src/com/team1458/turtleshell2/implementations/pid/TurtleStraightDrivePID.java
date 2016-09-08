package com.team1458.turtleshell2.implementations.pid;

import com.team1458.turtleshell2.interfaces.pid.TurtleDualPID;
import com.team1458.turtleshell2.interfaces.pid.TurtlePID;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.TurtlePIDConstants;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * Class used drive the robot in a straight line
 * @author mehnadnerd
 *
 */
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
	 * @param constants The constants for the PID
	 * @param target The target value
	 * @param kLR The constant for left-right difference
	 * @param kError The error constant passed to the TurtlePDD2
	 */
	public TurtleStraightDrivePID(TurtlePIDConstants constants, double target, double kLR, double kError) {
		//Output.outputNumber("targetLinear", target);
		lPID = new TurtlePDD2(constants, target, kError);
		rPID = new TurtlePDD2(constants, target, kError);
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
		//Output.outputNumber("lDist", inputs[0]);
		//Output.outputNumber("rDist", inputs[1]);
		lDist = inputs[0];
		rDist = inputs[1];
		lRate = inputs[2];
		rRate = inputs[3];
		return new MotorValue[] {
				new MotorValue(lPID.newValue(new double[] { lDist, lRate }).getValue() - kLR * (lDist - rDist)),
				new MotorValue(rPID.newValue(new double[] { rDist, rRate }).getValue() + kLR * (lDist - rDist)) };
	}

}
