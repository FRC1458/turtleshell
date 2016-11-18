package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.interfaces.AutoMode;

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
public class BlastoiseTestDistanceAutonomous implements AutoMode {
	private BlastoiseChassis chassis;
	private BlastoiseAutonomousController autonomous;

	private static double SPEED = 0.1;

	public BlastoiseTestDistanceAutonomous(BlastoiseChassis chassis) {
		this.chassis = chassis;
		this.autonomous = new BlastoiseAutonomousController(chassis);
	}

	/**
	 * Runs the autonomous program
	 */
	@Override
	public void auto(){
		autonomous.moveDistance(48, SPEED);
		autonomous.turnMillis(1000, SPEED);
		autonomous.moveDistance(-48, SPEED);
		autonomous.turnMillis(1000, -SPEED);
	}
}
