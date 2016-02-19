package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.component.TurtleRobotComponent;
import com.team1458.turtleshell.movement.TurtleMotor;
import com.team1458.turtleshell.movement.TurtleSolenoid;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleElectricalSolenoid;
import com.team1458.turtleshell.physical.TurtleVictor;
import com.team1458.turtleshell.pid.TurtleDualPID;
import com.team1458.turtleshell.pid.TurtleManualDualPID;
import com.team1458.turtleshell.pid.TurtlePDD2;
import com.team1458.turtleshell.pid.TurtleZeroPID;
import com.team1458.turtleshell.sensor.TurtleEncoder;
import com.team1458.turtleshell.util.Input;
import com.team1458.turtleshell.util.MotorValue;
import com.team1458.turtleshell.util.Input.XboxButton;

import edu.wpi.first.wpilibj.Timer;

public class TurtwigClimber implements TurtleRobotComponent {
	
	private static TurtwigClimber instance;
	public static TurtwigClimber getInstance() {
		if(instance==null) {
			instance = new TurtwigClimber();
		}
		return instance;
	}
	
	private TurtleMotor powerWinch = new TurtleVictor(TurtwigConstants.POWERWINCHVICTORPORT, false);
	private TurtleMotor hookWinch = new TurtleVictor(TurtwigConstants.HOOKWINCHVICTORPORT, false);

	private TurtleEncoder powerEncoder = new Turtle4PinEncoder(TurtwigConstants.POWERWINCHENCODERPORT1,
			TurtwigConstants.POWERWINCHENCODERPORT2, false);
	private TurtleEncoder hookEncoder = new Turtle4PinEncoder(TurtwigConstants.HOOKWINCHENCODERPORT1,
			TurtwigConstants.HOOKWINCHENCODERPORT2, false);

	private TurtleSolenoid folder = new TurtleElectricalSolenoid(TurtwigConstants.SOLENOIDPORT);

	private TurtleDualPID pid;

	private Timer unfoldTimer = new Timer();

	private ClimberState state = ClimberState.FOLDED;

	private enum ClimberState {
		FOLDED, UNFOLDING, UNFOLDED, RAISING, RAISED, RETRACTING, CLIMBING, CLIMBED
	}

	@Override
	public void init() {
		folder.set(true);

	}

	@Override
	public void teleUpdate() {
		// read user input and change state if necessary
		if (Input.getXboxButton(XboxButton.Y)) {
			switch (state) {
			case FOLDED:
				this.state = ClimberState.UNFOLDING;
				folder.set(false);
				unfoldTimer.start();
				break;
			case RAISED:
				this.state = ClimberState.RETRACTING;
				pid = new TurtleManualDualPID(TurtleZeroPID.getInstance(),
						new TurtlePDD2(TurtwigConstants.hookLowerConstants, 0, TurtwigConstants.pidTolerance));
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
			if (unfoldTimer.get() > TurtwigConstants.unfoldTime) {
				state = ClimberState.UNFOLDED;
				unfoldTimer.stop();
			}

			break;
		case UNFOLDED: // starts the raising
			state = ClimberState.RAISING;
			pid = new TurtleManualDualPID(TurtleZeroPID.getInstance(),
					new TurtlePDD2(TurtwigConstants.hookRaiseConstants, TurtwigConstants.hookEncoderTicks,
							TurtwigConstants.pidTolerance));
			break;
		case RAISING:// checks if it is done raising
			if (pid.atTarget()) {
				pid = null;
				state = ClimberState.RAISED;

			}
			break;
		case RETRACTING:
			if (pid.atTarget()) {
				// start climbing
				state = ClimberState.CLIMBING;
				pid = new TurtleManualDualPID(new TurtlePDD2(TurtwigConstants.robotRaiseConstants,
						TurtwigConstants.robotEncoderTicks, TurtwigConstants.pidTolerance),
						TurtleZeroPID.getInstance());
			}
		case CLIMBING:
			if (pid.atTarget()) {
				pid = null;
				state = ClimberState.CLIMBED;
			}
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
