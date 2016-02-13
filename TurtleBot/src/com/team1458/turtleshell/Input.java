package com.team1458.turtleshell;

import com.team1458.turtleshell.logging.TurtleLogger;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Static class to deal with Inputs, all inputs should be done through this.
 * 
 * @author mehnadnerd
 *
 */
public class Input {
	protected static InputDriveConfiguration inputDriveConfig = InputDriveConfiguration.TANK;
	protected static InputThingyConfiguration inputThingy1Config = InputThingyConfiguration.PANEL;
	protected static InputThingyConfiguration inputThingy2Config = InputThingyConfiguration.XBOX;

	protected static Joystick steering1 = new Joystick(0);
	protected static Joystick steering2 = new Joystick(1);
	protected static Joystick thingy1 = new Joystick(2);
	protected static Joystick thingy2 = new Joystick(3);

	public static enum InputDriveConfiguration {
		TANK, ARCADE, WHEEL
	}

	public static enum InputThingyConfiguration {
		PANEL, XBOX
	}

	public static void setDriveConfiguration(InputDriveConfiguration driveConfig) {
		inputDriveConfig = driveConfig;
	}

	public static void setThingy1Configuration(InputThingyConfiguration thingyConfig) {
		inputThingy1Config = thingyConfig;
	}

	public static void setThingy2Configuration(InputThingyConfiguration thingyConfig) {
		inputThingy2Config = thingyConfig;
	}

	/**
	 * Get the power to drive right motors with.
	 */
	public static double getRPower() {
		switch (inputDriveConfig) {
		case TANK:
			if (steering1.getRawButton(1)) {
				return -steering1.getAxis(Joystick.AxisType.kY);
			}
			if (steering2.getRawButton(1)) {
				return steering2.getAxis(Joystick.AxisType.kY);
			}
			return -steering1.getAxis(Joystick.AxisType.kY);
		case ARCADE:
			return (-steering1.getAxis(Joystick.AxisType.kX) - steering1.getAxis(Joystick.AxisType.kY))
					/ (1 + TurtleMaths.normaliseM(
							-steering1.getAxis(Joystick.AxisType.kY) / steering1.getAxis(Joystick.AxisType.kX)));
		case WHEEL:
			return 0;
		default:
			return 0;
		}
	}

	/**
	 * Get the power to drive left motors with.
	 */
	public static double getLPower() {
		switch (inputDriveConfig) {
		case TANK:
			if (steering1.getRawButton(1)) {
				return -steering1.getAxis(Joystick.AxisType.kY);
			}
			if (steering2.getRawButton(1)) {
				return -steering2.getAxis(Joystick.AxisType.kY);
			}
			return -steering2.getAxis(Joystick.AxisType.kY);
		case ARCADE:
			return (steering1.getAxis(Joystick.AxisType.kX) - steering1.getAxis(Joystick.AxisType.kY))
					/ (1 + TurtleMaths.normaliseM(
							-steering1.getAxis(Joystick.AxisType.kY) / steering1.getAxis(Joystick.AxisType.kX)));
		case WHEEL:
			return 0;
		default:
			return 0;
		}
	}

	/**
	 * Get a number from the SmartDashboard
	 * 
	 * @param key
	 *            The name of the SmartDashboard thing
	 * @return The number in SmartDashboard, or zero if it cannot be found
	 */
	public static double getDashboardNumber(String key) {
		return SmartDashboard.getNumber(key, 0);
	}

	/**
	 * Get a boolean from the SmartDashboard
	 * 
	 * @param key
	 *            The name of the SmartDashboard thing
	 * @return The boolean in SmartDashboard, or false if it cannot be found
	 */
	public static boolean getDashboardBoolean(String key) {
		return SmartDashboard.getBoolean(key);
	}

	public static boolean isDebug() {
		switch (inputThingy1Config) {
		case PANEL:
			return thingy1.getRawButton(13);
		default:
			return false;
		}
	}

}
