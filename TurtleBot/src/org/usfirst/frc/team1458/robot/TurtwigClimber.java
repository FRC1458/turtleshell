package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.Input;
import com.team1458.turtleshell.Input.XboxButton;
import com.team1458.turtleshell.MotorValue;
import com.team1458.turtleshell.TurtleEncoder;
import com.team1458.turtleshell.TurtleMotor;
import com.team1458.turtleshell.TurtleRobotComponent;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleVictor;
import com.team1458.turtleshell.pid.TurtleDualPID;

public class TurtwigClimber implements TurtleRobotComponent {
	private TurtleMotor powerWinch = new TurtleVictor(TurtwigConstants.POWERWINCHVICTORPORT, false);
	private TurtleMotor hookWinch = new TurtleVictor(TurtwigConstants.HOOKWINCHVICTORPORT, false);

	private TurtleEncoder powerEncoder = new Turtle4PinEncoder(TurtwigConstants.POWERWINCHENCODERPORT1,
			TurtwigConstants.POWERWINCHENCODERPORT2, false);
	private TurtleEncoder hookEncoder = new Turtle4PinEncoder(TurtwigConstants.HOOKWINCHENCODERPORT1,
			TurtwigConstants.HOOKWINCHENCODERPORT2, false);

	private TurtleDualPID pid;

	private ClimberState state = ClimberState.FOLDED;

	private enum ClimberState {
		FOLDED, UNFOLDING, UNFOLDED, RAISING, RAISED, CLIMBING, CLIMBED
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void teleUpdate() {
		// read user input and change state if necessary
		if (Input.getXboxButton(XboxButton.Y)) {
			switch (state) {
			case FOLDED:
				this.state = ClimberState.UNFOLDING;
				break;
			case UNFOLDED:
				this.state = ClimberState.RAISING;
				break;
			case RAISED:
				this.state = ClimberState.CLIMBING;
				break;
			default:
				break;
			}
		}
		// execute actions for state change
		if (pid != null) {
			driveMotors(pid.newValue(new double[] { powerEncoder.getTicks(), hookEncoder.getTicks(),
					powerEncoder.getRate(), hookEncoder.getRate() }));
		}

		// check if move to new state
		switch (state) {
		case UNFOLDING:
			break;
		case RAISING:
			break;
		case CLIMBING:
			break;
		default:
			break;
		}
	}

	/**
	 * Power, hook
	 * 
	 * @param v
	 */
	private void driveMotors(MotorValue[] v) {
		powerWinch.set(v[0]);
		hookWinch.set(v[1]);
	}

	@Override
	public void stop() {
		powerWinch.stop();
		hookWinch.stop();
	}

}
