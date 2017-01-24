package com.team1458.turtleshell2.implementations.input;

/**
 * Class for managing user inputs. Currently supports dual flight-sticks OR Xbox controller.
 *
 * @author asinghani
 */
public class InputManager {
	public enum InputType {
		BUTTON, ANALOG, POV
	}

	private TurtleFlightStick leftStick;
	private TurtleFlightStick rightStick;

	private TurtleXboxController xboxController;

	public InputManager(TurtleXboxController xboxController) {
		this.xboxController = xboxController;
	}

	public InputManager(TurtleFlightStick leftStick, TurtleFlightStick rightStick) {
		this.leftStick = leftStick;
		this.rightStick = rightStick;
	}
}
