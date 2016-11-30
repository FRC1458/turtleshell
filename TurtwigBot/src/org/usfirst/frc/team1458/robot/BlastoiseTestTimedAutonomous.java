package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.interfaces.AutoMode;

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
public class BlastoiseTestTimedAutonomous implements AutoMode {
	private BlastoiseChassis chassis;
	private BlastoiseAutonomousController autonomous;

	private static double SPEED = 0.5;

	public BlastoiseTestTimedAutonomous(BlastoiseChassis chassis) {
		this.chassis = chassis;
		this.autonomous = new BlastoiseAutonomousController(chassis);
	}

	/**
	 * Runs the autonomous program
	 */
	@Override
	public void auto(){
		//autonomous.moveMillis(500, SPEED);
		autonomous.turnMillis(1000, SPEED);
		//autonomous.moveMillis(500, -SPEED);
		autonomous.turnMillis(1000, -SPEED);
	}
}
