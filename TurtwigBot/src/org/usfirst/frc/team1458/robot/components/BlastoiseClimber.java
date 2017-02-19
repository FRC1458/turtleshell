package org.usfirst.frc.team1458.robot.components;

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

	//Motor
	private final TurtleMotor motor = new TurtleTalonSRXCAN(Constants.Climber.MOTOR_PORT);
	private final MotorValue speed = Constants.Climber.SPEED;
	private final MotorValue lowSpeed = Constants.Climber.SPEED_LOW;

	private ClimberStatus status;

	public BlastoiseClimber() {
		this.status = ClimberStatus.STOPPED;
	}

	public boolean isClimbing() {
		return status == ClimberStatus.CLIMBING;
	}

	public ClimberStatus getStatus() {
		return status;
	}

	public boolean isReadyToClimb() {
		return isRopeLowered() && status == ClimberStatus.STOPPED;
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
		status = ClimberStatus.CLIMBING;
	}

	public void startReverse() {
		motor.set(speed.invert());
		status = ClimberStatus.DESCENDING;
	}

	/**
	 * Stops climbing and runs motor at very low speed to maintain altitude
	 */
	public void finish() {
		motor.set(lowSpeed);
		status = ClimberStatus.FINISHED;
	}

	public void stop() {
		motor.set(MotorValue.zero);
		status = ClimberStatus.STOPPED;
	}

	public enum ClimberStatus {
		CLIMBING, FINISHED, DESCENDING, STOPPED
	}
}
