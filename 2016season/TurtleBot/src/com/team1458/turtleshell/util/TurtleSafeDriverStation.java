package com.team1458.turtleshell.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

/**
 * Class to access driver station functions in a safe matter
 * 
 * @author mehnadnerd
 *
 */
public class TurtleSafeDriverStation {
	private static DriverStation ds;
	private static boolean dsSet = false;

	public static void setDS(DriverStation ds) {
		TurtleSafeDriverStation.ds = ds;
		dsSet = true;
	}

	private static boolean dsSet() {
		if (dsSet) {
			return true;
		} else {
			System.out.println("Driver Station has not been set, unpredictable behaviour may occur.");
			return false;
		}
	}

	/**
	 * Returns whether or not the robot is enabled
	 * 
	 * @return
	 */
	public static boolean isEnabled() {
		return (dsSet() ? ds.isEnabled() : false);
	}

	public static boolean isDisabled() {
		return (dsSet() ? ds.isDisabled() : true);
	}

	public static boolean isOperatorControl() {
		return (dsSet() ? ds.isOperatorControl() : false);
	}

	public static boolean isAutonomous() {
		return (dsSet() ? ds.isAutonomous() : false);
	}

	public static boolean isTest() {
		return (dsSet() ? ds.isTest() : false);
	}

	public static Alliance getAlliance() {
		return (dsSet() ? ds.getAlliance() : Alliance.Invalid);
	}

	public static double getBatteryVoltage() {
		return (dsSet() ? ds.getBatteryVoltage() : -1);
	}

	public static boolean canAuto() {
		return isAutonomous() && isEnabled();
	}

	public static boolean canTele() {
		return isOperatorControl() && isEnabled();
	}

	/**
	 * Get remaining match time in seconds, is relative to end of current period
	 * (auto or teleop), not guaranteed to be accurate
	 * 
	 * @return Seconds, or -1 if driver station not set
	 */
	public static double getMatchTime() {
		return (dsSet() ? ds.getMatchTime() : -1);
	}

}
