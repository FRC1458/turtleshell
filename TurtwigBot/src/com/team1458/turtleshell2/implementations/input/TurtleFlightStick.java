package com.team1458.turtleshell2.implementations.input;

import edu.wpi.first.wpilibj.Joystick;

public class TurtleFlightStick {
	private Joystick j;

	public TurtleFlightStick(int usbport) {
		j = new Joystick(usbport);
	}
	
	
	/**
	 * Enum holding mappings from axis names to numbers
	 * @author mehnadnerd
	 */
	public static enum FlightAxis {
		ROLL(0), PITCH(1), THROTTLE(2), YAW(3), FOUR(4), FIVE(5);
		public final int val;

		FlightAxis(int i) {
			val = i;
		}
	}

	public static enum FlightButton {
		TRIGGER(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), ELEVEN(
				11), TWELVE(12);
		public final int val;

		FlightButton(int i) {
			val = i;
		}
	}

	public TurtleJoystickAxis getAxis(FlightAxis a) {
		if (a == FlightAxis.PITCH) {
			return new TurtleJoystickAxis(j, a.val, true);
		}
		return new TurtleJoystickAxis(j, a.val, false);
	}

	public TurtleJoystickButton getButton(FlightButton b) {
		return new TurtleJoystickButton(j, b.val);
	}
}
