package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.core.RobotComponent;
import com.team1458.turtleshell2.input.ButtonInput;
import com.team1458.turtleshell2.movement.TurtleMotor;
import com.team1458.turtleshell2.movement.TurtleVictorSP;
import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.sensor.TurtleHallSensor;
import com.team1458.turtleshell2.util.types.MotorValue;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

/**
 * Basic code for a single shooter. Uses RPM internally. Maintains constant motor speed.
 * @author asinghani
 */
public class BlastoiseShooter implements RobotComponent {
	private TurtleHallSensor hallSensor;
	private TurtleMotor shooter;
	private PID pid;

	private boolean shooting = false;

	private ButtonInput shootButton;
	private ButtonInput speedSwitch;

	private double speedRPM;

	public BlastoiseShooter(TurtleHallSensor hallSensor, int motorPort, ButtonInput shootButton, ButtonInput speedSwitch) {
		this.hallSensor = hallSensor;
		this.shooter = new TurtleVictorSP(motorPort);
		this.shootButton = shootButton;
		this.speedSwitch = speedSwitch;

		speedRPM = getRPM();
		pid = new PID(RobotConstants.Shooter.PID_CONSTANTS, speedRPM, 0);
	}

	public void startShooting() {
		shooting = true;
		speedRPM = getRPM();
		pid = new PID(RobotConstants.Shooter.PID_CONSTANTS, speedRPM, 0);
	}

	public void stopShooting() {
		shooting = false;
		shooter.set(MotorValue.zero);
	}

	public void teleUpdate() {
		if(shooting){
			if(getRPM() != speedRPM) {
				speedRPM = getRPM();
				pid = new PID(RobotConstants.Shooter.PID_CONSTANTS, speedRPM, 0);
			}

			double motorPower = pid.newValue(hallSensor.getRPM());
			MotorValue motorValue = new MotorValue(motorPower);
			shooter.set(motorValue);
		} else {
			shooter.set(MotorValue.zero);
		}
	}

	public void stop() {
		stopShooting();
	}

	public double getRPM() {
		return speedSwitch.getButton() ? RobotConstants.Shooter.LOW_RPM : RobotConstants.Shooter.HIGH_RPM;
	}
}
