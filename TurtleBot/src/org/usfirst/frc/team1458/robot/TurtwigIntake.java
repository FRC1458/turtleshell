package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.component.TurtleRobotComponent;
import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.movement.TurtleMotor;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleDigitalLimitSwitch;
import com.team1458.turtleshell.physical.TurtleEncoderLimit;
import com.team1458.turtleshell.physical.TurtleVictor;
import com.team1458.turtleshell.physical.TurtleVictorSP;
import com.team1458.turtleshell.pid.TurtleDualPID;
import com.team1458.turtleshell.pid.TurtlePDD2;
import com.team1458.turtleshell.pid.TurtlePID;
import com.team1458.turtleshell.pid.TurtleStraightDrivePID;
import com.team1458.turtleshell.sensor.TurtleEncoder;
import com.team1458.turtleshell.sensor.TurtleLimitSwitch;
import com.team1458.turtleshell.util.Input;
import com.team1458.turtleshell.util.MotorValue;
import com.team1458.turtleshell.util.Output;
import com.team1458.turtleshell.util.TurtleMaths;
import com.team1458.turtleshell.util.Input.XboxAxis;
import com.team1458.turtleshell.util.Input.XboxButton;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurtwigIntake implements TurtleRobotComponent {

    private static TurtwigIntake instance;

    public static TurtwigIntake getInstance() {
	if (instance == null) {
	    instance = new TurtwigIntake();
	}
	return instance;
    }

    private TurtleMotor lMotor = new TurtleVictor(TurtwigConstants.LEFTINTAKEVICTORPORT, false);
    private TurtleMotor rMotor = new TurtleVictor(TurtwigConstants.RIGHTINTAKEVICTORPORT, true);

    private TurtleEncoder lEncoder = new Turtle4PinEncoder(TurtwigConstants.LEFTINTAKEENCODERPORT1, TurtwigConstants.LEFTINTAKEENCODERPORT2, true);
    private TurtleEncoder rEncoder = new Turtle4PinEncoder(TurtwigConstants.RIGHTINTAKEENCODERPORT1, TurtwigConstants.RIGHTINTAKEENCODERPORT2, false);

    private TurtleMotor sMotor = new TurtleVictorSP(TurtwigConstants.SPININTAKEVICTORSPPORT, false);
    private TurtlePID pid;

    private TurtleLimitSwitch ballLimit = new TurtleDigitalLimitSwitch(TurtwigConstants.BALLLIMITSWITCHPORT, true);
    private TurtleLimitSwitch intakeTopLimit = new TurtleEncoderLimit(rEncoder, 0, false);
    private boolean intakeTopLimitFlag = false;

    @Override
    public void init() {

    }

    @Override
    public void teleUpdate() {
	Output.outputNumber("lEncoder", lEncoder.getTicks());
	Output.outputNumber("rEncoder", rEncoder.getTicks());

	// driveMotors(new MotorValue(Input.getXboxAxis(XboxAxis.LY)));

	if (Input.getXboxAxis(XboxAxis.LY) != 0) {
	    SmartDashboard.putBoolean("Intake top limit hit", intakeTopLimit.isPressed());
	    if (pid != null) {
		TurtleLogger.info("Started moving, not holding");
		pid = null;
	    }

	    if (intakeTopLimit.isPressed() && !intakeTopLimitFlag) { //
		// First press of top limit
		TurtleLogger.severe("Hit top limit");
		intakeTopLimitFlag = true;
	    } else if (!intakeTopLimit.isPressed()) { // Intake top limit has
						      // been released
		intakeTopLimitFlag = false;
	    }
	    if (intakeTopLimit.isPressed() && Input.getXboxAxis(XboxAxis.LY) < 0) {
		TurtleLogger.warning("Attempting to move intake up while limit switch is preventing it");
	    } else {
		driveMotors(new MotorValue(Input.getXboxAxis(XboxAxis.LY)));
	    }

	} else if (pid == null) {
	    TurtleLogger.info("Holding position");
	    double currentPos = TurtleMaths.fitRange(TurtleMaths.avg(lEncoder.getTicks(), rEncoder.getTicks()), 0, TurtwigConstants.INTAKEENCODERMAX);
	    pid = new TurtlePDD2(TurtwigConstants.intakePIDConstants, currentPos, 0);
	    
	} else {
	    driveMotors(pid.newValue(new double[] { TurtleMaths.avg(lEncoder.getTicks(), rEncoder.getTicks()),
		    TurtleMaths.avg(lEncoder.getRate(), rEncoder.getRate()) }));
	    Output.outputNumber("lIntakePower", lMotor.get().getValue());
	    Output.outputNumber("rIntakePower", rMotor.get().getValue());
	}
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
	//sMotor.set(new MotorValue(Input.getXboxAxis(XboxAxis.RY)));
    }

    @Override
    public void stop() {
	TurtleLogger.warning("Intake stopping");
	lMotor.stop();
	rMotor.stop();
	sMotor.stop();
    }

    private void driveMotors(MotorValue power) {
	lMotor.set(power);
	rMotor.set(power);
    }

    private void driveMotors(MotorValue[] powers) {
	lMotor.set(powers[0]);
	rMotor.set(powers[1]);
    }

}
