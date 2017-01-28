package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.implementations.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleButtonInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleSmartMotor;
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
