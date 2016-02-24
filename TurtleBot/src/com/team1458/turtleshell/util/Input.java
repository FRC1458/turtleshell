package com.team1458.turtleshell.util;

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
    protected static double xboxDeadband = 0.15;
    protected static double joystickDeadband = 0.1;

    public static void setXboxDeadband(double xboxDeadband) {
	Input.xboxDeadband = xboxDeadband;
    }

    public static void setJoystickDeadband(double joystickDeadband) {
	Input.joystickDeadband = joystickDeadband;
    }

    public static enum InputDriveConfiguration {
	TANK, ARCADE, WHEEL
    }

    public static enum InputThingyConfiguration {
	PANEL, XBOX
    }

    public static enum XboxButton {
	A(1), B(2), X(3), Y(4), LBUMP(5), RBUMP(6), SELECT(7), START(8), LSTICK(9), RSTICK(10);
	public final int val;

	XboxButton(int i) {
	    val = i;
	}
    }

    public static enum POV {
	NONE(-1), N(0), NW(1), W(2), SW(3), S(4), SE(5), E(6), NE(7);
	public final int val;

	POV(int i) {
	    val = i;
	}

	public static POV POVFromAngle(int angle) {
	    switch (angle) {
	    case 0:
		return POV.N;
	    case 45:
		return POV.NE;
	    case 90:
		return POV.E;
	    case 135:
		return POV.SE;
	    case 180:
		return POV.S;
	    case 225:
		return POV.SW;
	    case 270:
		return POV.W;
	    case 315:
		return POV.NW;
	    case 360:
		return POV.N;
	    default:
		return POV.NONE;
	    }
	}
    }

    public static enum XboxAxis {
	LX(0), LY(1), LT(2), RT(3), RX(4), RY(5), ;
	public final int val;

	XboxAxis(int i) {
	    val = i;
	}
    }

    public static POV getXBoxPOV() {
	if (inputThingy1Config == InputThingyConfiguration.XBOX) {
	    return POV.POVFromAngle(thingy1.getPOV());
	}
	if (inputThingy2Config == InputThingyConfiguration.XBOX) {
	    return POV.POVFromAngle(thingy2.getPOV());
	}
	TurtleLogger.severe("Requesting an Xbox button, but no Xbox controller is connected or configured");
	return POV.NONE;
    }

    public static void setDriveConfiguration(InputDriveConfiguration driveConfig) {
	steering1.getPOV(0);
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
		    / (1 + TurtleMaths.normaliseM(-steering1.getAxis(Joystick.AxisType.kY) / steering1.getAxis(Joystick.AxisType.kX)));
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
		    / (1 + TurtleMaths.normaliseM(-steering1.getAxis(Joystick.AxisType.kY) / steering1.getAxis(Joystick.AxisType.kX)));
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

    public static boolean shouldInterrupt() {
	boolean toReturn = false;
	for (int i = 0; i < 15; i++) {
	    toReturn |= steering1.getRawButton(i);
	}
	for (int i = 0; i < 15; i++) {
	    toReturn |= steering2.getRawButton(i);
	}
	for (int i = 0; i < 15; i++) {
	    toReturn |= thingy1.getRawButton(i);
	}
	for (int i = 0; i < 15; i++) {
	    toReturn |= thingy2.getRawButton(i);
	}
	if (Math.abs(steering1.getAxis(Joystick.AxisType.kY)) > .1) {
	    toReturn = true;
	}
	if (Math.abs(steering2.getAxis(Joystick.AxisType.kY)) > .1) {
	    toReturn = true;
	}
	return toReturn;
    }

    public static boolean getXboxButton(XboxButton b) {
	if (inputThingy1Config == InputThingyConfiguration.XBOX) {
	    return thingy1.getRawButton(b.val);
	}
	if (inputThingy2Config == InputThingyConfiguration.XBOX) {
	    return thingy2.getRawButton(b.val);
	}
	TurtleLogger.severe("Requesting an Xbox button, but no Xbox controller is connected or configured");
	return false;
    }

    public static double getXboxAxis(XboxAxis a) {
	if (inputThingy1Config == InputThingyConfiguration.XBOX) {
	    if (a == XboxAxis.LY || a == XboxAxis.RY) {
		return TurtleMaths.deadband(-thingy1.getRawAxis(a.val), xboxDeadband);
	    }
	    return TurtleMaths.deadband(thingy1.getRawAxis(a.val), xboxDeadband);
	}
	if (inputThingy2Config == InputThingyConfiguration.XBOX) {
	    if (a == XboxAxis.LY || a == XboxAxis.RY) {
		return TurtleMaths.deadband(-thingy2.getRawAxis(a.val), xboxDeadband);
	    }
	    return TurtleMaths.deadband(thingy2.getRawAxis(a.val), xboxDeadband);
	}
	TurtleLogger.severe("Requesting an Xbox axis, but no Xbox controller is connected or configured");
	return 0;
    }

}
