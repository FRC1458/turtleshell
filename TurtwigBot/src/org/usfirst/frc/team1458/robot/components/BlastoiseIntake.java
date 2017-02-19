package org.usfirst.frc.team1458.robot.components;

import org.usfirst.frc.team1458.robot.Constants;

import com.team1458.turtleshell2.movement.TurtleMotor;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * Intake code
 *
 * @author asinghani
 */
public class BlastoiseIntake {
	private final TurtleMotor motor = new TurtleTalonSRXCAN(Constants.Intake.MOTOR_PORT);
	
	private final MotorValue speed = Constants.Intake.SPEED;
	private final MotorValue reverseSpeed = Constants.Intake.REVERSE_SPEED;

	private IntakeStatus status;

	public BlastoiseIntake() {
		this.status = IntakeStatus.STOPPED;
	}

	public void start() {
		status = IntakeStatus.RUNNING;
		motor.set(speed);
	}

	public void startReverse() {
		status = IntakeStatus.REVERSED;
		motor.set(reverseSpeed);
	}

	public void stop() {
		status = IntakeStatus.STOPPED;
		motor.set(MotorValue.zero);
	}

	public IntakeStatus getStatus() {
		return status;
	}

	public enum IntakeStatus {
		RUNNING, REVERSED, STOPPED
	}
}
