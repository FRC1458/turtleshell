package com.team1458.turtleshell2.implementations.input;

/**
 * Utility class for representing one axis or button of one joystick. Changing this file will break many things.
 *
 * @author asinghani
 */
public class InputObject {
	InputManager.InputType inputType;
	InputManager.InputDevice inputDevice;

	TurtleXboxController.XboxAxis xboxAxis;
	TurtleXboxController.XboxButton xboxButton;

	TurtleFlightStick.FlightAxis flightAxis;
	TurtleFlightStick.FlightButton flightButton;


	public InputObject(TurtleXboxController.XboxAxis xboxAxis) {
		this.inputType = InputManager.InputType.ANALOG;
		this.inputDevice = InputManager.InputDevice.XBOX_CONTROLLER;
		this.xboxAxis = xboxAxis;
	}

	public InputObject(TurtleXboxController.XboxButton xboxButton) {
		this.inputType = InputManager.InputType.BUTTON;
		this.inputDevice = InputManager.InputDevice.XBOX_CONTROLLER;
		this.xboxButton = xboxButton;
	}

	public InputObject(TurtleFlightStick.FlightAxis flightAxis, boolean rightStick) {
		this.inputType = InputManager.InputType.ANALOG;
		this.inputDevice = rightStick ? InputManager.InputDevice.RIGHT_FLIGHT_STICK : InputManager.InputDevice.LEFT_FLIGHT_STICK;
		this.flightAxis = flightAxis;
	}

	public InputObject(TurtleFlightStick.FlightButton flightButton, boolean rightStick) {
		this.inputType = InputManager.InputType.BUTTON;
		this.inputDevice = rightStick ? InputManager.InputDevice.RIGHT_FLIGHT_STICK : InputManager.InputDevice.LEFT_FLIGHT_STICK;
		this.flightButton = flightButton;
	}

	/**
	 * Only for POV switch
	 */
	public InputObject(InputManager.InputDevice inputDevice) {
		this.inputType = InputManager.InputType.POV;
		this.inputDevice = inputDevice;
	}

	public InputManager.InputType getInputType() {
		return inputType;
	}

	public InputManager.InputDevice getInputDevice() {
		return inputDevice;
	}

	public TurtleXboxController.XboxAxis getXboxAxis() {
		return xboxAxis;
	}

	public TurtleXboxController.XboxButton getXboxButton() {
		return xboxButton;
	}

	public TurtleFlightStick.FlightAxis getFlightAxis() {
		return flightAxis;
	}

	public TurtleFlightStick.FlightButton getFlightButton() {
		return flightButton;
	}
}
