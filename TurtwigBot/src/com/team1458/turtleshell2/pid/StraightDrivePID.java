package com.team1458.turtleshell2.pid;

import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.Tuple;

/**
 * A PID class for driving straight, uses left and right separately, doesn't
 * rely on them for angle
 * 
 * @author mehnadnerd
 *
 */
public class StraightDrivePID {
	private final PID left;
	private final PID right;
	private final PID angle;
	private final double kLR;

	/**
	 * Create the StraightDrivePID
	 * 
	 * @param distance
	 *            The distance it should travel forward
	 * @param deadband
	 * @param leftConstants
	 * @param rightConstants
	 * @param turnConstants
	 * @param kLR
	 */
	public StraightDrivePID(double distance, double deadband, double angleDeadband, PIDConstants leftConstants,
			PIDConstants rightConstants, PIDConstants turnConstants, double kLR) {
		this.kLR = kLR;
		left = new PID(leftConstants, distance, deadband);
		right = new PID(rightConstants, distance, deadband);
		angle = new PID(turnConstants, 0, angleDeadband);

	}

	public Tuple<Double, Double> newValue(double distanceLeft, double distanceRight, double currentAngle) {
		double l = left.newValue(distanceLeft);
		double r = right.newValue(distanceLeft);
		double a = angle.newValue(currentAngle);
		return new Tuple<>(l + kLR * a, r - kLR * a);
	}

	public boolean atTarget() {
		return left.atTarget() && right.atTarget() && angle.atTarget();
	}

}
