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
public class Constants {

	/**
	 * If set to true, all code will run for practice robot
	 */
	public static boolean PRACTICE_ROBOT = false;

	public static boolean DEBUG = true;

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

	/**
	 * Intake-related constants
	 */
	public static final class Intake {
		public static final int MOTOR_PORT = -1;
		public static final MotorValue SPEED = MotorValue.fullForward;
	}

	/**
	 * Climber-related constants
	 */
	public static final class Climber {
		public static final int MOTOR_PORT = -1;

		public static final MotorValue SPEED = MotorValue.fullForward;
		public static final MotorValue SPEED_LOW = new MotorValue(0.3);
	}

	/**
	 * Shooter-related constants
	 */
	public static final class LeftShooter {
		public static final int MOTOR_PORT = -1;
		public static final int HALL_PORT = -1;
		public static final double SPEED_RPM = 3600;

		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0, 0, 0);
	}

	/**
	 * Shooter-related constants
	 */
	public static final class RightShooter {
		public static final int MOTOR_PORT = -1;
		public static final int HALL_PORT = -1;
		public static final double SPEED_RPM = 3600;

		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0, 0, 0);
	}

	public static final class ShooterVision {
		public static final class Camera {
			public static final double MOUNT_ANGLE = 20.0;
			public static final double MOUNT_HEIGHT = 7.0;

			public static final double HEIGHT_FOV = 41.1;
			public static final double WIDTH_FOV = 54.8;

			public static final double WIDTH_PX = 320;
			public static final double HEIGHT_PX = 240;

			public static final String URL = "http://localhost:5800/?action=stream";
		}

		public static final class VisionPID {
			public static final PIDConstants PID_CONSTANTS = new PIDConstants(0.006, 0, 0);
			public static final MotorValue SPEED = new MotorValue(0.5);
		}
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
	 * @throws IllegalStateException when called
 	 */
	private Constants() {
		throw new IllegalStateException("Constants cannot be initialized. Something very bad has happened.");
	}
}