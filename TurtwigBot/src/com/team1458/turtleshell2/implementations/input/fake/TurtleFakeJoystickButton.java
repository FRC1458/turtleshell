package com.team1458.turtleshell2.implementations.input.fake;

import com.team1458.turtleshell2.implementations.input.TurtleJoystickButton;

public class TurtleFakeJoystickButton extends TurtleJoystickButton {
	
	public TurtleFakeJoystickButton(Object... args) {
		super(null, 0);
	}
	
	@Override
	public boolean get() {
		return false;
	}
}
