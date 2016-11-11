package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.autonomous.TurtleAutonomousController;
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
public class BlastoiseTestTimedAutonomous {
	private BlastoiseChassis chassis;
	private TurtleAutonomousController autonomous;
	private TurtleLogger logger;

	private static double SPEED = 0.25;

	public BlastoiseTestTimedAutonomous(BlastoiseChassis chassis) {
		this.chassis = chassis;
		this.autonomous = new TurtleAutonomousController(chassis);
		this.logger = TurtleLogger.getLogger();
	}

	/**
	 * Runs the autonomous program
	 */
	public void run(){
		autonomous.moveMillis(1000, SPEED);
		autonomous.turnMillis(1000, SPEED);
		autonomous.moveMillis(1000, -SPEED);
		autonomous.turnMillis(1000, -SPEED);

		logger.info("BlastoiseTestTimedAutonomous running command list.");
		autonomous.run();
	}
}
