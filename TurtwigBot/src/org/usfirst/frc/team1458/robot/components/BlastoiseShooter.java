package org.usfirst.frc.team1458.robot.components;

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

	private ShooterStatus status;

	private MotorValue openLoop;

	/**
	 * reversed if on right side
	 */
	public BlastoiseShooter(int motorPort, int hallPort, PIDConstants pidConstants, double speedTarget,
			boolean reversed, MotorValue openLoop) {
		this.hallSensor = new TurtleHallSensor(hallPort);
		this.motor = new TurtleTalonSRXCAN(motorPort, reversed);
		motor.setBrakeMode(BrakeMode.COAST); // Prevents the shooter from killing the motor

		this.status = ShooterStatus.STOPPED;
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
		status = ShooterStatus.SHOOTING;
		setSpeedTargetRpm(speedTarget);
	}

	public void startReverse() {
		status = ShooterStatus.REVERSED;
	}

	public void stop() {
		status = ShooterStatus.STOPPED;
		motor.set(MotorValue.zero);
	}

	/**
	 * Adjusts the shooter speed based on desired RPM
	 */
	public void teleUpdate() {
		if (status == ShooterStatus.SHOOTING) {
			double motorPower = pid.newValue(hallSensor.getRPM());
			MotorValue motorValue = new MotorValue(motorPower);
			motor.set(motorValue);

			SmartDashboard.putNumber("ShooterMotorPower", motorPower);
		} else if (status == ShooterStatus.REVERSED) {
			motor.set(Constants.Shooter.REVERSE_SPEED);
		}
	}
	
	
	public double getSpeed() {
		return hallSensor.getRPM();
	}
	
	public ShooterStatus getStatus() {
		return status;
	}

	public enum ShooterStatus {
		SHOOTING, STOPPED, REVERSED
	}
}
