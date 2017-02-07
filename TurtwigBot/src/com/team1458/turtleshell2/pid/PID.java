package com.team1458.turtleshell2.pid;

import com.team1458.turtleshell2.util.PIDConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.BaseSystemNotInitializedException;

/**
 * Class for a PID loop. Handles integration and derivation itself
 * @author asinghani
 *
 */
public class PID {
	private final PIDConstants constants;
	private final double target;
	private final double deadband;

	private double lastError = 0;
	private double derivative = 0;
	private double sum = 0;
	private double lastTime = -1;

	public PID(PIDConstants constants, double target, double deadband) {
		this.constants = constants;
		this.target = target;
		this.deadband = Math.abs(deadband);
		this.lastError = Double.POSITIVE_INFINITY;
	}

	/**
	 * Get new value from PID
	 * @param value
	 */
	public double newValue(double value) {
		double error = target - value;
		derivative = (lastError - error) / (getTime() - lastTime);

		if(lastTime == -1) {
			lastTime = getTime();
			derivative = 0;
		}

		double output =
				constants.kP * error +     // P term
				constants.kI * sum +       // I term
				constants.kD * derivative; // D term

		lastTime = getTime();
		sum += value;
		sum = 0.75 * sum; //Why is this here? Decaying I term
		lastError = error;

		return output;
	}

	public boolean atTarget() {
		return Math.abs(lastError) < deadband &&     // P term
				Math.abs(derivative) < (deadband / 15.0); // D term
	}

	private double getTime() {
		double time = System.currentTimeMillis() / 1000.0;
		try {
			time = Timer.getFPGATimestamp();
		} catch (BaseSystemNotInitializedException e) {}
		return time;
	}
}
