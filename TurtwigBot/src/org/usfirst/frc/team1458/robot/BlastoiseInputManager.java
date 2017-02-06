package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.input.*;
import com.team1458.turtleshell2.input.fake.FakeRumbleable;

/**
 * Manages all input/control for BlastoiseRobot
 *
 * @author asinghani
 */
public class BlastoiseInputManager implements Rumbleable {

	// Drive Joysticks
	private AnalogInput leftJoystick;
	private AnalogInput rightJoystick;

	// Extra Drive Functions
	private ButtonInput straightButton;
	private ButtonInput turnButton;

	// Turn for degrees buttons
	private ButtonInput right90button;
	private ButtonInput left90button;

	// Slow button for finer control
	private ButtonInput slowButton;

	// POV switch
	private DigitalInput pov;

	// Rumble
	private Rumbleable rumbleController;

	/**
	 * Instantiate BlastoiseInputManager with 2 flight sticks
	 */
	public BlastoiseInputManager(FlightStick leftStick, FlightStick rightStick) {
		// Drive Joysticks
		this.leftJoystick = leftStick.getAxis(FlightStick.FlightAxis.PITCH);
		this.rightJoystick = rightStick.getAxis(FlightStick.FlightAxis.PITCH);

		// Extra Drive Functions
		this.straightButton = rightStick.getButton(FlightStick.FlightButton.TRIGGER);
		this.turnButton = leftStick.getButton(FlightStick.FlightButton.TRIGGER);

		// Turn for degrees buttons
		this.right90button = rightStick.getButton(FlightStick.FlightButton.THREE);
		this.left90button = leftStick.getButton(FlightStick.FlightButton.FOUR);

		// Slow Button for finer control
		this.slowButton = new MultiButtonInput(MultiButtonInput.Operator.OR,
				rightStick.getButton(FlightStick.FlightButton.TWO),
				leftStick.getButton(FlightStick.FlightButton.TWO));

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
		this.straightButton = controller.getButton(XboxController.XboxButton.RBUMP);
		this.turnButton = controller.getButton(XboxController.XboxButton.LBUMP);

		// Turn for degrees buttons
		this.right90button = controller.getButton(XboxController.XboxButton.B);
		this.left90button = controller.getButton(XboxController.XboxButton.X);

		// Slow Button for finer control
		this.slowButton = controller.getButton(XboxController.XboxButton.A);

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

	public AnalogInput getLeftJoystick() {
		return leftJoystick;
	}

	public AnalogInput getRightJoystick() {
		return rightJoystick;
	}

	public double getLeft() {
		return leftJoystick.get();
	}

	public double getRight() {
		return rightJoystick.get();
	}

	public ButtonInput getStraightButton() {
		return straightButton;
	}

	public ButtonInput getTurnButton() {
		return turnButton;
	}

	public ButtonInput getRight90button() {
		return right90button;
	}

	public ButtonInput getLeft90button() {
		return left90button;
	}

	public ButtonInput getSlowButton() {
		return slowButton;
	}

	public DigitalInput getPov() {
		return pov;
	}
}
