package com.team1458.turtleshell.pid;

import com.team1458.turtleshell.util.MotorValue;

/**
 * Class used for synchronising two PID when should be going at different rates.
 * Assumes they should PROPORTIONALLY go, at percent
 * 
 * @author mehnadnerd
 *
 */
public class TurtleAsymmetricPID implements TurtleDualPID {
	private final TurtlePID apid;
	private final TurtlePID bpid;
	private final double kABA;
	private final double kABB;

	private double aDist;
	private double bDist;
	private double aRate;
	private double bRate;

	private double aTarget;
	private double bTarget;

	private MotorValue aLone, bLone;

	public TurtleAsymmetricPID(TurtlePIDConstants kA, TurtlePIDConstants kB, double aTarget, double bTarget,
			double kABA, double kABB, double aTolerance, double bTolerance) {
		apid = new TurtlePDD2(kA, aTarget, aTolerance);
		bpid = new TurtlePDD2(kB, bTarget, bTolerance);
		this.aTarget = aTarget;
		this.bTarget = bTarget;
		this.kABA = kABA;
		this.kABB = kABB;
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

		return new MotorValue[] {
				new MotorValue(aLone.getValue() + kABA * (aLone.getValue() / aTarget - bLone.getValue() / bTarget)),
				new MotorValue(bLone.getValue() - kABB * (aLone.getValue() / aTarget - bLone.getValue() / bTarget)) };
	}

}
