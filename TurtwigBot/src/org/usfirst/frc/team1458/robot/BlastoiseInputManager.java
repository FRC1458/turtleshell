package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.input.*;
import com.team1458.turtleshell2.input.fake.FakeRumbleable;

/**
 * Manages all input/control for Robot
 *
 * @author asinghani
 */
public class BlastoiseInputManager implements Rumbleable {

	// Drive Joysticks
	AnalogInput leftJoystick;
	AnalogInput rightJoystick;

	// Extra Drive Functions
	ButtonInput straightButton;
	ButtonInput turnRightButton;
	ButtonInput turnLeftButton;

	// Turn for degrees buttons
	ButtonInput right90button;
	ButtonInput left90button;

	// Slow button for finer control
	ButtonInput slowButton;

	// Stuff related to vision
	ButtonInput alignShooterButton;

	// POV switch
	DigitalInput pov;

	// Rumble
	Rumbleable rumbleController;

	/**
	 * Instantiate BlastoiseInputManager with 2 flight sticks
	 */
	public BlastoiseInputManager(FlightStick leftStick, FlightStick rightStick) {
		// Drive Joysticks
		this.leftJoystick = leftStick.getAxis(FlightStick.FlightAxis.PITCH);
		this.rightJoystick = rightStick.getAxis(FlightStick.FlightAxis.PITCH);

		// Extra Drive Functions
		this.turnLeftButton = leftStick.getButton(FlightStick.FlightButton.TRIGGER);
		this.turnRightButton = rightStick.getButton(FlightStick.FlightButton.TRIGGER);
		this.straightButton = new MultiButtonInput(MultiButtonInput.Operator.AND, this.turnLeftButton, this.turnRightButton);

		// Turn for degrees buttons
		this.right90button = rightStick.getButton(FlightStick.FlightButton.THREE);
		this.left90button = leftStick.getButton(FlightStick.FlightButton.FOUR);

		// Slow Button for finer control
		this.slowButton = new MultiButtonInput(MultiButtonInput.Operator.OR,
				rightStick.getButton(FlightStick.FlightButton.TWO),
				leftStick.getButton(FlightStick.FlightButton.TWO));

		this.alignShooterButton = rightStick.getButton(FlightStick.FlightButton.FIVE);

		// POV switch
		this.pov = rightStick.getPOVSwitch();

		// Rumble
		this.rumbleController = new FakeRumbleable();
	}

	/**
	 * Instantiate BlastoiseInputManager with xbox controller
	 */
	public BlastoiseInputManager(XboxController controller) {
		// Drive Joysticks
		this.leftJoystick = controller.getAxis(XboxController.XboxAxis.LY);
		this.rightJoystick = controller.getAxis(XboxController.XboxAxis.RY);

		// Extra Drive Functions
		this.turnLeftButton = controller.getButton(XboxController.XboxButton.LBUMP);
		this.turnRightButton = controller.getButton(XboxController.XboxButton.RBUMP);
		this.straightButton = new MultiButtonInput(MultiButtonInput.Operator.AND, this.turnLeftButton, this.turnRightButton);

		// Turn for degrees buttons
		this.right90button = controller.getButton(XboxController.XboxButton.B);
		this.left90button = controller.getButton(XboxController.XboxButton.X);

		// Slow Button for finer control
		this.slowButton = controller.getButton(XboxController.XboxButton.A);

		this.alignShooterButton = controller.getButton(XboxController.XboxButton.Y);

		// POV switch
		this.pov = controller.getDPad();

		// Rumble
		this.rumbleController = controller;
	}

	@Override
	public void rumbleRight(float strength, long millis) {
		rumbleController.rumbleRight(strength, millis);
	}

	@Override
	public void rumbleLeft(float strength, long millis) {
		rumbleController.rumbleLeft(strength, millis);
	}

	@Override
	public void rumble(float strength, long millis) {
		rumbleController.rumble(strength, millis);
	}

	public double getLeft() {
		return leftJoystick.get();
	}

	public double getRight() {
		return rightJoystick.get();
	}
}
