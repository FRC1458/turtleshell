package org.usfirst.frc.team1458.robot.constants;

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
    public static final class RightDrive {
	    public static final int MOTOR1 = -1;
	    public static final int MOTOR2 = -1;

		public static final int ENCODER_A = -1;
	    public static final int ENCODER_B = -1;
	    public static final double ENCODER_RATIO = 0.0697777777;
    }

	/**
	 * Left drive system
	 */
	public static final class LeftDrive {
		public static final int MOTOR1 = -1;
		public static final int MOTOR2 = -1;

		public static final int ENCODER_A = -1;
		public static final int ENCODER_B = -1;
		public static final double ENCODER_RATIO = 0.0697777777;
	}

	/**
	 * Constructor so RobotConstants can't be initialised
	 *
	 * @throws IllegalStateException when called
 	 */
	private BlastoiseConstants() {
		throw new IllegalStateException("RobotConstants cannot be initialized. Something very bad has happened.");
	}
}