package com.team1458.turtleshell2.implementations.input;

/**
 * Utility class for representing one axis or button of one joystick. Changing this file will break many things.
 *
 * @author asinghani
 */
public class InputObject {
	InputManager.InputType inputType;
	//InputManager.InputDevice inputDevice;
	boolean rightStick;

	com.team1458.turtleshell2.input.XboxAxis xboxAxis;
	com.team1458.turtleshell2.input.XboxButton xboxButton;

	com.team1458.turtleshell2.input.FlightAxis flightAxis;
	com.team1458.turtleshell2.input.FlightButton flightButton;


	public InputObject(TurtleXboxController.XboxAxis xboxAxis, TurtleFlightStick.FlightAxis flightAxis, boolean rightStick) {
		this.inputType = InputManager.InputType.ANALOG;
		this.xboxAxis = xboxAxis;

		this.flightAxis = flightAxis;
		this.rightStick = rightStick;
	}

	public InputObject(TurtleXboxController.XboxButton xboxButton, TurtleFlightStick.FlightButton flightButton, boolean rightStick) {
		this.inputType = InputManager.InputType.BUTTON;
		this.xboxButton = xboxButton;

		this.flightButton = flightButton;
		this.rightStick = rightStick;
	}

	/**
	 * Only for POV switch
	 */
	public InputObject() {
		this.inputType = InputManager.InputType.POV;
	}

	public InputManager.InputType getInputType() {
		return inputType;
	}

	/*public InputManager.InputDevice getInputDevice() {
		return inputDevice;
	}*/

	public boolean isRightFlightStick() {
		return rightStick;
	}

	public com.team1458.turtleshell2.input.XboxAxis getXboxAxis() {
		return xboxAxis;
	}

	public com.team1458.turtleshell2.input.XboxButton getXboxButton() {
		return xboxButton;
	}

	public com.team1458.turtleshell2.input.FlightAxis getFlightAxis() {
		return flightAxis;
	}

	public com.team1458.turtleshell2.input.FlightButton getFlightButton() {
		return flightButton;
	}
}
