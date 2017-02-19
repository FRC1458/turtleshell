package org.usfirst.frc.team1458.robot.components;

import org.usfirst.frc.team1458.robot.BlastoiseFluxStore;
import org.usfirst.frc.team1458.robot.Constants;

import com.team1458.turtleshell2.movement.TurtleSmartMotor;
import com.team1458.turtleshell2.movement.TurtleSmartMotor.BrakeMode;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.pid.ShooterPID;
import com.team1458.turtleshell2.sensor.TurtleHallSensor;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Basic code for a single shooter. Maintains constant motor speed.
 * 
 * @author asinghani
 */
public class BlastoiseShooter {
	private final TurtleHallSensor hallSensor;
	private final TurtleSmartMotor motor;

	private ShooterPID pid;
	private PIDConstants pidConstants;
	private double speedTarget;

	private MotorValue openLoop;

	private final BlastoiseFluxStore store;
	private final boolean rightShooter;

	/**
	 * Reversed if on right side
	 * @param motorPort
	 * @param hallPort
	 * @param pidConstants
	 * @param speedTarget
	 * @param reversed
	 * @param openLoop
	 * @param rightShooter
	 */
	public BlastoiseShooter(int motorPort, int hallPort, PIDConstants pidConstants, double speedTarget,
			boolean reversed, MotorValue openLoop, boolean rightShooter) {
		this.hallSensor = new TurtleHallSensor(hallPort);
		this.motor = new TurtleTalonSRXCAN(motorPort, reversed);
		motor.setBrakeMode(BrakeMode.COAST); // Prevents the shooter from killing the motor

		this.rightShooter = rightShooter;
		this.store = BlastoiseFluxStore.getInstance();

		this.pidConstants = pidConstants;
		this.openLoop = openLoop;
		setSpeedTargetRpm(speedTarget);
	}

	public void setSpeedTargetRpm(double speedTarget) {
		this.speedTarget = speedTarget;
		pid = new ShooterPID(pidConstants, speedTarget, 0, openLoop);
	}
	
	public void setOpenLoop(double openLoop) {
		this.openLoop = new MotorValue(openLoop);
		pid = new ShooterPID(pidConstants, speedTarget, 0, this.openLoop);
	}

	public void setPIDConstants(PIDConstants constants) {
		this.pidConstants = constants;
		setSpeedTargetRpm(speedTarget);
	}

	public void start() {
		if(rightShooter) {
			store.rightShooterStatus = BlastoiseFluxStore.ShooterStatus.SHOOTING;
		} else {
			store.leftShooterStatus = BlastoiseFluxStore.ShooterStatus.SHOOTING;
		}
		setSpeedTargetRpm(speedTarget);
	}

	public void startReverse() {
		if(rightShooter) {
			store.rightShooterStatus = BlastoiseFluxStore.ShooterStatus.REVERSED;
		} else {
			store.leftShooterStatus = BlastoiseFluxStore.ShooterStatus.REVERSED;
		}
		setSpeedTargetRpm(speedTarget);
	}

	public void stop() {
		if(rightShooter) {
			store.rightShooterStatus = BlastoiseFluxStore.ShooterStatus.STOPPED;
		} else {
			store.leftShooterStatus = BlastoiseFluxStore.ShooterStatus.STOPPED;
		}
		motor.set(MotorValue.zero);
	}

	/**
	 * Adjusts the shooter speed based on desired RPM
	 */
	public void teleUpdate() {
		if (getStatus() == BlastoiseFluxStore.ShooterStatus.SHOOTING) {
			double motorPower = pid.newValue(hallSensor.getRPM());
			MotorValue motorValue = new MotorValue(motorPower);
			motor.set(motorValue);

			SmartDashboard.putNumber("ShooterMotorPower", motorPower);
		} else if (getStatus() == BlastoiseFluxStore.ShooterStatus.REVERSED) {
			motor.set(Constants.Shooter.REVERSE_SPEED);
		}
	}
	
	
	public double getSpeed() {
		return hallSensor.getRPM();
	}
	
	private BlastoiseFluxStore.ShooterStatus getStatus() {
		if(rightShooter) {
			return store.rightShooterStatus;
		} else {
			return store.leftShooterStatus;
		}
	}
}
