package com.team1458.turtleshell2.implementations.input;

import com.team1458.turtleshell2.interfaces.input.TurtleDigitalInput;
import edu.wpi.first.wpilibj.Joystick;

public class TurtleJoystickPOVSwitch implements TurtleDigitalInput {
	public enum POVValue {
		CENTER(-1), N(0), NW(315), W(270), SW(235), S(180), SE(135), E(90), NE(45);
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

	public POVValue getPOV() {
		return POVValue.POVFromAngle(masterJoystick.getPOV(povNum));
	}

	@Override
	public int get() {
		return getPOV().val;
	}
}
