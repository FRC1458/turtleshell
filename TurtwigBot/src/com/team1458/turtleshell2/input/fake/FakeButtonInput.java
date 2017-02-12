package com.team1458.turtleshell2.input.fake;

import com.team1458.turtleshell2.input.ButtonInput;

/**
 * Fake button
 *
 * @author asinghani
 */
public class FakeButtonInput implements ButtonInput {

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
	
	@Override
	public boolean hasChanged() {
		return false;
	}
}
