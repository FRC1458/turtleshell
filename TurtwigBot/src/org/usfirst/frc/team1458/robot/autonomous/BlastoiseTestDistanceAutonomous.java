package org.usfirst.frc.team1458.robot.autonomous;

import com.team1458.turtleshell2.util.TurtleLogger;

import org.usfirst.frc.team1458.robot.BlastoiseAutoMode;
import org.usfirst.frc.team1458.robot.components.BlastoiseChassis;

/**
 * Time-based autonomous test program
 *
 * Drives the robot forward for 4 feet,
 * then turns right for one second,
 * then drives backward for 4 feet,
 * then turns left for one second
 *
 * @author asinghani
 */
public class BlastoiseTestDistanceAutonomous extends BlastoiseAutoMode {

	private static double SPEED = 0.5;

	public BlastoiseTestDistanceAutonomous(BlastoiseChassis chassis, TurtleLogger logger) {
		super(chassis, logger);
	}

	/**
	 * Runs the autonomous program
	 */
	@Override
	public void auto(){
		moveDistance(48, SPEED);
		//turnMillis(1000, SPEED);
		//Timer.delay(2);
		//moveDistance(-48, SPEED);
		//turnMillis(1000, -SPEED);
	}
}
