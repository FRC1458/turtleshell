package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.TurtlePIDConstants;

/**
 * Constants for Blastoise Robot
 * All inner classes are defined as "static final" so static variables can be defined
 *
 * @author asinghani
 */
public class BlastoiseConstants {

    /**
     * Right drive system
     */
    static final class RightDrive {
	    public static final int MOTOR1 = 0; // Spark
	    public static final int MOTOR2 = 2; // Talon SR

		public static final int ENCODER_A = 0;
	    public static final int ENCODER_B = 1;
	    public static final double ENCODER_RATIO = 0.0697777777;
    }

	/**
	 * Left drive system
	 */
	static final class LeftDrive {
		public static final int MOTOR1 = 1; // Spark
		public static final int MOTOR2 = 3; // Talon SR

		public static final int ENCODER_A = 2;
		public static final int ENCODER_B = 3;
		public static final double ENCODER_RATIO = 0.0697777777;
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
	static final class TurningPID {
		public static final TurtlePIDConstants PID_CONSTANTS = new TurtlePIDConstants(.0015, 0, .0001, .0001);
		public static final double kLR = 0.00005;
		public static final double TOLERANCE = 0.05;
	}

	/**
	 * Misc
	 */
	public static final int LOGGER_MODE = TurtleLogger.PLAINTEXT;
	public static final int GYRO_PORT = -1; // TODO: Find correct port
	public static final double JOYSTICK_DEADBAND = 0.09;
	public static final boolean USE_FLIGHT_STICK = true;

	/**
	 * Constructor so BlastoiceConstants can't be initialised
	 * @throws IllegalStateException when called
 	 */
	private BlastoiseConstants() {
		throw new IllegalStateException("BlastoiseConstants cannot be initialized. Something very bad has happened.");
	}
}