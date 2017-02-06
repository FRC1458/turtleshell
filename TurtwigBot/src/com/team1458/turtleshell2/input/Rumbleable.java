package com.team1458.turtleshell2.input;

/**
 * @author asinghani
 */
public interface Rumbleable {
	void rumbleRight(float strength, long millis);

	void rumbleLeft(float strength, long millis);

	void rumble(float strength, long millis);
}
