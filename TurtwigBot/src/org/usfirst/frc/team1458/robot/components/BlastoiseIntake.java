package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.implementations.drive.TurtleMotorSet;
import com.team1458.turtleshell2.implementations.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.util.types.MotorValue;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

/**
 * Base code for intake
 *
 * @author asinghani
 */
public class BlastoiseIntake implements TurtleComponent {
	private TurtleMotorSet motors = new TurtleMotorSet(new TurtleTalonSRXCAN(RobotConstants.Intake.MOTOR_PORT));

	@Override
	public void teleUpdate() {
		motors.set(MotorValue.zero);
	}
}
