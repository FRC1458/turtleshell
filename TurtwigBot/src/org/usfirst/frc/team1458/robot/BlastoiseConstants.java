package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.TurtlePIDConstants;

/**
 * BlastoiseConstants for new robot
 * All inner classes are defined as "static" so static variables can be defined
 *
 * @author asinghani
 */
public class BlastoiseConstants {

    /**
     * Right drive system
     */
    static class RightDrive {
	    public static final int MOTOR1 = -1; // TODO: Use correct value for right MOTOR1 port
	    //public static final int MOTOR2 = -1

		public static final int ENCODER_A = -1; // TODO: Use correct value for right ENCODER_A port
	    public static final int ENCODER_B = -1; // TODO: Use correct value for right ENCODER_B port
	    public static final double ENCODER_RATIO = 1; // TODO: Use correct value for right ENCODER_RATIO
    }

	/**
	 * Left drive system
	 */
	static class LeftDrive {
		public static final int MOTOR1 = -1; // TODO: Use correct value for left MOTOR1 port
		//public static final int MOTOR2 = -1

		public static final int ENCODER_A = -1; // TODO: Use correct value for left ENCODER_A port
		public static final int ENCODER_B = -1; // TODO: Use correct value for left ENCODER_B port
		public static final double ENCODER_RATIO = 1; // TODO: Use correct value for left ENCODER_RATIO
	}

    /**
     * USB ports
     */
    static class UsbPorts {
	    public static final int XBOX_CONTROLLER = 1;
    }

	/**
	 * Straight Drive PID
	 */
	static class StraightDrive {
		public static final TurtlePIDConstants PID_CONSTANTS = new TurtlePIDConstants(.0015, 0, .0001, .0001);
		public static final double kLR = 0.00005;
		public static final double TOLERANCE = 0.05;
	}

	/**
	 * Turning PID
	 */
	static class Turning {
		public static final TurtlePIDConstants PID_CONSTANTS = new TurtlePIDConstants(.0015, 0, .0001, .0001);
		public static final double kLR = 0.00005;
		public static final double TOLERANCE = 0.05;
	}

	/**
	 * Misc
	 */
	public static final int LOGGER_MODE = TurtleLogger.PLAINTEXT;

	/**
	 * Constructor so can't be initialised
 	 */
	private BlastoiseConstants() {}
}