package org.usfirst.frc.team1458.robot.components;

import org.usfirst.frc.team1458.robot.BlastoiseFluxStore;
import org.usfirst.frc.team1458.robot.Constants;

import com.team1458.turtleshell2.movement.TurtleMotor;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Intake code
 *
 * @author asinghani
 */
public class BlastoiseIntake {
	public final TurtleTalonSRXCAN motor = new TurtleTalonSRXCAN(Constants.Intake.MOTOR_PORT, true);
	
	private final MotorValue speed = Constants.Intake.SPEED;
	private final MotorValue reverseSpeed = Constants.Intake.REVERSE_SPEED;

	private final BlastoiseFluxStore store;

	public BlastoiseIntake() {
		store = BlastoiseFluxStore.getInstance();
		store.intakeStatus = BlastoiseFluxStore.IntakeStatus.STOPPED;
		
		SmartDashboard.putNumber("IntakeSpeed", speed.getValue());
	}

	public void start() {
		store.intakeStatus = BlastoiseFluxStore.IntakeStatus.RUNNING;
		motor.set(new MotorValue( SmartDashboard.getNumber("IntakeSpeed", speed.getValue()) ));
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
