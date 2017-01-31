package com.team1458.turtleshell2.input.fake;

import com.team1458.turtleshell2.input.TurtleJoystickPOVSwitch;

public class TurtleFakeJoystickPOVSwitch extends TurtleJoystickPOVSwitch {

	public TurtleFakeJoystickPOVSwitch(Object... args) {
		super(null, 0);
	}

	public int get() {
		return POVValue.CENTER.val;
	}
}
