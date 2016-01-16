package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.TurtleAutonomous;
import com.team1458.turtleshell.TurtleEncoderDistanceDrivePID;

/**
 * Test implementation of an auto that drives 10 revolutions forward
 * @author mehnadnerd
 *
 */
public class RedTies2015Auto1 implements TurtleAutonomous {
	
	private final double kP = 0.1;
	private final double kD = 0.01;
	private final double kLRP = 0.1;
	private final int distanceToMove = 360*10;
	
	// 4 Autos, one for each position of obstacle
	// vision tracking for theta
	private int lEncoder;
	private int rEncoder;
	private TurtleEncoderDistanceDrivePID pid;

	private static RedTies2015Auto1 instance;

	private RedTies2015Auto1() {
		pid = new TurtleEncoderDistanceDrivePID(kP, kD, kLRP, distanceToMove);
	}

	public static RedTies2015Auto1 getInstance() {
		if (instance == null) {
			instance = new RedTies2015Auto1();
		}
		return instance;
	}

	@Override
	public double[] getMotors() {
		return pid.getP(lEncoder, rEncoder);
	}

	@Override
	public void calculate() {
		//nothing to do without values
	}
	/**
	 * Send the encoder values back in for recalculation.
	 * @param encoderValues left,right
	 */
	@Override
	public void calculate(double[] encoderValues) {
		lEncoder=(int) encoderValues[0];
		rEncoder=(int) encoderValues[1];
	}

	@Override
	public void init() {
		lEncoder=0;
		rEncoder=0;
	}

}
