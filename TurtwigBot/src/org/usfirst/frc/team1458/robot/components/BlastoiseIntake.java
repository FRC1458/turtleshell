package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.movement.TurtleMotor;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * Intake code
 *
 * @author asinghani
 */
public class BlastoiseIntake {
	/**
	 * Motor
	 */
	private TurtleMotor motor;
	private MotorValue speed;

	private IntakeStatus status;

	/**
	 * Instantiates BlastoiseIntake
	 * @param motorPort
	 * @param speed
	 */
	public BlastoiseIntake(int motorPort, MotorValue speed) {
		this.motor = new TurtleTalonSRXCAN(motorPort);
		this.status = IntakeStatus.STOPPED;
		this.speed = speed;
	}

	/**
	 * Start running the intake
	 */
	public void start() {
		status = IntakeStatus.RUNNING;
		motor.set(speed);
	}

	/**
	 * Start running the intake in reverse
	 */
	public void startReverse() {
		status = IntakeStatus.REVERSED;
		motor.set(speed.invert());
	}

	/**
	 * Stop running the intake
	 */
	public void stop() {
		status = IntakeStatus.STOPPED;
		motor.set(MotorValue.zero);
	}

	/**
	 * Get the current status of the intake
	 */
	public IntakeStatus getStatus() {
		return status;
	}

	public enum IntakeStatus {
		RUNNING, REVERSED, STOPPED
	}
}
