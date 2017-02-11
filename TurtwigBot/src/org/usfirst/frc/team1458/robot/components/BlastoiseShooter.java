package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.movement.TurtleMotor;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.sensor.TurtleHallSensor;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * Basic code for a single shooter. Maintains constant motor speed.
 * @author asinghani
 */
public class BlastoiseShooter {
	private TurtleHallSensor hallSensor;
	private TurtleMotor motor;

	private PID pid;
	private PIDConstants pidConstants;
	private double speedTarget;

	private ShooterStatus status;

	/**
	 * Instantiates BlastoiseShooter
	 * @param hallPort
	 * @param motorPort
	 * @param pidConstants
	 * @param speedTarget
	 */
	public BlastoiseShooter(int hallPort, int motorPort, PIDConstants pidConstants, double speedTarget) {
		this.hallSensor = new TurtleHallSensor(hallPort);
		this.motor = new TurtleTalonSRXCAN(motorPort);

		this.status = ShooterStatus.STOPPED;
		this.pidConstants = pidConstants;
		setSpeedTarget(speedTarget);
	}

	/**
	 * Sets the speed target in RPM
	 * @param speedTarget
	 */
	public void setSpeedTarget(double speedTarget) {
		this.speedTarget = speedTarget;
		pid = new PID(pidConstants, speedTarget, 0);
	}

	/**
	 * Starts shooting
	 */
	public void start() {
		status = ShooterStatus.SHOOTING;
		setSpeedTarget(speedTarget);
	}

	/**
	 * Starts running the shooter in reverse
	 */
	public void startReverse() {
		status = ShooterStatus.REVERSE;
		setSpeedTarget(speedTarget);
	}

	/**
	 * Stops the shooter
	 */
	public void stop() {
		status = ShooterStatus.STOPPED;
		motor.set(MotorValue.zero);
	}

	/**
	 * Adjusts the shooter speed based on desired RPM
	 */
	public void teleUpdate() {
		if(status != ShooterStatus.STOPPED){
			double motorPower = pid.newValue(hallSensor.getRPM());
			MotorValue motorValue = new MotorValue(motorPower);
			if(status == ShooterStatus.REVERSE) motorValue = motorValue.invert();
			motor.set(motorValue);
		}
	}

	/**
	 * Get the speed of the shooter in RPM
	 */
	public double getSpeed() {
		return hallSensor.getRPM();
	}

	public enum ShooterStatus {
		SHOOTING, STOPPED, REVERSE
	}
}
