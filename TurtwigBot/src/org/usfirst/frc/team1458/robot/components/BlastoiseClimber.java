package org.usfirst.frc.team1458.robot.components;

import org.usfirst.frc.team1458.robot.BlastoiseFluxStore;
import org.usfirst.frc.team1458.robot.Constants;

import com.team1458.turtleshell2.movement.TurtleMotor;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Climber code
 *
 * @author asinghani
 */
public class BlastoiseClimber {
	public final TurtleTalonSRXCAN motor = new TurtleTalonSRXCAN(Constants.Climber.MOTOR_PORT);
	private final MotorValue speed = Constants.Climber.SPEED;
	private final MotorValue lowSpeed = Constants.Climber.SPEED_LOW;

	private final BlastoiseFluxStore store;

	public BlastoiseClimber() {
		store = BlastoiseFluxStore.getInstance();
		store.climberStatus = BlastoiseFluxStore.ClimberStatus.STOPPED;
	}

	public boolean isReadyToClimb() {
		return isRopeLowered() && store.climberStatus == BlastoiseFluxStore.ClimberStatus.STOPPED;
	}

	/**
	 * Returns if there is less than 30 seconds left in the match.
	 */
	public boolean isRopeLowered() {
		DriverStation ds = DriverStation.getInstance();
		if(ds.isFMSAttached()) {
			return ds.getMatchTime() < 30;
		}
		return true;
	}

	public void start() {
		motor.set(speed);
		store.climberStatus = BlastoiseFluxStore.ClimberStatus.CLIMBING;
	}

	public void startReverse() {
		motor.set(new MotorValue(-0.2));
		store.climberStatus = BlastoiseFluxStore.ClimberStatus.DESCENDING;
	}

	/**
	 * Stops climbing and runs motor at very low speed to maintain altitude
	 */
	public void finish() {
		motor.set(lowSpeed);
		store.climberStatus = BlastoiseFluxStore.ClimberStatus.FINISHED;
	}

	public void stop() {
		motor.set(MotorValue.zero);
		store.climberStatus = BlastoiseFluxStore.ClimberStatus.STOPPED;
	}
}
