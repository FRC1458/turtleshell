package com.team1458.turtleshell2.input;

import edu.wpi.first.wpilibj.Joystick;

import java.util.Timer;
import java.util.TimerTask;

public class XboxController implements Rumbleable {
	private final Joystick j;

	public XboxController(int usbport) {
		j = new Joystick(usbport);
	}

	public static enum XboxButton {
		A(1), B(2), X(3), Y(4), LBUMP(5), RBUMP(6), SELECT(7), START(8), LSTICK(9), RSTICK(10);
		public final int val;

		XboxButton(int i) {
			val = i;
		}
	}

	public static enum XboxAxis {
		LX(0), LY(1), LT(2), RT(3), RX(4), RY(5);
		public final int val;

		XboxAxis(int i) {
			val = i;
		}
	}

	public JoystickAxis getAxis(XboxAxis a) {
		if (a == XboxAxis.LY || a == XboxAxis.RY) {
			return new JoystickAxis(j, a.val, true);
		}
		return new JoystickAxis(j, a.val);
	}

	public TurtleJoystickButton getButton(XboxButton b) {
		return new TurtleJoystickButton(j, b.val);
	}

	public TurtleJoystickPOVSwitch getDPad() {
		return new TurtleJoystickPOVSwitch(j, 0);
	}

	public void rumbleRight(float strength, long millis) {
		j.setRumble(Joystick.RumbleType.kRightRumble, strength);

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				j.setRumble(Joystick.RumbleType.kRightRumble, 0);
			}
		}, millis);
	}

	public void rumbleLeft(float strength, long millis) {
		j.setRumble(Joystick.RumbleType.kLeftRumble, strength);

		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				j.setRumble(Joystick.RumbleType.kLeftRumble, 0);
			}
		}, millis);
	}

	public void rumble(float strength, long millis) {
		rumbleRight(strength, millis);
		rumbleLeft(strength, millis);
	}
}
