package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.pid.TurtlePDD2Constants;
import com.team1458.turtleshell.pid.TurtlePIDConstants;

public class TurtwigConstants {
	public static final int RJOYSTICKPORT = 0;
	public static final int LJOYSTICKPORT = 1;
	public static final int BUTTONPANELPORT = 2;
	
	public static final int LEFT1VICTORPORT = 0;
	public static final int LEFT2VICTORPORT = 1;
	public static final int RIGHT1VICTORPORT = 2;
	public static final int RIGHT2VICTORPORT = 3;
	
	public static final int LEFTINTAKEVICTORPORT = 4;
	public static final int RIGHTINTAKEVICTORPORT = 5;
	public static final int SPININTAKEVICTORPORT = 6;
	public static final int POWERWINCHVICTORPORT = 7;
	public static final int HOOKWINCHVICTORPORT = 8;
	
	public static final int LEFTENCODERPORT1 = 0;
	public static final int LEFTENCODERPORT2 = 1;
	public static final int RIGHTENCODERPORT1 = 2;
	public static final int RIGHTENCODERPORT2 = 3;
	
	public static final int LEFTINTAKEENCODERPORT1 = 4;
	public static final int LEFTINTAKEENCODERPORT2 = 5;
	public static final int RIGHTINTAKEENCODERPORT1 = 6;
	public static final int RIGHTINTAKEENCODERPORT2 = 7;
	
	public static final int POWERWINCHENCODERPORT1 = 8;
	public static final int POWERWINCHENCODERPORT2 = 9;
	public static final int HOOKWINCHENCODERPORT1 = 10;
	public static final int HOOKWINCHENCODERPORT2 = 11;
	
	public static final int SOLENOIDPORT = 0;
	
	public static final double WHEELDIAMETER = 8;
	public static final double WHEELBASE = 24;

	public static final int GYROPORT = 1;

	public static final TurtlePIDConstants straightConstants = new TurtlePDD2Constants(.00325, .001, .00008);
	public static final TurtlePIDConstants turnConstants = new TurtlePDD2Constants(.008, .00035, .00045);
	public static final TurtlePIDConstants turnGyroConstants = new TurtlePDD2Constants(.015, .00035, .0004);
	
	public static final TurtlePIDConstants aClimber = new TurtlePDD2Constants(.008, .00035, .00045);
	public static final TurtlePIDConstants bClimber = new TurtlePDD2Constants(.015, .00035, .0004);
	public static final TurtlePIDConstants cClimber = new TurtlePDD2Constants(.008, .00035, .00045);
	public static final TurtlePIDConstants dClimber = new TurtlePDD2Constants(.015, .00035, .0004);
	
	public static final double kABA = 0.042;
	public static final double kABB = 0.042;
	public static final double kABA2 = 0.042;
	public static final double kABB2 = 0.042;
	public static final double aTolerance = 42;
	public static final double bTolerance = 42;
	public static final double cTolerance = 42;
	public static final double dTolerance = 42;
	
	public static final double cameraHeight = 8;
	/**
	 * Elevation of camera in radians
	 */
	public static final double cameraAngle = 0;
	
	public static final double pidTolerance = 20;
	


}
