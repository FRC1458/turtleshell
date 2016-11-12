package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleJoystickPOVSwitch;
import com.team1458.turtleshell2.implementations.movement.TurtleTalonSR;
import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.types.MotorValue;
import com.team1458.turtleshell2.implementations.input.TurtleJoystickPOVSwitch.POVValue;

public class Turtwig2016DumbClimber implements TurtleComponent {

    private static Turtwig2016DumbClimber instance;
    private TurtleAnalogInput hookInputAxis;
    private TurtleJoystickPOVSwitch winchInputAxis;

    public static Turtwig2016DumbClimber getInstance() {
	if (instance == null) {
	    instance = new Turtwig2016DumbClimber();
	}
	return instance;
    }

    @Override
    public void teleUpdate() {
	//Output.outputNumber("Hook encoder", hookEncoder.getTicks());
	if (POVValue.POVFromValue(winchInputAxis.get()) == POVValue.N) { 
	    /*
	     * Checks winch input axis. 
	     * For that value, turns it into a POV(Point of View) value
	     * Checks if that represents up or north 
	     * (Up == North)
	     * Sets it to go backwards
	     */
	    /*
	     * if (hookEncoder.getTicks() >
	     * TurtwigConstants.hookLiftEncoderTicks) {
	     * TurtleLogger.warning("Trying to put arm beyond limit"); } else {
	     */
	    hookWinch.set(MotorValue.fullBackward);
	    // }
	} else if (POVValue.POVFromValue(winchInputAxis.get()) == POVValue.S) {
	    hookWinch.set(new MotorValue(0.3));
	} else {
	    hookWinch.set(MotorValue.zero);
	}
	
	powerWinch.set(new MotorValue(hookInputAxis.get()));
	
    }

    private TurtleMotor powerWinch = new TurtleVictor888(TurtwigConstants.POWERWINCHVICTORPORT, false);
    private TurtleMotor hookWinch = new TurtleTalonSR(TurtwigConstants.HOOKWINCHTALONPORT, false);

   // private TurtleEncoder hookEncoder = new Turtle4PinEncoder(TurtwigConstants.HOOKWINCHENCODERPORT1, TurtwigConstants.HOOKWINCHENCODERPORT2, false);

    @Override
    public void autoUpdate() {
	// TODO Auto-generated method stub
	
    }
}