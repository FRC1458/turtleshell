package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.component.TurtleRobotComponent;
import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.movement.TurtleMotor;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleTalon;
import com.team1458.turtleshell.physical.TurtleVictor;
import com.team1458.turtleshell.pid.TurtlePDD2;
import com.team1458.turtleshell.pid.TurtlePID;
import com.team1458.turtleshell.sensor.TurtleEncoder;
import com.team1458.turtleshell.util.Input;
import com.team1458.turtleshell.util.Input.POV;
import com.team1458.turtleshell.util.Input.XboxAxis;
import com.team1458.turtleshell.util.MotorValue;
import com.team1458.turtleshell.util.Input.XboxButton;

import edu.wpi.first.wpilibj.Timer;

public class TurtwigClimber2 implements TurtleRobotComponent {

    private static TurtwigClimber2 instance;

    public static TurtwigClimber2 getInstance() {
	if (instance == null) {
	    instance = new TurtwigClimber2();
	}
	return instance;
    }

    private TurtleMotor powerWinch = new TurtleVictor(TurtwigConstants.POWERWINCHVICTORPORT, false);
    private TurtleMotor hookWinch = new TurtleTalon(TurtwigConstants.HOOKWINCHTALONPORT, false);

    private TurtleEncoder hookEncoder = new Turtle4PinEncoder(TurtwigConstants.HOOKWINCHENCODERPORT1, TurtwigConstants.HOOKWINCHENCODERPORT2, false);

    private TurtlePID pid;

    private Timer timer = new Timer();

    private ClimberState state = ClimberState.FOLDED;

    private enum ClimberState {
	FOLDED, UNFOLDING, UNFOLDED, RAISING, RAISED, YANKING, RETRACTING, CLIMBING, CLIMBED;
    }

    @Override
    public void init() {

    }

    @Override
    public void teleUpdate() {

	if (Input.getXBoxPOV() == POV.N) {
	    hookWinch.set(MotorValue.fullBackward);
	} else if (Input.getXBoxPOV() == POV.S) {
	    hookWinch.set(MotorValue.fullForward);
	}
	
	powerWinch.set(new MotorValue(-Input.getXboxAxis(XboxAxis.RY)));

	// Determine using user input and measurements whether it should switch
	// state

	/*
	 * switch (state) {
	 * 
	 * case CLIMBED: break; case CLIMBING: break; case RETRACTING: if
	 * (pid.atTarget()) { this.state = ClimberState.CLIMBING; pid = null; }
	 * break; case YANKING: if (timer.get() > TurtwigConstants.yankTime) {
	 * this.state = ClimberState.RETRACTING; pid = new
	 * TurtlePDD2(TurtwigConstants.hookLowerConstants,
	 * TurtwigConstants.hookDownEncoderTicks,
	 * TurtwigConstants.drivePIDTolerance); } break;
	 * 
	 * case RAISED: if (Input.getXboxButton(XboxButton.Y)) { this.state =
	 * ClimberState.YANKING; timer.start(); } break; case RAISING: if
	 * (pid.atTarget()) { state = ClimberState.RAISED; pid = null; } break;
	 * case UNFOLDED: state = ClimberState.RAISING; hookEncoder.reset(); pid
	 * = new TurtlePDD2(TurtwigConstants.hookRaiseConstants,
	 * TurtwigConstants.hookLiftEncoderTicks,
	 * TurtwigConstants.drivePIDTolerance); break; case UNFOLDING: if
	 * (timer.get() > TurtwigConstants.unfoldTime) { state =
	 * ClimberState.UNFOLDED; timer.stop(); timer.reset(); } break; case
	 * FOLDED: if (Input.getXboxButton(XboxButton.Y)) { state =
	 * ClimberState.UNFOLDING; timer.start(); } break; default:
	 * TurtleLogger.critical("Unknown climber state"); break; }
	 * 
	 * // Take action based on the state switch (state) { case CLIMBED:
	 * break; case CLIMBING: if (Input.getXboxButton(XboxButton.Y)) {
	 * powerWinch.set(MotorValue.fullForward); } else {
	 * powerWinch.set(MotorValue.zero); } break; case RETRACTING:
	 * powerWinch.set(MotorValue.zero); hookWinch.set(pid.newValue(new
	 * double[] { hookEncoder.getTicks(), hookEncoder.getRate() })); break;
	 * case YANKING: powerWinch.set(MotorValue.fullBackward); break; case
	 * FOLDED: break; case RAISED: hookWinch.set(MotorValue.zero); break;
	 * case RAISING: hookWinch.set(pid.newValue(new double[] {
	 * hookEncoder.getTicks(), hookEncoder.getRate() })); break; case
	 * UNFOLDED: powerWinch.set(MotorValue.zero); break; case UNFOLDING:
	 * powerWinch.set(MotorValue.fullForward); break; default: break;
	 * 
	 * }
	 */
    }

    @Override
    public void stop() {
	powerWinch.stop();
	hookWinch.stop();
    }

}
