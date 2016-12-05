package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.util.TurtleLogger;

/**
 * Time-based autonomous test program
 *
 * Drives the robot forward for 1 second,
 * then turns right for one second,
 * then drives backward for one second,
 * then turns left for one second
 *
 * @author asinghani
 */
public class BlastoiseTestTimedAutonomous extends BlastoiseAutoMode {

	private static double SPEED = 0.5;

	public BlastoiseTestTimedAutonomous(BlastoiseChassis chassis, TurtleLogger logger) {
		super(chassis, logger);
	}

	/**
	 * Runs the autonomous program
	 */
	@Override
	public void auto(){
		//moveMillis(500, SPEED);
		turnMillis(1000, SPEED);
		//moveMillis(500, -SPEED);
		turnMillis(1000, -SPEED);
	}
}