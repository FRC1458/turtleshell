package com.team1458.turtleshell2.input;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a joystick or physical driver station button
 *
 * @author asinghani
 */
public abstract class TurtleSampleButtonInput implements TurtleButtonInput {
	private boolean lastValue = false;
	boolean pressed = false;
	boolean released = false;

	Timer timer;

	public TurtleSampleButtonInput() {
		timer = new Timer();

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(getButton() && !lastValue) {
					pressed = true;
					released = false;
				} else if(!getButton() && lastValue) {
					pressed = false;
					released = true;
				}
			}
		}, 50, 50);
	}

	public abstract boolean getButton();

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

	@Override
	public int get() {
		return getButton() ? 1 : 0;
	}
}
