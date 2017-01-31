package com.team1458.turtleshell2.input.fake;

import com.team1458.turtleshell2.input.TurtleJoystickButton;

public class TurtleFakeJoystickButton extends TurtleJoystickButton {
	
	public TurtleFakeJoystickButton(Object... args) {
		super(null, 0);
	}
	
	@Override
	public boolean getButton() {
		return false;
	}
}
