package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.core.RobotComponent;
import com.team1458.turtleshell2.input.TurtleButtonInput;
import com.team1458.turtleshell2.movement.TurtleSmartMotor;
import com.team1458.turtleshell2.movement.TurtleTalonSRXCAN;

import edu.wpi.first.wpilibj.DriverStation;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

/**
 * Climber code
 *
 * @author asinghani
 */
public class BlastoiseClimber implements RobotComponent {

	// We need to detect stall
	private TurtleSmartMotor motor1 = new TurtleTalonSRXCAN(RobotConstants.Climber.MOTOR_PORT);

	private TurtleButtonInput climbButton;

	private boolean climbing;

	public BlastoiseClimber(TurtleButtonInput climbButton) {
		this.climbButton = climbButton;
	}

	@Override
	public void teleUpdate() {
		if(climbButton.getDown()) {
			motor1.set(RobotConstants.Climber.SPEED);
			climbing = true;
		} else {
			climbing = false;
		}
	}

	public boolean isClimbing() {
		return climbing;
	}

	public boolean isReadyToClimb() {
		DriverStation ds = DriverStation.getInstance();
		if(ds.isFMSAttached()){
			return ds.getMatchTime() < 35; // 35 seconds (for some buffer)
		}
		return true;
	}
}
