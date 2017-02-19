package com.team1458.turtleshell2.pid;

import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;
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
	 * @param kLR If this is set to 0, then no angle correction is performed
	 */
	public StraightDrivePID(Distance distance, Distance deadband, PIDConstants leftConstants,
							PIDConstants rightConstants, PIDConstants turnConstants, double kLR) {
		this.kLR = kLR;
		left = new PID(leftConstants, distance.getInches(), deadband.getInches());
		right = new PID(rightConstants, distance.getInches(), deadband.getInches());
		angle = new PID(turnConstants, 0, 0);

	}

	public Tuple<MotorValue, MotorValue> newValue(Distance distanceLeft, Distance distanceRight, Angle currentAngle) {
		double l = left.newValue(distanceLeft.getInches());
		double r = right.newValue(distanceLeft.getInches());
		double a = angle.newValue(currentAngle.getDegrees());

		return new Tuple<>(new MotorValue(l + kLR * a), new MotorValue(r - kLR * a));
	}

	public boolean atTarget() {
		return left.atTarget() && right.atTarget();
	}
}
