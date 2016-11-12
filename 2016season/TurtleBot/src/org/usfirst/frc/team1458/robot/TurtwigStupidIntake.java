package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.component.TurtleRobotComponent;
import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.movement.TurtleMotor;
import com.team1458.turtleshell.physical.TurtleDigitalLimitSwitch;
import com.team1458.turtleshell.physical.TurtleTalon;
import com.team1458.turtleshell.physical.TurtleVictor;
import com.team1458.turtleshell.sensor.TurtleLimitSwitch;
import com.team1458.turtleshell.util.Input;
import com.team1458.turtleshell.util.Input.XboxAxis;
import com.team1458.turtleshell.util.MotorValue;
import com.team1458.turtleshell.util.Output;
import com.team1458.turtleshell.util.Input.XboxButton;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurtwigStupidIntake implements TurtleRobotComponent {
    private static TurtwigStupidIntake instance;
    public static TurtwigStupidIntake getInstance() {
	if(instance==null) {
	    instance = new TurtwigStupidIntake();
	}
	return instance;
    }
    
    private TurtleMotor lMotor = new TurtleVictor(TurtwigConstants.LEFTINTAKEVICTORPORT, false);
    private TurtleMotor rMotor = new TurtleVictor(TurtwigConstants.RIGHTINTAKEVICTORPORT, true);
    
    private TurtleMotor sMotor = new TurtleTalon(TurtwigConstants.SPININTAKETALONPORT, false);
    
    private TurtleLimitSwitch ballLimit = new TurtleDigitalLimitSwitch(TurtwigConstants.BALLLIMITSWITCHPORT, true);

    @Override
    public void init() {
	// TODO Auto-generated method stub
	
    }

    @Override
    public void teleUpdate() {

	lMotor.set(new MotorValue(1.0*Input.getXboxAxis(XboxAxis.LY)));
	rMotor.set(new MotorValue(1.0*Input.getXboxAxis(XboxAxis.LY)));
	Output.outputNumber("lIntakePower", lMotor.get().getValue());
	Output.outputNumber("rIntakePower", rMotor.get().getValue());

	Output.outputBoolean("ballLimit", ballLimit.isPressed());

	if (Input.getXboxButton(XboxButton.LBUMP)) {
	    // should intake
	    if (ballLimit.isPressed()) {
		SmartDashboard.putString("Spinny", "limit");
		TurtleLogger.warning("Trying to spin intake, but already pressed");
		sMotor.set(MotorValue.zero);
	    } else {
		SmartDashboard.putString("Spinny", "in");
		sMotor.set(MotorValue.fullForward);
	    }
	} else if (Input.getXboxButton(XboxButton.RBUMP)) {
	    // should outtake
	    SmartDashboard.putString("Spinny", "out");
	    sMotor.set(MotorValue.fullBackward);
	} else {
	    // neither pressed, should stop
	    SmartDashboard.putString("Spinny", "stop");
	    sMotor.set(MotorValue.zero);
	}

	
    }

    @Override
    public void stop() {
	// TODO Auto-generated method stub
	
    }

}
