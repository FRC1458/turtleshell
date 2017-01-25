package com.team1458.turtleshell2.implementations.input.fake;

import com.team1458.turtleshell2.implementations.input.TurtleJoystickPOVSwitch;

public class TurtleFakeJoystickPOVSwitch extends TurtleJoystickPOVSwitch {

	public TurtleFakeJoystickPOVSwitch(Object... args) {
		super(null, 0);
	}

	public POVValue get() {
		return POVValue.CENTER;
	}
}
