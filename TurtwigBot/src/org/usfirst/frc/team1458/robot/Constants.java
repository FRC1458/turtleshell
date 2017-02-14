package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.MotorValue;
import com.team1458.turtleshell2.util.types.Time;

/**
 * Constants for Robot All inner classes are defined as "static final" so static
 * variables can be defined
 *
 * @author asinghani
 */
public class Constants {

	public static boolean DEBUG = true;
	public static final Logger.LogFormat LOGGER_MODE = Logger.LogFormat.PLAINTEXT;
	public static final double COLLISION_THRESHOLD = 1.5f;

	/**
	 * Control code
	 */
	public static final class DriverStation {
		public static final double JOYSTICK_DEADBAND = 0.1;
		public static final boolean USE_XBOX_CONTROLLER = true;
		public static final boolean LOGISTIC_SCALE = false;

		public static final class UsbPorts {
			public static final int RIGHT_STICK = 0;
			public static final int LEFT_STICK = 1;

			public static final int XBOX_CONTROLLER = 3;
		}
	}
	
	public static final class Drive {
		public static final double slowSpeed = 0.5;
	}

	/**
	 * Right drive system
	 */
	public static final class RightDrive {
		public static final int MOTOR1 = 13;
		public static final int MOTOR2 = 14;
		public static final int MOTOR3 = 15;

		public static final int ENCODER_A = -1;
		public static final int ENCODER_B = -1;
		public static final double ENCODER_RATIO = 0.0697777777;
	}

	/**
	 * Left drive system
	 */
	public static final class LeftDrive {
		public static final int MOTOR1 = 10;
		public static final int MOTOR2 = 11;
		public static final int MOTOR3 = 12;

		public static final int ENCODER_A = -1;
		public static final int ENCODER_B = -1;
		public static final double ENCODER_RATIO = 0.0697777777;
	}

	/**
	 * Intake-related constants
	 */
	public static final class Intake {
		public static final int MOTOR_PORT = 60;
		public static final MotorValue SPEED = MotorValue.fullForward;

		public static final MotorValue UNCLOG_SPEED = MotorValue.fullForward;
		public static final Time UNCLOG_TIME = Time.one;
	}

	/**
	 * Climber-related constants
	 */
	public static final class Climber {
		public static final int MOTOR_PORT = 20;

		public static final MotorValue SPEED = MotorValue.fullForward;
		public static final MotorValue SPEED_LOW = new MotorValue(0.3);
		public static final MotorValue SPEED_ZERO = new MotorValue(0.1);
		// Run at very low speed when at top (to prevent slipping)
		// TODO: ZERO not being zero is not a good idea. The climber should have
		// a ratchet, but failing that just rename this

	}

	/**
	 * Shooter-related constants
	 */
	public static final class LeftShooter {
		public static final int MOTOR_PORT = 19;
		public static final int HALL_PORT = -1;
		public static final double SPEED_RPM = 3600;
		public static final boolean motorReversed = false;

		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0, 0, 0);
		public static final MotorValue baseValue = new MotorValue(0.65);
	}

	/**
	 * Shooter-related constants
	 */
	public static final class RightShooter {
		public static final int MOTOR_PORT = 18;
		public static final int HALL_PORT = -1;
		public static final double SPEED_RPM = 3600;
		public static final boolean motorReversed = true;

		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0, 0, 0);
		public static final MotorValue baseValue = new MotorValue(0.65);
	}

	public static final class ShooterVision {
		public static final class Camera {
			public static final double MOUNT_ANGLE = 20.0;
			public static final double MOUNT_HEIGHT = 7.0;

			public static final double HEIGHT_FOV = 41.1;
			public static final double WIDTH_FOV = 54.8;

			public static final int WIDTH_PX = 320;
			public static final int HEIGHT_PX = 240;

			public static final String URL = "http://localhost:5800/?action=stream";
		}

		public static final class VisionPID {
			public static final PIDConstants PID_CONSTANTS = new PIDConstants(0.006, 0, 0);
			public static final MotorValue SPEED = new MotorValue(0.5);
		}
	}

	/**
	 * Settings for driver vision
	 */
	public static final class DriverVision {
		public static final int CAMERA_WIDTH = 320;
		public static final int CAMERA_HEIGHT = 240;

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
	 * Constructor so Constants can't be initialised
	 *
	 * @throws IllegalStateException
	 *             when called
	 */
	private Constants() {
		throw new IllegalStateException("Constants cannot be initialized. Something very bad has happened.");
	}
}