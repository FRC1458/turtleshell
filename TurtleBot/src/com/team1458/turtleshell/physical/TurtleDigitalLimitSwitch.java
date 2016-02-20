package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.sensor.TurtleLimitSwitch;

import edu.wpi.first.wpilibj.DigitalInput;

public class TurtleDigitalLimitSwitch implements TurtleLimitSwitch {
	private final DigitalInput i;

	public TurtleDigitalLimitSwitch(int port) {
		i = new DigitalInput(port);
	}

	@Override
	public void update() {
		// nothing
	}

	@Override
	public boolean isPressed() {
		return i.get();
	}
}
