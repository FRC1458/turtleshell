package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.movement.TurtleSmartMotor;
import com.team1458.turtleshell2.movement.TurtleSmartMotor.BrakeMode;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.pid.PID;
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
	private TurtleHallSensor hallSensor;
	private TurtleSmartMotor motor;

	private ShooterPID pid;
	private PIDConstants pidConstants;
	private double speedTarget;

	private ShooterStatus status;

	private MotorValue openLoop;

	/**
	 * Instantiates BlastoiseShooter
	 * 
	 * @param hallPort
	 * @param motorPort
	 * @param pidConstants
	 * @param speedTarget
	 * @param reversed
	 *            Whether this should be reversed, i.e. If is the right side
	 */
	public BlastoiseShooter(int motorPort, int hallPort, PIDConstants pidConstants, double speedTarget,
			boolean reversed, MotorValue openLoop) {
		this.hallSensor = new TurtleHallSensor(hallPort);
		this.motor = new TurtleTalonSRXCAN(motorPort, reversed);
		motor.setBrakeMode(BrakeMode.COAST);

		this.status = ShooterStatus.STOPPED;
		this.pidConstants = pidConstants;
		this.openLoop = openLoop;
		setSpeedTarget(speedTarget);
	}

	/**
	 * Sets the speed target in RPM. Also recreates PID loop, resetting I and D
	 * terms.
	 * 
	 * @param speedTarget
	 */
	public void setSpeedTarget(double speedTarget) {
		this.speedTarget = speedTarget;
		pid = new ShooterPID(pidConstants, speedTarget, 0, openLoop);
	}

	/**
	 * Sets new PID constants for the shooter. Also recreates PID loop,
	 * resetting I and D terms.
	 * 
	 * @param constants
	 */
	public void setPIDConstants(PIDConstants constants) {
		this.pidConstants = constants;
		setSpeedTarget(speedTarget);
	}

	/**
	 * Starts shooting. Also recreates PID loop, resetting I and D terms.
	 */
	public void start() {
		status = ShooterStatus.SHOOTING;
		setSpeedTarget(speedTarget);
	}

	/**
	 * Stops the shooter
	 */
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
		} else if (status == ShooterStatus.MANUAL) {
			motor.set(new MotorValue(0.7));
		}
	}

	/**
	 * Get the speed of the shooter in RPM
	 */
	public double getSpeed() {
		return hallSensor.getRPM();
	}

	public enum ShooterStatus {
		SHOOTING, STOPPED, DUMPING, MANUAL
	}
}
