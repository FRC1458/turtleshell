package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.movement.TurtleTalonSR;
import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.types.MotorValue;

public class Turtwig2016DumbClimber implements TurtleComponent {

    private static Turtwig2016DumbClimber instance;
    private TurtleAnalogInput hookInputAxis;
    private TurtleAnalogInput winchInputAxis;
    

    public static Turtwig2016DumbClimber getInstance() {
	if (instance == null) {
	    instance = new Turtwig2016DumbClimber();
	}
	return instance;
    }

    @Override
    public void teleUpdate() {
	//Output.outputNumber("Hook encoder", hookEncoder.getTicks());
	if (Input.getXBoxPOV() == POV.N) {
	    /*
	     * if (hookEncoder.getTicks() >
	     * TurtwigConstants.hookLiftEncoderTicks) {
	     * TurtleLogger.warning("Trying to put arm beyond limit"); } else {
	     */
	    hookWinch.set(MotorValue.fullBackward);
	    // }
	} else if (Input.getXBoxPOV() == POV.S) {
	    hookWinch.set(new MotorValue(0.3));
	} else {
	    hookWinch.set(MotorValue.zero);
	}
    }

    private TurtleMotor powerWinch = new TurtleVictor888(TurtwigConstants.POWERWINCHVICTORPORT, false);
    private TurtleMotor hookWinch = new TurtleTalonSR(TurtwigConstants.HOOKWINCHTALONPORT, false);

   // private TurtleEncoder hookEncoder = new Turtle4PinEncoder(TurtwigConstants.HOOKWINCHENCODERPORT1, TurtwigConstants.HOOKWINCHENCODERPORT2, false);

   public void getHookInputAxis(TurtleAnalogInput hIA)
   {
 get{hookInputAxis;}
   set{hookInputAxis=value;}
   }


    

    @Override
    public void autoUpdate() {
	// TODO Auto-generated method stub
	
    }
}