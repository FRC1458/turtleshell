package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.TurtlePIDConstants;

/**
 * Constants for Robot
 * All inner classes are defined as "static final" so static variables can be defined
 *
 * @author asinghani
 */
public class RobotConstants {

	public static boolean PRACTICE_ROBOT = true;
	
	/**
	 * Shooter-related constants
	 */
	static final class Shooter {
		public static final int MOTOR_PORT = -1;
	}

	/**
     * USB ports
     */
    static final class UsbPorts {
	    public static final int XBOX_CONTROLLER = 3;
	    public static final int LEFT_STICK = 1;
	    public static final int RIGHT_STICK = 0;
    }

	/**
	 * RoboRIO sensor ports
	 */
	static final class Sensors {
		public static final int NAVX_PORT = 1; // TODO: Find correct port
		public static final int PRACTICE_ROBOT_DIO = 0;
	}

	/**
	 * Straight Drive PID
	 */
	static final class StraightDrivePID {
		public static final TurtlePIDConstants PID_CONSTANTS = new TurtlePIDConstants(.0015, 0, .0001, .0001);
		public static final double kLR = 0.00005;
		public static final double TOLERANCE = 2;
	}

	/**
	 * Turning PID
	 */
	static final class TurnPID {
		public static final TurtlePIDConstants PID_CONSTANTS = new TurtlePIDConstants(.0015, 0, .0001, .0001);
		public static final double TOLERANCE = 3;

		public static final double TURN_SPEED = 0.5;
	}

	/**
	 * Misc
	 */
	public static final int LOGGER_MODE = TurtleLogger.PLAINTEXT;
	public static final double JOYSTICK_DEADBAND = 0.05;
	public static final boolean USE_XBOX_CONTROLLER = false;

	public static final double MOTOR_DEADBAND = 0.05; // Only for logging to SmartDashboard
	public static final double COLLISION_THRESHOLD = 1.5f;

	/**
	 * Constructor so RobotConstants can't be initialised
	 *
	 * @throws IllegalStateException when called
 	 */
	private RobotConstants() {
		throw new IllegalStateException("RobotConstants cannot be initialized. Something very bad has happened.");
	}
}