package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.util.TurtlePIDConstants;

public class Constants {
	public static final int RJOYSTICKPORT = 0;
	public static final int LJOYSTICKPORT = 1;
	public static final int BUTTONPANELPORT = 2;

	public static final int LEFT1VICTORPORT = 2;
	public static final int LEFT2VICTORPORT = 3;
	public static final int RIGHT1VICTORPORT = 5;
	public static final int RIGHT2VICTORPORT = 7;

	public static final int LEFTINTAKEVICTORPORT = 4;
	public static final int RIGHTINTAKEVICTORPORT = 9;
	public static final int SPININTAKEVICTORSPPORT = 8;

	public static final int POWERWINCHVICTORPORT = 1;
	public static final int HOOKWINCHTALONPORT = 0;

	public static final int LEFTENCODERPORT1 = 2;
	public static final int LEFTENCODERPORT2 = 3;
	public static final int RIGHTENCODERPORT1 = 0;
	public static final int RIGHTENCODERPORT2 = 1;

	public static final int LEFTINTAKEENCODERPORT1 = 6;
	public static final int LEFTINTAKEENCODERPORT2 = 7;
	public static final int RIGHTINTAKEENCODERPORT1 = 10;
	public static final int RIGHTINTAKEENCODERPORT2 = 11;

	public static final int HOOKWINCHENCODERPORT1 = 4;
	public static final int HOOKWINCHENCODERPORT2 = 5;

	public static final int BALLLIMITSWITCHPORT = 8;

	public static final TurtlePIDConstants straightConstants = new TurtlePIDConstants(.00325, 0, .001, .00008);
	public static final TurtlePIDConstants turnConstants = new TurtlePIDConstants(.008, 0, .00035, .00045);
	public static final TurtlePIDConstants turnGyroConstants = new TurtlePIDConstants(.015, 0, .00035, .0004);

	public static final TurtlePIDConstants intakePIDConstants = new TurtlePIDConstants(.05, 0, .002, .0003);

	public static final double intakePIDkLR = 0.001;

	public static final TurtlePIDConstants hookRaiseConstants = new TurtlePIDConstants(.008, 0, .00035, .00045);
	public static final TurtlePIDConstants hookLowerConstants = new TurtlePIDConstants(.008, 0, .00035, .00045);
	public static final TurtlePIDConstants robotRaiseConstants = new TurtlePIDConstants(.008, 0, .002, .001);

	public static final double drivePIDTolerance = 20;
	public static final double hookPIDTolerance = 150;

	public static final double hookLiftEncoderTicks = 8360;
	public static final double hookDownEncoderTicks = 2046;
	// public static final double robotLiftEncoderTicks = 100;

	public static final double cameraHeight = 8;
	/**
	 * Elevation of camera in radians
	 */
	public static final double cameraAngle = 0;

	public static final double WHEELDIAMETER = 3.5;
	public static final double WHEELBASE = 24.70;
	/**
	 * In degrees
	 */
	public static final double roughTerrainFlatAngle = 5;

	/**
	 * In seconds
	 */
	public static final double roughTerrainMinFlatTime = 0.2;
	public static final double unfoldTime = .1;

	public static final double INTAKEENCODERMAX = 250;
	public static final double yankTime = .3;
	public static final double intakePIDScale = 100;

	public static final double intakePIDCurrentWeight = .75;

	/**
	 * Constructor so can't be initialised
 	 */
	private Constants() {}
}