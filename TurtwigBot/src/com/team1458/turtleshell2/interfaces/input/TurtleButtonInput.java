package com.team1458.turtleshell2.interfaces.input;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a joystick or physical driver station button
 *
 * @author asinghani
 */
public abstract class TurtleButtonInput {
	boolean lastValue = false;
	boolean pressed = false;
	boolean released = false;

	Timer timer;

	public TurtleButtonInput() {
		timer = new Timer();

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(get() && !lastValue) {
					pressed = true;
					released = false;
				} else if(!get() && lastValue) {
					pressed = false;
					released = true;
				}
			}
		}, 50, 50);
	}

	public abstract boolean get();

	public boolean getDown() {
		if(pressed) {
			pressed = false;
			return true;
		}
		return false;
	}

	public boolean getUp() {
		if(released) {
			released = false;
			return true;
		}
		return false;
	}
}
