package com.team1458.turtleshell2.implementations.input;

import com.team1458.turtleshell2.interfaces.input.InputMapping;

import static com.team1458.turtleshell2.implementations.input.InputManager.InputDevice.FLIGHT_STICK;
import static com.team1458.turtleshell2.implementations.input.InputManager.InputDevice.XBOX_CONTROLLER;

/**
 * Class for managing user inputs. Currently supports dual flight-sticks OR Xbox controller.
 *
 * @author asinghani
 */
public class InputManager {
	private TurtleFlightStick leftStick;
	private TurtleFlightStick rightStick;

	private TurtleXboxController xboxController;

	private InputDevice inputDevice;

	private InputMapping mapping;

	public InputManager(TurtleXboxController xboxController, InputMapping mapping) {
		this.xboxController = xboxController;
		this.mapping = mapping;
		this.inputDevice = XBOX_CONTROLLER;
	}

	public InputManager(TurtleFlightStick leftStick, TurtleFlightStick rightStick, InputMapping mapping) {
		this.leftStick = leftStick;
		this.rightStick = rightStick;
		this.mapping = mapping;
		this.inputDevice = FLIGHT_STICK;
	}


	public TurtleJoystickAxis getAxis(String s) {
		InputObject object = mapping.getMapping().get(s);
		switch(inputDevice) {
			case XBOX_CONTROLLER:
				return xboxController.getAxis(object.getXboxAxis());
			case FLIGHT_STICK:
				return object.isRightFlightStick() ? rightStick.getAxis(object.getFlightAxis()) : leftStick.getAxis(object.getFlightAxis());
		}
		return null;
	}

	public double getAxisValue(String s) {
		return getAxis(s).get();
	}



	public TurtleJoystickButton getButton(String s) {
		InputObject object = mapping.getMapping().get(s);
		switch(inputDevice) {
			case XBOX_CONTROLLER:
				return xboxController.getButton(object.getXboxButton());
			case FLIGHT_STICK:
				return object.isRightFlightStick() ? rightStick.getButton(object.getFlightButton()) : leftStick.getButton(object.getFlightButton());
		}
		return null;
	}

	public boolean getButtonValue(String s) {
		return getButton(s).get();
	}

	public boolean getButtonUp(String s) {
		return getButton(s).getUp();
	}

	public boolean getButtonDown(String s) {
		return getButton(s).getDown();
	}



	public TurtleJoystickPOVSwitch getPOV(String s) {
		InputObject object = mapping.getMapping().get(s);
		switch(inputDevice) {
			case XBOX_CONTROLLER:
				return xboxController.getDPad();
			case FLIGHT_STICK:
				return object.isRightFlightStick() ? rightStick.getPOVSwitch() : leftStick.getPOVSwitch();
		}
		return null;
	}

	public TurtleJoystickPOVSwitch.POVValue getPOVvalue(String s) {
		return getPOV(s).get();
	}

	public void rumbleRight(float strength, long millis) {
		if(xboxController != null) xboxController.rumbleRight(strength, millis);
	}

	public void rumbleLeft(float strength, long millis) {
		if(xboxController != null) xboxController.rumbleLeft(strength, millis);
	}

	public void rumble(float strength, long millis) {
		if(xboxController != null) xboxController.rumble(strength, millis);
	}

	public enum InputType {
		BUTTON, ANALOG, POV
	}
	public enum InputDevice {
		XBOX_CONTROLLER, FLIGHT_STICK
	}
}

