package com.team1458.turtleshell.util;

import com.team1458.turtleshell.logging.TurtleLogger;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Output {
	
	/**
	 * Just spew something to the SmartDashboard
	 * @param o Object to spew
	 */
	public static void spew(Object o) {
		SmartDashboard.putString(o.hashCode()+"", o.toString());
		TurtleLogger.verbose("Spewing to SmartDashboard: "+o.toString());
	}
	/**
	 * Output a number to the SmartDashboard
	 * @param s The name to use for the parameter.
	 * @param n The number to output.
	 */
	public static void outputNumber(String s, Number n) {
		SmartDashboard.putNumber(s, n.doubleValue());
		TurtleLogger.verbose("Outputing to SmartDashboard" + n + "with key "+s);
	}
	
	/**
	 * Output a boolean to the SmartDashboard
	 * @param s The name to use for the parameter.
	 * @param b The boolean to output.
	 */
	public static void outputBoolean(String s, boolean b) {
		SmartDashboard.putBoolean(s, b);
		TurtleLogger.verbose("Outputing to SmartDashboard" + b + "with key "+s);
	}
	
	/**
	 * Output to standard out.
	 * @param o The object to output.
	 */
	public static void syso(Object o) {
		System.out.println(o);
		TurtleLogger.verbose("Outputing to standard out: " + o);
	}
}
