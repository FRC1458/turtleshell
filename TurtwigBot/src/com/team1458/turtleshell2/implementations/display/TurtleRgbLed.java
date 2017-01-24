package com.team1458.turtleshell2.implementations.display;

import com.team1458.turtleshell2.util.TurtleMaths;
import edu.wpi.first.wpilibj.PWM;

/**
 * Represents an RGB LED with 3 PWM outputs. Works great if motor controllers are CAN.
 *
 * @author asinghani
 */
public class TurtleRgbLed {
	private PWM red;
	private PWM green;
	private PWM blue;

	/**
	 * Create RGB LED with 3 PWM ports
	 *
	 * @param redPort PWM port for Red LED
	 * @param greenPort PWM port for Green LED
	 * @param bluePort PWM port for Blue LED
	 */
	public TurtleRgbLed(int redPort, int greenPort, int bluePort) {
		red = new PWM(redPort);
		green = new PWM(greenPort);
		blue = new PWM(bluePort);

		set(0, 0, 0);
	}

	/**
	 * Set value of all 3 channels
	 * @param red
	 * @param green
	 * @param blue
	 */
	public void set(int red, int green, int blue) {
		setRed(red);
		setGreen(green);
		setBlue(blue);
	}

	/**
	 * Set value of red channel. Value must be 0-255
	 * @param value
	 */
	public void setRed(int value) {
		red.setRaw((int) TurtleMaths.fitRange(value, 0, 255));
	}

	/**
	 * Set value of green channel. Value must be 0-255
	 * @param value
	 */
	public void setGreen(int value) {
		green.setRaw((int) TurtleMaths.fitRange(value, 0, 255));
	}

	/**
	 * Set value of blue channel. Value must be 0-255
	 * @param value
	 */
	public void setBlue(int value) {
		blue.setRaw((int) TurtleMaths.fitRange(value, 0, 255));
	}

	/**
	 * Get value of red channel
	 */
	public int getRed() {
		return red.getRaw();
	}

	/**
	 * Get value of green channel
	 */
	public int getGreen() {
		return green.getRaw();
	}

	/**
	 * Get value of blue channel
	 */
	public int getBlue() {
		return blue.getRaw();
	}
}
