package org.usfirst.frc.team1458.robot.components;

import org.usfirst.frc.team1458.robot.BlastoiseFluxStore;
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

	private final BlastoiseFluxStore store;

	public BlastoiseIntake() {
		store = BlastoiseFluxStore.getInstance();
		store.intakeStatus = BlastoiseFluxStore.IntakeStatus.STOPPED;
	}

	public void start() {
		store.intakeStatus = BlastoiseFluxStore.IntakeStatus.RUNNING;
		motor.set(speed);
	}

	public void startReverse() {
		store.intakeStatus = BlastoiseFluxStore.IntakeStatus.REVERSED;
		motor.set(reverseSpeed);
	}

	public void stop() {
		store.intakeStatus = BlastoiseFluxStore.IntakeStatus.STOPPED;
		motor.set(MotorValue.zero);
	}
}
