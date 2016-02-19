package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.component.TurtleRobotComponent;
import com.team1458.turtleshell.movement.TurtleMotor;
import com.team1458.turtleshell.movement.TurtleSolenoid;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleElectricalSolenoid;
import com.team1458.turtleshell.physical.TurtleVictor;
import com.team1458.turtleshell.pid.TurtleAsymmetricPID;
import com.team1458.turtleshell.pid.TurtleDualPID;
import com.team1458.turtleshell.pid.TurtlePDD2Constants;
import com.team1458.turtleshell.pid.TurtlePIDConstants;
import com.team1458.turtleshell.sensor.TurtleEncoder;
import com.team1458.turtleshell.util.Input;
import com.team1458.turtleshell.util.MotorValue;
import com.team1458.turtleshell.util.Input.XboxButton;

import edu.wpi.first.wpilibj.Timer;

public class TurtwigClimber implements TurtleRobotComponent {
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
	
	private final double unfoldTime = 1.0;
	
	TurtwigConstants consti = new TurtwigConstants();
	
		
	private double aTarget = 42;
	
	private double bTarget = 42;
	
	private double cTarget = 42;
	
	private double dTarget = 42;
	
	

	private enum ClimberState {
		FOLDED, UNFOLDING, UNFOLDED, RAISING, RAISED, CLIMBING, CLIMBED
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
				this.state = ClimberState.CLIMBING;
				pid = new TurtleAsymmetricPID(TurtwigConstants.cClimber, TurtwigConstants.dClimber, cTarget, dTarget, TurtwigConstants.kABA2, TurtwigConstants.kABB2, TurtwigConstants.cTolerance, TurtwigConstants.dTolerance);
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
			if (unfoldTimer.get() > unfoldTime) {
				state = ClimberState.UNFOLDED;
				unfoldTimer.stop();
			}
				
			break;
		case UNFOLDED: //starts the climbing
			state = ClimberState.RAISING;
			pid = new TurtleAsymmetricPID(TurtwigConstants.aClimber, TurtwigConstants.bClimber, aTarget, bTarget, TurtwigConstants.kABA, TurtwigConstants.kABB, TurtwigConstants.aTolerance, TurtwigConstants.bTolerance);
			break;
		case RAISING:// checks if it is done raising
		    	if(pid.atTarget()){
		    	    /*
		    	     * TODO: make PID null
		    	     */
		    	    pid = null;
		    	    state = ClimberState.RAISED;
		    	    
		    	}
			break;
		case CLIMBING:
		    if(pid.atTarget()){
		    	    /*
		    	     * TODO: make PID null
		    	     */
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
