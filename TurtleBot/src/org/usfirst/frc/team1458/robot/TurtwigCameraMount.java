package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.Input;
import com.team1458.turtleshell.Input.XboxAxis;
import com.team1458.turtleshell.Output;
import com.team1458.turtleshell.TurtleMaths;
import com.team1458.turtleshell.TurtleMaths.RangeShifter;
import com.team1458.turtleshell.TurtleRobotComponent;
import com.team1458.turtleshell.physical.TurtleServo;

public class TurtwigCameraMount implements TurtleRobotComponent{
	private TurtleServo horizontalServo = new TurtleServo(8, 90,72,15);
	private TurtleServo verticalServo = new TurtleServo(9, 90,72,15);
	private RangeShifter horizontalShifter = new RangeShifter(-1, 1, -1, 1);
	private RangeShifter verticalShifter = new RangeShifter(-1, 1, -1, 1);

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teleUpdate() {
	    Output.outputNumber("horizontal", Input.getXboxAxis(XboxAxis.RX));
	    Output.outputNumber("vertical", Input.getXboxAxis(XboxAxis.RY));
		horizontalServo.updateAngle(horizontalShifter.shift(TurtleMaths.deadband(Input.getXboxAxis(XboxAxis.RX), 0.15)));
		verticalServo.updateAngle(verticalShifter.shift(TurtleMaths.deadband(Input.getXboxAxis(XboxAxis.LY), 0.15)));
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}
