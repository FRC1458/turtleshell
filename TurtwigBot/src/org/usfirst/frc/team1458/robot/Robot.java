package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.TurtleDashboard;
import edu.wpi.first.wpilibj.SampleRobot;

/**
 * This is the base robot code.
 * @author asinghani
 */
public class Robot extends SampleRobot {

	private BlastoiseRobot robot;
	private Logger logger;

	/**
	 * Constructor for robot
	 */
	public Robot() {
		logger = new Logger(Logger.LogFormat.PLAINTEXT);
		this.robot = new BlastoiseRobot(logger);
	}

	@Override
	protected void robotInit() {
		robot.robotInit();
		TurtleDashboard.setup();
	}

	@Override
	protected void disabled() {
		logger.info("Robot disabled");
		TurtleDashboard.disabled();
		
		robot.disabled();

	}

	@Override
	public void autonomous() {
		logger.info("Entered autonomous control");
		TurtleDashboard.autonomous();
		
		robot.autonomous();

	}

	@Override
	public void operatorControl() {
		logger.info("Entered operator control");
		TurtleDashboard.teleop();
		
		robot.operatorControl();

	}

	@Override
	public void test() {
		logger.info("Entered test mode");
		TurtleDashboard.test();
		
		robot.test();
	}
}
