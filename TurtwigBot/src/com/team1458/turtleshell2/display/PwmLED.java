package com.team1458.turtleshell2.display;

import edu.wpi.first.wpilibj.PWM;

/**
 * Represents an LED with 1 PWM output.
 *
 * @author asinghani
 */
public class PwmLED {
	private PWM output;

	/**
	 * Create LED with PWM port
	 */
	public PwmLED(int port) {
		output = new PWM(port);

		set(0);
	}

	public void set(int value) {
		output.setRaw(value);
	}

	public int get() {
		return output.getRaw();
	}
}
