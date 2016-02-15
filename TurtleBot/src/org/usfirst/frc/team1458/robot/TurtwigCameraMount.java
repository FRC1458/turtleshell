package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.Input;
import com.team1458.turtleshell.TurtleMaths.RangeShifter;
import com.team1458.turtleshell.TurtleRobotComponent;
import com.team1458.turtleshell.physical.TurtleServo;

public class TurtwigCameraMount implements TurtleRobotComponent{
	private TurtleServo thetaServo = new TurtleServo(0);
	private TurtleServo phiServo = new TurtleServo(1);
	private RangeShifter thetaShifter = new RangeShifter(-1, 1, 0, 180);
	private RangeShifter phiShifter = new RangeShifter(-1, 1, 0, 180);

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teleUpdate() {
		thetaServo.setAngle(thetaShifter.shift(Input.getLPower()));
		phiServo.setAngle(phiShifter.shift(Input.getRPower()));
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
