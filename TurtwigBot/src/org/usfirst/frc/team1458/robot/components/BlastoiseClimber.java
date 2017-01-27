package org.usfirst.frc.team1458.robot.components;

import com.sun.tools.internal.xjc.Driver;
import com.team1458.turtleshell2.implementations.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleButtonInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleSmartMotor;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

/**
 * Climber code
 *
 * @author asinghani
 */
public class BlastoiseClimber implements TurtleComponent {

	// We need to detect stall
	private TurtleSmartMotor motor1 = new TurtleTalonSRXCAN(RobotConstants.Climber.MOTOR_PORT);

	private TurtleButtonInput climbButton;
	private TurtleButtonInput stopButton;

	private boolean climbing;

	@Override
	public void teleUpdate() {
		if(climbButton.getDown()) {
			climbing = true;
		}

		if(climbButton.getDown()) {
			climbing = false;
			motor1.set(MotorValue.zero);
		}

		if(climbing) {
			motor1.set(RobotConstants.Climber.SPEED);
			if(motor1.isStalling()) {
				motor1.set(RobotConstants.Climber.SPEED_LOWER);
			}
		}
	}

	public boolean timeToClimb() {
		DriverStation ds = DriverStation.getInstance();
		if(ds.isFMSAttached()){
			return ds.getMatchTime() < 35; // 35 seconds (for some buffer)
		}
		return true;
	}
}
