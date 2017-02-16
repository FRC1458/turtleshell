package org.usfirst.frc.team1458.robot;

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


}
