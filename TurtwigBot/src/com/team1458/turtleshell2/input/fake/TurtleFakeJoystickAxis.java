package com.team1458.turtleshell2.input.fake;

import com.team1458.turtleshell2.input.JoystickAxis;

public class TurtleFakeJoystickAxis extends JoystickAxis {

	public TurtleFakeJoystickAxis(Object... args) {
		super(null, null);
	}

	@Override
	public double get() {
		return 0.0;
	}

}
