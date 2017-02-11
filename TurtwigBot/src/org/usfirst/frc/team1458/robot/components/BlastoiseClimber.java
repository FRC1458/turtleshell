package org.usfirst.frc.team1458.robot.components;

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

	/**
	 * Motor
	 */
	private TurtleMotor motor;
	private MotorValue speed;
	private MotorValue lowSpeed;

	private ClimberStatus status;

	/**
	 * Instantiates BlastoiseClimber
	 * @param motorPort
	 */
	public BlastoiseClimber(int motorPort, MotorValue speed, MotorValue lowSpeed) {
		this.motor = new TurtleTalonSRXCAN(motorPort);
		this.speed = speed;
		this.lowSpeed = lowSpeed;
		this.status = ClimberStatus.STOPPED;
	}

	/**
	 * Returns whether the robot is currently climbing the rope.
	 */
	public boolean isClimbing() {
		return status == ClimberStatus.CLIMBING;
	}

	/**
	 * Returns the current status of the climber.
	 */
	public ClimberStatus getStatus() {
		return status;
	}

	/**
	 * Returns if the robot is ready to climb.
	 */
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

	/**
	 * Starts climbing
	 */
	public void start() {
		motor.set(speed);
		status = ClimberStatus.CLIMBING;
	}

	/**
	 * Starts descending
	 */
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

	/**
	 * Stops climbing
	 */
	public void stop() {
		motor.set(MotorValue.zero);
		status = ClimberStatus.STOPPED;
	}

	public enum ClimberStatus {
		CLIMBING, FINISHED, DESCENDING, STOPPED
	}
}
