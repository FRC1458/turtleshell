package com.team1458.turtleshell2.implementations.input;

import com.team1458.turtleshell2.interfaces.input.TurtleDigitalInput;

import edu.wpi.first.wpilibj.Joystick;

public class TurtleJoystickPOVSwitch implements TurtleDigitalInput {
	public enum POVValue {
		CENTER(-1), N(0), NW(1), W(2), SW(3), S(4), SE(5), E(6), NE(7);
		public final int val;

		POVValue(int i) {
			val = i;
		}

		public static POVValue POVFromValue(int val) {
			for (POVValue p : POVValue.values()) {
				if (p.val == val) {
					return p;
				}
			}
			return POVValue.CENTER;
		}

		public static POVValue POVFromAngle(int angle) {
			switch (angle) {
			case 0:
				return POVValue.N;
			case 45:
				return POVValue.NE;
			case 90:
				return POVValue.E;
			case 135:
				return POVValue.SE;
			case 180:
				return POVValue.S;
			case 225:
				return POVValue.SW;
			case 270:
				return POVValue.W;
			case 315:
				return POVValue.NW;
			case 360:
				return POVValue.N;
			default:
				return POVValue.CENTER;
			}
		}
	}

	private final Joystick masterJoystick;
	private final int povNum;

	public TurtleJoystickPOVSwitch(Joystick j, int buttonNum) {
		this.masterJoystick = j;
		this.povNum = buttonNum;
	}

	@Override
	public int get() {
		return POVValue.POVFromAngle(masterJoystick.getPOV(povNum)).val;
	}
}
