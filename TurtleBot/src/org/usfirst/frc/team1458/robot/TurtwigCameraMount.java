package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.component.TurtleRobotComponent;
import com.team1458.turtleshell.movement.TurtleSmartServo;
import com.team1458.turtleshell.physical.TurtlePWMServo;
import com.team1458.turtleshell.util.Input;
import com.team1458.turtleshell.util.Output;
import com.team1458.turtleshell.util.TurtleMaths;
import com.team1458.turtleshell.util.Input.XboxAxis;
import com.team1458.turtleshell.util.TurtleMaths.RangeShifter;

public class TurtwigCameraMount implements TurtleRobotComponent{
	
	private static TurtwigCameraMount instance;
	public static TurtwigCameraMount getInstance() {
		if(instance==null) {
			instance = new TurtwigCameraMount();
		}
		return instance;
	}
	
	private TurtleSmartServo horizontalServo = new TurtlePWMServo(8, 90,72,15);
	private TurtleSmartServo verticalServo = new TurtlePWMServo(9, 90,72,15);
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
