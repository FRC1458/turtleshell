package com.team1458.turtleshell2.implementations.input;

import com.team1458.turtleshell2.interfaces.input.TurtleButtonInput;
import com.team1458.turtleshell2.interfaces.input.TurtleDigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class TurtleJoystickButton extends TurtleButtonInput {
	private final Joystick masterJoystick;
	private final int buttonNum;
	
	public TurtleJoystickButton(Joystick j, int buttonNum) {
		this.masterJoystick = j;
		this.buttonNum = buttonNum;
	}
	
	@Override
	public boolean getButton() {
		return masterJoystick.getRawButton(buttonNum);
	}
}
