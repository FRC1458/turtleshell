package com.team1458.turtleshell2.input;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Represents a joystick or physical driver station button
 *
 * @author asinghani
 */
public abstract class SampleButtonInput implements ButtonInput {
	private boolean lastValue = false;
	boolean pressed = false;
	boolean released = false;
	boolean last = false;

	Timer timer;

	public SampleButtonInput() {
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
				lastValue = getButton();
			}
		}, 50, 50);
	}

	public abstract boolean getButton();

	public boolean getDown() {
		last = getButton();
		if(pressed) {
			pressed = false;
			return true;
		}
		return false;
	}

	public boolean getUp() {
		last = getButton();
		if(released) {
			released = false;
			return true;
		}
		return false;
	}

	@Override
	public int get() {
		last = getButton();
		return getButton() ? 1 : 0;
	}

	@Override
	public boolean hasChanged() {
		boolean returnVal = last != getButton();
		last = getButton();
		return returnVal;
	}
}
