package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.TurtleDashboard;
import edu.wpi.first.wpilibj.SampleRobot;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

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
		logger = new Logger(RobotConstants.LOGGER_MODE);
		this.robot = new BlastoiseRobot(logger);
	}

	@Override
	protected void robotInit() {
		robot.robotInit();
		TurtleDashboard.setup();
	}

	@Override
	protected void disabled() {
		robot.disabled();

		logger.info("Robot disabled");
		TurtleDashboard.disabled();
	}

	@Override
	public void autonomous() {
		robot.autonomous();

		logger.info("Entered autonomous control");
		TurtleDashboard.autonomous();
	}

	@Override
	public void operatorControl() {
		robot.operatorControl();

		logger.info("Entered operator control");
		TurtleDashboard.teleop();
	}

	@Override
	public void test() {
		robot.test();

		logger.info("Entered test mode");
		TurtleDashboard.test();
	}

	public static boolean isPracticeRobot() {
		return RobotConstants.PRACTICE_ROBOT;
	}
}
