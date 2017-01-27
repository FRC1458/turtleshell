package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.implementations.drive.TurtleMotorSet;
import com.team1458.turtleshell2.implementations.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleButtonInput;
import com.team1458.turtleshell2.interfaces.input.TurtleDigitalInput;
import com.team1458.turtleshell2.util.types.MotorValue;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

/**
 * Base code for intake
 *
 * @author asinghani
 */
public class BlastoiseIntake implements TurtleComponent {
	private TurtleMotorSet motors = new TurtleMotorSet(new TurtleTalonSRXCAN(RobotConstants.Intake.MOTOR_PORT));

	private TurtleDigitalInput enable;
	private TurtleButtonInput unclog;

	// Unclog
	private boolean unclogging = false;
	private long stopUnclogTime = 0;

	@Override
	public void teleUpdate() {
		if(unclog.getUp()) {
			unclog();
		}

		if(unclogging && System.currentTimeMillis() > stopUnclogTime) {
			unclogging = false;
		}

		if(!unclogging) {
			if(enable.get() == 1) {
				motors.set(RobotConstants.Intake.SPEED);
			} else {
				motors.set(MotorValue.zero);
			}
		}
	}

	public void unclog() {
		if(!unclogging) {
			unclogging = true;
			motors.set(RobotConstants.Intake.UNCLOG_SPEED);

			stopUnclogTime = System.currentTimeMillis() + RobotConstants.Intake.UNCLOG_TIME.getMillis();
		}
	}

	public boolean isUnclogging() {
		return unclogging;
	}
}
