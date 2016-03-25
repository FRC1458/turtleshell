package com.team1458.turtleshell.pid;

import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.util.MotorValue;
import com.team1458.turtleshell.util.Output;
import com.team1458.turtleshell.util.TurtleMaths;

/**
 * Class used drive the robot in a straight line
 * 
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
     * 
     * @param kP
     * @param kD
     * @param kDD
     * @param target
     * @param kLR
     */
    public TurtleStraightDrivePID(TurtlePIDConstants constants, double target, double kLR, double kError) {
	Output.outputNumber("targetLinear", target);
	lPID = new TurtlePDD2(constants, target, kError);
	rPID = new TurtlePDD2(constants, target, kError);
	this.kLR = kLR;
    }

    @Override
    public boolean atTarget() {
	return lPID.atTarget() && rPID.atTarget() && TurtleMaths.absDiff(lDist, rDist) < 20;
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
	TurtleLogger.info("lDist: "+inputs[0]);
	TurtleLogger.info("rDist: "+inputs[1]);
	lDist = inputs[0];
	rDist = inputs[1];
	lRate = inputs[2];
	rRate = inputs[3];
	double leftRaw = lPID.newValue(new double[] { lDist, lRate }).getValue();
	double rightRaw = rPID.newValue(new double[] { rDist, rRate }).getValue();
	double lrDelta = (lDist - rDist);
	TurtleLogger.info("LeftRaw: " + leftRaw);
	TurtleLogger.info("Right raw: " + rightRaw);
	TurtleLogger.info("lrDelta: " + lrDelta);

	return new MotorValue[] { new MotorValue(1.0 * (leftRaw - kLR * lrDelta)), new MotorValue(1.0 * (rightRaw + kLR * lrDelta)) };
    }

}
