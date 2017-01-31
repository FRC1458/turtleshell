package com.team1458.turtleshell2.input.fake;

import java.util.Timer;
import java.util.TimerTask;

import com.team1458.turtleshell2.input.TurtleButtonInput;

/**
 * Fake button
 *
 * @author asinghani
 */
public class TurtleFakeButtonInput implements TurtleButtonInput {

	public boolean getButton() {
		return false;
	}

	public boolean getDown() {
		return false;
	}

	public boolean getUp() {
		return false;
	}

	@Override
	public int get() {
		return 0;
	}
}
