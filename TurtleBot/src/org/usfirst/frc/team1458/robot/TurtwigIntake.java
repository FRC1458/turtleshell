package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.component.TurtleRobotComponent;
import com.team1458.turtleshell.movement.TurtleMotor;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleTalon;
import com.team1458.turtleshell.physical.TurtleVictor;
import com.team1458.turtleshell.physical.TurtleVictorSP;
import com.team1458.turtleshell.pid.TurtleDualPID;
import com.team1458.turtleshell.pid.TurtleStraightDrivePID;
import com.team1458.turtleshell.sensor.TurtleEncoder;
import com.team1458.turtleshell.util.Input;
import com.team1458.turtleshell.util.MotorValue;
import com.team1458.turtleshell.util.TurtleMaths;
import com.team1458.turtleshell.util.Input.XboxAxis;
import com.team1458.turtleshell.util.Input.XboxButton;

public class TurtwigIntake implements TurtleRobotComponent {
	
	private static TurtwigIntake instance;
	public static TurtwigIntake getInstance() {
		if(instance==null) {
			instance = new TurtwigIntake();
		}
		return instance;
	}
	
	private TurtleMotor lMotor = new TurtleVictor(TurtwigConstants.LEFTINTAKEVICTORPORT, false);
	private TurtleMotor rMotor = new TurtleVictor(TurtwigConstants.RIGHTINTAKEVICTORPORT, true);

	private TurtleEncoder lEncoder = new Turtle4PinEncoder(TurtwigConstants.LEFTINTAKEENCODERPORT1,
			TurtwigConstants.LEFTINTAKEENCODERPORT2, false);
	private TurtleEncoder rEncoder = new Turtle4PinEncoder(TurtwigConstants.RIGHTINTAKEENCODERPORT1,
			TurtwigConstants.RIGHTINTAKEENCODERPORT2, true);

	private TurtleMotor sMotor = new TurtleVictorSP(TurtwigConstants.SPININTAKEVICTORSPPORT, false);
	private TurtleDualPID pid;

	@Override
	public void init() {

	}

	@Override
	public void teleUpdate() {
		if (Input.getXboxAxis(XboxAxis.LY) != 0) {
			pid = null;
			driveMotors(new MotorValue(Input.getXboxAxis(XboxAxis.LY)));
		} else if (pid == null) {
			pid = new TurtleStraightDrivePID(TurtwigConstants.intakePIDConstants,
					TurtleMaths.avg(lEncoder.getTicks(), rEncoder.getTicks()), TurtwigConstants.intakePIDkLR, 0.0);
		} else {
			driveMotors(pid.newValue(
					new double[] { lEncoder.getTicks(), rEncoder.getTicks(), lEncoder.getRate(), rEncoder.getRate() }));
		}

		sMotor.set(Input.getXboxButton(XboxButton.LBUMP) || Input.getXboxButton(XboxButton.RBUMP)
				? (Input.getXboxButton(XboxButton.LBUMP) ? MotorValue.fullBackward : MotorValue.fullForward)
				: MotorValue.zero);// don't look at this
	}

	@Override
	public void stop() {
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
