package com.team1458.turtleshell2.input;

/**
 * Interface for button inputs, that can be pressed or not.
 * @author mehnadnerd & asinghani
 *
 */
public interface ButtonInput extends DigitalInput {
	default boolean getButton() {
		return (this.get() == 1);
	}

	/**
	 * Detect a rising edge (Button being pressed down)
	 * Will not trigger more than once without being released in between.
	 * @return
	 */
	boolean getDown();

	/**
	 * Detect a falling edge (Button being released).
	 * Will not trigger more than once without being pressed in between.
	 * @return
	 */
	boolean getUp();

	/**
	 * Has the button changed since last time it was used?
	 */
	boolean hasChanged();
}
