package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.component.TurtleSmartRobotComponent;
import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.movement.TurtleMotor;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleDigitalLimitSwitch;
import com.team1458.turtleshell.physical.TurtleTalon;
import com.team1458.turtleshell.physical.TurtleVictor;
import com.team1458.turtleshell.pid.TurtlePDD2;
import com.team1458.turtleshell.pid.TurtlePID;
import com.team1458.turtleshell.sensor.TurtleEncoder;
import com.team1458.turtleshell.sensor.TurtleLimitSwitch;
import com.team1458.turtleshell.util.Input;
import com.team1458.turtleshell.util.MotorValue;
import com.team1458.turtleshell.util.Output;
import com.team1458.turtleshell.util.TurtleMaths;
import com.team1458.turtleshell.util.Input.XboxAxis;
import com.team1458.turtleshell.util.Input.XboxButton;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurtwigPIDIntake implements TurtleSmartRobotComponent {

    private static TurtwigPIDIntake instance;

    public static TurtwigPIDIntake getInstance() {
	if (instance == null) {
	    instance = new TurtwigPIDIntake();
	}
	return instance;
    }

    private TurtleMotor lMotor = new TurtleVictor(TurtwigConstants.LEFTINTAKEVICTORPORT, false);
    private TurtleMotor rMotor = new TurtleVictor(TurtwigConstants.RIGHTINTAKEVICTORPORT, true);

    private TurtleEncoder lEncoder = new Turtle4PinEncoder(TurtwigConstants.LEFTINTAKEENCODERPORT1, TurtwigConstants.LEFTINTAKEENCODERPORT2, true);
    private TurtleEncoder rEncoder = new Turtle4PinEncoder(TurtwigConstants.RIGHTINTAKEENCODERPORT1, TurtwigConstants.RIGHTINTAKEENCODERPORT2, true);

    private TurtleMotor sMotor = new TurtleTalon(TurtwigConstants.SPININTAKETALONPORT, false);
    private TurtlePID pid;

    private TurtleLimitSwitch ballLimit = new TurtleDigitalLimitSwitch(TurtwigConstants.BALLLIMITSWITCHPORT, true);

    private TurtleLimitSwitch intakeTopLimit = new TurtleDigitalLimitSwitch(TurtwigConstants.INTAKETOPLIMITPORT, true);

    private MotorValue smoother = MotorValue.zero;

    private Timer inputTimer = new Timer();
    private double intakeIdealPosition = 0;

    @Override
    public void init() {
	inputTimer.start();
	resetEncoders();
    }

    public void resetEncoders() {
	lEncoder.reset();
	rEncoder.reset();
	pid = new TurtlePDD2(TurtwigConstants.intakePIDConstants, 0, 0);
	inputTimer.reset();
    }

    @Override
    public void teleUpdate() {
	Output.outputNumber("lIntakeEncoder", lEncoder.getTicks());
	Output.outputNumber("rIntakeEncoder", rEncoder.getTicks());

	// driveMotors(new MotorValue(Input.getXboxAxis(XboxAxis.LY)));

	if (Input.getXboxAxis(XboxAxis.LY) != 0) {
	    intakeIdealPosition += -Input.getXboxAxis(XboxAxis.LY) * inputTimer.get() * TurtwigConstants.intakePIDScale;
	    intakeIdealPosition = TurtleMaths.fitRange(intakeIdealPosition, getEncoderTicks() - 120,
		    getEncoderTicks() + 120);
	    Output.outputNumber("IntakePID Target", intakeIdealPosition);
	    pid = new TurtlePDD2(TurtwigConstants.intakePIDConstants, intakeIdealPosition, 0.0);

	}
	inputTimer.reset();
	smoother = new MotorValue(smoother.getValue()
		* TurtwigConstants.intakePIDCurrentWeight
		+ (1 - TurtwigConstants.intakePIDCurrentWeight)
		* pid.newValue(
			new double[] { getEncoderTicks(), getEncoderRate() })
			.getValue());
	driveMotors(smoother);
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
	// sMotor.set(new MotorValue(Input.getXboxAxis(XboxAxis.RY)));
    }

    @Override
    public void stop() {
	TurtleLogger.warning("Intake stopping");
	lMotor.stop();
	rMotor.stop();
	sMotor.stop();
    }

    private void driveMotors(MotorValue power) {
	if (!intakeTopLimit.isPressed()) {
	    // can move up
	    lMotor.set(power);
	    rMotor.set(power);
	}
	Output.outputNumber("lIntakePower", lMotor.get().getValue());
	Output.outputNumber("rIntakePower", rMotor.get().getValue());
    }

    @Override
    public void autoUpdate() {
	Output.outputNumber("lIntakeEncoder", lEncoder.getTicks());
	Output.outputNumber("rIntakeEncoder", rEncoder.getTicks());
	smoother = new MotorValue(smoother.getValue()
		* TurtwigConstants.intakePIDCurrentWeight
		+ (1 - TurtwigConstants.intakePIDCurrentWeight)
		* pid.newValue(
			new double[] { getEncoderTicks(), getEncoderRate() })
			.getValue());
	driveMotors(smoother);
	Output.outputNumber("lIntakePower", lMotor.get().getValue());
	Output.outputNumber("rIntakePower", rMotor.get().getValue());

    }

    private double getEncoderTicks() {
	return TurtleMaths.avg(lEncoder.getTicks(), rEncoder.getTicks());
    }
    
    private double getEncoderRate() {
	return TurtleMaths.avg(lEncoder.getRate(), rEncoder.getRate());
    }

    public void setPosition(double pos) {
	pid = new TurtlePDD2(TurtwigConstants.intakePIDConstants, pos, 0.0);
    }
}