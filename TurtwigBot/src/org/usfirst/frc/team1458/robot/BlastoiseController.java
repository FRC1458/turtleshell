package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.input.JoystickAxis;
import com.team1458.turtleshell2.input.TurtleJoystickButton;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Interface with custom driver station buttons
 *
 * @author asinghani
 */
public class BlastoiseController {
	private final Joystick joystick;

	public BlastoiseController(int port) {
		joystick = new Joystick(port);
	}

	// TODO Fix numbers

	public JoystickAxis getIntake() {
		return new JoystickAxis(joystick, 0);
	}

	public JoystickAxis getShooterSpeed() {
		return new JoystickAxis(joystick, 1);
	}


	public TurtleJoystickButton getFeeder() {
		return new TurtleJoystickButton(joystick, 0);
	}

	public TurtleJoystickButton getShooterToggle() {
		return new TurtleJoystickButton(joystick, 1);
	}

	public TurtleJoystickButton getClimber() {
		return new TurtleJoystickButton(joystick, 2);
	}

	public TurtleJoystickButton getPanic() {
		return new TurtleJoystickButton(joystick, 3);
	}

	public TurtleJoystickButton getShooterAutoManual() {
		return new TurtleJoystickButton(joystick, 4);
	}
}
