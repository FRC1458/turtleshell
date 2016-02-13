package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.Input;
import com.team1458.turtleshell.MotorValue;
import com.team1458.turtleshell.TurtleMotor;
import com.team1458.turtleshell.TurtleRobotComponent;
import com.team1458.turtleshell.physical.TurtleVictor;

public class TurtwigIntake implements TurtleRobotComponent {
	private TurtleMotor lMotor = new TurtleVictor(TurtwigConstants.LEFTINTAKEVICTORPORT, false);
	private TurtleMotor rMotor = new TurtleVictor(TurtwigConstants.RIGHTINTAKEVICTORPORT, false);

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teleUpdate() {
		driveMotors(new MotorValue(Input.getRPower()));
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
