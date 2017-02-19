package org.usfirst.frc.team1458.robot;

/**
 * Single source of data4
 * @author asinghani
 */
public class BlastoiseFluxStore {
	private static BlastoiseFluxStore instance;

	public ClimberStatus climberStatus;
	public IntakeStatus intakeStatus;
	public ShooterStatus leftShooterStatus;
	public ShooterStatus rightShooterStatus;

	private BlastoiseFluxStore() {
		climberStatus = ClimberStatus.STOPPED;
		intakeStatus = IntakeStatus.STOPPED;
		leftShooterStatus = ShooterStatus.STOPPED;
		rightShooterStatus = ShooterStatus.STOPPED;
	}

	public boolean isRobotClimbingRope() {
		return climberStatus == BlastoiseFluxStore.ClimberStatus.CLIMBING;
	}

	public enum ClimberStatus {
		CLIMBING, FINISHED, DESCENDING, STOPPED
	}

	public enum IntakeStatus {
		RUNNING, REVERSED, STOPPED
	}

	public enum ShooterStatus {
		SHOOTING, STOPPED, REVERSED, MANUAL
	}

	public static BlastoiseFluxStore getInstance() {
		if(instance == null) instance = new BlastoiseFluxStore();
		return instance;
	}
}
