package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.implementations.drive.MotorSet;
import com.team1458.turtleshell2.implementations.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.interfaces.RobotComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleButtonInput;
import com.team1458.turtleshell2.interfaces.input.TurtleDigitalInput;
import com.team1458.turtleshell2.util.types.MotorValue;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

/**
 * Code for intake
 *
 * @author asinghani
 */
public class BlastoiseIntake implements RobotComponent {
	private MotorSet motors = new MotorSet(new TurtleTalonSRXCAN(RobotConstants.Intake.MOTOR_PORT));

	private TurtleDigitalInput enableSwitch;
	private TurtleButtonInput unclogButton;

	// Unclog function
	private boolean unclogging = false;
	private long stopUnclogTime = 0;

	private IntakeStatus status;

	public BlastoiseIntake(TurtleDigitalInput enableSwitch, TurtleButtonInput unclogButton) {
		this.enableSwitch = enableSwitch;
		this.unclogButton = unclogButton;

		this.status = IntakeStatus.STOPPED;
	}

	@Override
	public void teleUpdate() {
		if(unclogButton.getUp()) {
			unclog();
		}

		if(unclogging && System.currentTimeMillis() > stopUnclogTime) {
			unclogging = false;
		}

		if(!unclogging) {
			if(enableSwitch.get() == 1) {
				status = IntakeStatus.RUNNING;
				motors.set(RobotConstants.Intake.SPEED);
			} else {
				status = IntakeStatus.STOPPED;
				motors.set(MotorValue.zero);
			}
		}
	}

	public void unclog() {
		if(!unclogging) {
			unclogging = true;
			status = IntakeStatus.UNCLOGGING;

			motors.set(RobotConstants.Intake.UNCLOG_SPEED);
			stopUnclogTime = System.currentTimeMillis() + RobotConstants.Intake.UNCLOG_TIME.getMillis();
		}
	}

	public boolean isUnclogging() {
		return unclogging;
	}

	public IntakeStatus getStatus() {
		return status;
	}

	public enum IntakeStatus {
		STOPPED(0), RUNNING(1), UNCLOGGING(2);

		public final int id;
		IntakeStatus(int i) {
			id = i;
		}
	}
}
