package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.input.JoystickAxis;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

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


	public JoystickButton getFeeder() {
		return new JoystickButton(joystick, 0);
	}

	public JoystickButton getShooterToggle() {
		return new JoystickButton(joystick, 1);
	}

	public JoystickButton getClimber() {
		return new JoystickButton(joystick, 2);
	}

	public JoystickButton getPanic() {
		return new JoystickButton(joystick, 3);
	}
}
