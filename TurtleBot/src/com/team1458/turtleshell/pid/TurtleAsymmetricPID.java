package com.team1458.turtleshell.pid;

import com.team1458.turtleshell.MotorValue;

public class TurtleAsymmetricPID implements TurtleDualPID {
	private final TurtlePID apid;
	private final TurtlePID bpid;
	private final double kAB;

	private double aDist;
	private double bDist;
	private double aRate;
	private double bRate;

	private MotorValue aLone, bLone;

	public TurtleAsymmetricPID(TurtlePIDConstants kA, TurtlePIDConstants kB, double aTarget, double bTarget, double kAB,
			double aTolerance, double bTolerance) {
		apid = new TurtlePDD2(kA, aTarget, aTolerance);
		bpid = new TurtlePDD2(kB, bTarget, bTolerance);
		this.kAB = kAB;
	}

	@Override
	public boolean atTarget() {
		return apid.atTarget() && bpid.atTarget();
	}

	/**
	 * 
	 * @param inputs
	 *            a tick, b tick, a rate, b rate
	 * @return a motorValue, b motorValue
	 */
	@Override
	public MotorValue[] newValue(double[] inputs) {
		aDist = inputs[0];
		bDist = inputs[1];
		aRate = inputs[2];
		bRate = inputs[3];

		aLone = apid.newValue(new double[] { aDist, aRate });
		bLone = bpid.newValue(new double[] { bDist, bRate });

		return new MotorValue[] { new MotorValue(aLone.getValue()+kAB*0), new MotorValue(bLone.getValue()-kAB*0) };
	}

}
