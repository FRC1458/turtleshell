package com.team1458.turtleshell2.implementations.input.fake;

import com.team1458.turtleshell2.implementations.input.TurtleJoystickAxis;

public class TurtleFakeJoystickAxis extends TurtleJoystickAxis {

	public TurtleFakeJoystickAxis(Object... args) {
		super(null, null);
	}

	@Override
	public double get() {
		return 0.0;
	}

}
