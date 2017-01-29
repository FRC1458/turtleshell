package com.team1458.turtleshell2.implementations.display;

import edu.wpi.first.wpilibj.DigitalOutput;

/**
 * Represents an LED with 1 digital output.
 *
 * @author asinghani
 */
public class LED {
	private DigitalOutput output;

	/**
	 * Create LED with digital port
	 */
	public LED(int port) {
		output = new DigitalOutput(port);

		set(false);
	}

	public void set(boolean value) {
		output.set(value);
	}

	public boolean get() {
		return output.get();
	}
}
