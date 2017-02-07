package org.usfirst.frc.team1458.robot.constants;

import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.MotorValue;
import com.team1458.turtleshell2.util.types.Time;

/**
 * Constants for Robot
 * All inner classes are defined as "static final" so static variables can be defined
 *
 * @author asinghani
 */
public class RobotConstants {

	public static boolean PRACTICE_ROBOT = false;
	
	/**
	 * Shooter-related constants
	 */
	public static final class Shooter {
		public static final int MOTOR_PORT = -1;
		public static final double HIGH_RPM = 4000;
		public static final double LOW_RPM = 3500;

		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0, 0, 0);
	}

	/**
	 * Intake-related constants
	 */
	public static final class Intake {
		public static final int MOTOR_PORT = -1;
		public static final MotorValue SPEED = MotorValue.fullForward;

		public static final MotorValue UNCLOG_SPEED = MotorValue.fullForward;
		public static final Time UNCLOG_TIME = Time.one;
		
	}

	/**
	 * Climber-related constants
	 */
	public static final class Climber {
		public static final int MOTOR_PORT = -1;

		public static final MotorValue SPEED = MotorValue.fullForward;
		public static final MotorValue SPEED_LOWER = new MotorValue(0.3);
		public static final MotorValue SPEED_ZERO = new MotorValue(0.1); // Run at very low speed when at top (to prevent slipping)
	}

	/**
     * The USB ports controllers are attached to.
     */
    public static final class UsbPorts {
	    public static final int XBOX_CONTROLLER = 3;
	    public static final int LEFT_STICK = 1;
	    public static final int RIGHT_STICK = 0;
    }

	/**
	 * RoboRIO sensor ports
	 */
	public static final class Sensors {

	}

	/**
	 * Settings for vision
	 */
	public static final class Vision {
		public static final int CAMERA_WIDTH = 480;
		public static final int CAMERA_HEIGHT = 320;

		public static final String CAMERA_URL = "http://localhost:5801/?action=stream";
	}

	/**
	 * Straight Drive PID
	 */
	public static final class StraightDrivePID {
		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0, 0, 0);
		public static final double kLR = 0.00005;
		public static final double TOLERANCE = 2;
	}

	/**
	 * Turning PID
	 */
	public static final class TurnPID {
		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0.01, 0.001, 0.002);
		public static final double TOLERANCE = 3;

		public static final MotorValue TURN_SPEED = new MotorValue(0.65);
		public static final MotorValue MIN_SPEED = new MotorValue(0.15);
	}

	/**
	 * Gear Alignment PID
	 */
	public static final class GearPID {
		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0, 0, 0);

		public static final MotorValue SPEED = new MotorValue(0.5);

	}

	/**
	 * Misc
	 */
	public static final Logger.LogFormat LOGGER_MODE = Logger.LogFormat.PLAINTEXT;
	public static final double JOYSTICK_DEADBAND = 0.08;
	public static final boolean USE_XBOX_CONTROLLER = false;
	public static final boolean LOGISTIC_SCALE = false;

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