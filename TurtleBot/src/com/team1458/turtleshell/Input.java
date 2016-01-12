package com.team1458.turtleshell;

import edu.wpi.first.wpilibj.Joystick;

public class Input {
	
	public static void setRJoystickPort(int port) {
		rJoystick= new Joystick(port);
	}
	public static void setLJoystickPort(int port) {
		lJoystick= new Joystick(port);
	}
	public static void setButtonPanelPort(int port) {
		buttonPanel= new Joystick(port);
	}
	
	private static Joystick rJoystick = new Joystick(0);
	private static Joystick lJoystick = new Joystick(1);
	private static Joystick buttonPanel = new Joystick(2);
	
	/**
	 * Get the power for the right joystick.
	 * @return The right joystick power, measured on y axis. n.b. this is negative from the raw axis.
	 */
	public static double getRPower() {
		return -rJoystick.getAxis(Joystick.AxisType.kY);
	}
	
	/**
	 * Get the power for the left joystick.
	 * @return The left joystick power, measured on y axis. n.b. this is negative from the raw axis.
	 */
	public static double getLPower() {
		return -lJoystick.getAxis(Joystick.AxisType.kY);
	}
	
	/**
	 * Gets whether a button on the buttonPanel is pressed.
	 * @param whichButton Which button you want, note that this starts at 1.
	 * @return Whether or not the button is pressed.
	 */
	public static boolean isPanelButtonPressed(int whichButton) {
		return buttonPanel.getRawButton(whichButton);
	}
	
	public static double getRTheta() {
		return 180*Math.atan2(-rJoystick.getAxis(Joystick.AxisType.kY), rJoystick.getAxis(Joystick.AxisType.kX))/Math.PI;
	}
}
