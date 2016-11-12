package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.sensor.TurtleLimitSwitch;

import edu.wpi.first.wpilibj.DigitalInput;

public class TurtleDigitalLimitSwitch implements TurtleLimitSwitch {
    private final DigitalInput i;
    private final boolean isReversed;

    public TurtleDigitalLimitSwitch(int port) {
	this(port, false);
    }

    public TurtleDigitalLimitSwitch(int port, boolean isReversed) {
	i = new DigitalInput(port);
	this.isReversed = isReversed;
    }

    @Override
    public void update() {
	// nothing
    }

    @Override
    public boolean isPressed() {
	return isReversed ^ i.get();//XORs it, reversing it (This is the right way)
    }
}
