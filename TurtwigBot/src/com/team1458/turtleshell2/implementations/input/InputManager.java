package com.team1458.turtleshell2.implementations.input;

import com.team1458.turtleshell2.interfaces.input.InputMapping;

/**
 * Class for managing user inputs. Currently supports dual flight-sticks OR Xbox controller.
 *
 * @author asinghani
 */
public class InputManager {
	private TurtleFlightStick leftStick;
	private TurtleFlightStick rightStick;

	private TurtleXboxController xboxController;

	private InputMapping mapping;

	public InputManager(TurtleXboxController xboxController, InputMapping mapping) {
		this.xboxController = xboxController;
		this.mapping = mapping;
	}

	public InputManager(TurtleFlightStick leftStick, TurtleFlightStick rightStick, InputMapping mapping) {
		this.leftStick = leftStick;
		this.rightStick = rightStick;
		this.mapping = mapping;
	}


	public TurtleJoystickAxis getAxis(String s) {
		InputObject object = mapping.getMapping().get(s);
		switch(object.getInputDevice()) {
			case XBOX_CONTROLLER:
				return xboxController.getAxis(object.getXboxAxis());
			case LEFT_FLIGHT_STICK:
				return leftStick.getAxis(object.getFlightAxis());
			case RIGHT_FLIGHT_STICK:
				return rightStick.getAxis(object.getFlightAxis());
		}
		return null;
	}

	public double getAxisValue(String s) {
		InputObject object = mapping.getMapping().get(s);
		switch(object.getInputDevice()) {
			case XBOX_CONTROLLER:
				return xboxController.getAxis(object.getXboxAxis()).get();
			case LEFT_FLIGHT_STICK:
				return leftStick.getAxis(object.getFlightAxis()).get();
			case RIGHT_FLIGHT_STICK:
				return rightStick.getAxis(object.getFlightAxis()).get();
		}
		return 0;
	}



	public TurtleJoystickButton getButton(String s) {
		InputObject object = mapping.getMapping().get(s);
		switch(object.getInputDevice()) {
			case XBOX_CONTROLLER:
				return xboxController.getButton(object.getXboxButton());
			case LEFT_FLIGHT_STICK:
				return leftStick.getButton(object.getFlightButton());
			case RIGHT_FLIGHT_STICK:
				return rightStick.getButton(object.getFlightButton());
		}
		return null;
	}

	public boolean getButtonValue(String s) {
		InputObject object = mapping.getMapping().get(s);
		switch(object.getInputDevice()) {
			case XBOX_CONTROLLER:
				return xboxController.getButton(object.getXboxButton()).get();
			case LEFT_FLIGHT_STICK:
				return leftStick.getButton(object.getFlightButton()).get();
			case RIGHT_FLIGHT_STICK:
				return rightStick.getButton(object.getFlightButton()).get();
		}
		return false;
	}

	public boolean getButtonUp(String s) {
		InputObject object = mapping.getMapping().get(s);
		switch(object.getInputDevice()) {
			case XBOX_CONTROLLER:
				return xboxController.getButton(object.getXboxButton()).getUp();
			case LEFT_FLIGHT_STICK:
				return leftStick.getButton(object.getFlightButton()).getUp();
			case RIGHT_FLIGHT_STICK:
				return rightStick.getButton(object.getFlightButton()).getUp();
		}
		return false;
	}

	public boolean getButtonDown(String s) {
		InputObject object = mapping.getMapping().get(s);
		switch(object.getInputDevice()) {
			case XBOX_CONTROLLER:
				return xboxController.getButton(object.getXboxButton()).getDown();
			case LEFT_FLIGHT_STICK:
				return leftStick.getButton(object.getFlightButton()).getDown();
			case RIGHT_FLIGHT_STICK:
				return rightStick.getButton(object.getFlightButton()).getDown();
		}
		return false;
	}



	public TurtleJoystickPOVSwitch getPOV(String s) {
		InputObject object = mapping.getMapping().get(s);
		switch(object.getInputDevice()) {
			case XBOX_CONTROLLER:
				return xboxController.getDPad();
			case LEFT_FLIGHT_STICK:
				return leftStick.getPOVSwitch();
			case RIGHT_FLIGHT_STICK:
				return rightStick.getPOVSwitch();
		}
		return null;
	}

	public TurtleJoystickPOVSwitch.POVValue getPOVvalue(String s) {
		InputObject object = mapping.getMapping().get(s);
		switch(object.getInputDevice()) {
			case XBOX_CONTROLLER:
				return xboxController.getDPad().get();
			case LEFT_FLIGHT_STICK:
				return leftStick.getPOVSwitch().get();
			case RIGHT_FLIGHT_STICK:
				return rightStick.getPOVSwitch().get();
		}
		return TurtleJoystickPOVSwitch.POVValue.CENTER;
	}



	public enum InputType {
		BUTTON, ANALOG, POV
	}
	public enum InputDevice {
		XBOX_CONTROLLER, RIGHT_FLIGHT_STICK, LEFT_FLIGHT_STICK
	}
}

