package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.Input;
import com.team1458.turtleshell.MotorValue;
import com.team1458.turtleshell.TurtleMotor;
import com.team1458.turtleshell.TurtleRobotComponent;
import com.team1458.turtleshell.physical.TurtleVictor;

public class TurtwigIntake implements TurtleRobotComponent {
	private TurtleMotor lMotor = new TurtleVictor(TurtwigConstants.LEFTINTAKEVICTORPORT, false);
	private TurtleMotor rMotor = new TurtleVictor(TurtwigConstants.RIGHTINTAKEVICTORPORT, true);
	private TurtleMotor sMotor = new TurtleVictor(TurtwigConstants.SPININTAKEVICTORPORT, false);

	@Override
	public void init() {
		
	}

	@Override
	public void teleUpdate() {
		driveMotors(new MotorValue(Input.getRPower()));
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
