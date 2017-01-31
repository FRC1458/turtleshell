package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.InputObject;
import com.team1458.turtleshell2.input.TurtleFlightStick;
import com.team1458.turtleshell2.input.TurtleXboxController;
import com.team1458.turtleshell2.interfaces.input.InputMapping;

import java.util.Map;

/**
 * @author asinghani
 */
public class BlastoiseInputMapping implements InputMapping {

	Map<String, InputObject> mapping;

	public BlastoiseInputMapping() {
		mapping.put("LEFT_JOYSTICK",
				new InputObject(TurtleXboxController.XboxAxis.LY, TurtleFlightStick.FlightAxis.PITCH, false));
		mapping.put("RIGHT_JOYSTICK",
				new InputObject(TurtleXboxController.XboxAxis.RY, TurtleFlightStick.FlightAxis.PITCH, true));

		mapping.put("TURN_BUTTON",
				new InputObject(TurtleXboxController.XboxButton.LBUMP, TurtleFlightStick.FlightButton.TRIGGER, false));
		mapping.put("STRAIGHT_DRIVE_BUTTON",
				new InputObject(TurtleXboxController.XboxButton.RBUMP, TurtleFlightStick.FlightButton.TRIGGER, true));

		mapping.put("POV", new InputObject());
	}

	@Override
	public Map<String, InputObject> getMapping() {
		return mapping;
	}
}
