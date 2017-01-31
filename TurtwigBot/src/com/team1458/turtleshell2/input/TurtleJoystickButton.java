package com.team1458.turtleshell2.input;

import edu.wpi.first.wpilibj.Joystick;

public class TurtleJoystickButton extends TurtleSampleButtonInput {
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

	@Override
	public int get() {
		return getButton() ? 1 : 0;
	}
}
