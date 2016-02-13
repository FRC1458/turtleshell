package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.Input;
import com.team1458.turtleshell.MotorValue;
import com.team1458.turtleshell.TurtleDualPID;
import com.team1458.turtleshell.TurtleEncoder;
import com.team1458.turtleshell.TurtleMaths;
import com.team1458.turtleshell.TurtleMotor;
import com.team1458.turtleshell.TurtleRobotComponent;
import com.team1458.turtleshell.TurtleStraightDrivePID;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleVictor;

public class TurtwigIntake implements TurtleRobotComponent {
    private TurtleMotor lMotor = new TurtleVictor(TurtwigConstants.LEFTINTAKEVICTORPORT, false);
    private TurtleMotor rMotor = new TurtleVictor(TurtwigConstants.RIGHTINTAKEVICTORPORT, true);
    private TurtleMotor sMotor = new TurtleVictor(TurtwigConstants.SPININTAKEVICTORPORT, false);
    private TurtleEncoder lEncoder = new Turtle4PinEncoder(TurtwigConstants.LEFTINTAKEENCODERPORT1, TurtwigConstants.LEFTINTAKEENCODERPORT2, false);
    private TurtleEncoder rEncoder = new Turtle4PinEncoder(TurtwigConstants.RIGHTINTAKEENCODERPORT1, TurtwigConstants.RIGHTINTAKEENCODERPORT2, true);
    private TurtleDualPID pid;

    @Override
    public void init() {

    }

    @Override
    public void teleUpdate() {
	if (TurtleMaths.absDiff(Input.getRPower(), 0) < .1) {
	    if (pid == null) {
		pid = new TurtleStraightDrivePID(TurtwigConstants.straightConstants, rEncoder.getTicks(), 0.0001);
	    }
	    MotorValue[] v = pid.newValue(new double[] { lEncoder.getTicks(), rEncoder.getTicks(), lEncoder.getRate(), rEncoder.getRate() });
	    lMotor.set(v[0]);
	    rMotor.set(v[1]);

	} else {
	    pid = null;
	    driveMotors(new MotorValue(1 * Input.getRPower()));
	}

	sMotor.set(new MotorValue(Input.getLPower()));
    }

    @Override
    public void stop() {
	driveMotors(MotorValue.zero);
    }

    private void driveMotors(MotorValue power) {
	lMotor.set(power);
	rMotor.set(power);
    }

}
