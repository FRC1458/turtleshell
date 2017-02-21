package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * Constants for Robot. All inner classes are defined as "static final" so
 * static variables can be defined
 *
 * @author asinghani
 */
public class Constants {

	public static boolean DEBUG = true;
	public static final Logger.LogFormat LOGGER_MODE = Logger.LogFormat.PLAINTEXT;

	public static final boolean LOGGER_PRETTY_PRINT = true;

	public static final class DriverStation {
		public static final double JOYSTICK_DEADBAND = 0.1;
		public static final boolean USE_XBOX_CONTROLLER = false;
		public static final boolean LOGISTIC_SCALE = false;

		public static final class UsbPorts {
			public static final int RIGHT_STICK = 0;
			public static final int LEFT_STICK = 1;

			public static final int XBOX_CONTROLLER = 2;
			public static final int DEBUG_XBOX_CONTROLLER = 3;
		}
	}

	public static final class Drive {
		public static final double SLOW_SPEED = 0.5;
	}

	public static final class RightDrive {
		public static final int MOTOR1 = TalonID.DILLON.id;
		public static final int MOTOR2 = TalonID.ELSA.id;
		public static final int MOTOR3 = TalonID.FITZ.id;

		public static final int ENCODER_A = 2;
		public static final int ENCODER_B = 3;
		public static final double ENCODER_RATIO = 4 * Math.PI / 600;
		// (Diameter * PI) / (Ticks Per Rotation)
		// Wheel diameter = 4 inches
	}

	public static final class LeftDrive {
		public static final int MOTOR1 = TalonID.ALLEN.id;
		public static final int MOTOR2 = TalonID.BOB.id;
		public static final int MOTOR3 = TalonID.CARTER.id;

		public static final int ENCODER_A = 0;
		public static final int ENCODER_B = 1;
		public static final double ENCODER_RATIO = 4 * Math.PI / 600;
		// (Diameter * PI) / (Ticks Per Rotation)
	}

	public static final class Intake {
		public static final int MOTOR_PORT = TalonID.GRANT.id;
		public static final MotorValue SPEED = new MotorValue(1);
		public static final MotorValue REVERSE_SPEED = new MotorValue(-0.8);
	}

	public static final class Climber {
		public static final int MOTOR_PORT = TalonID.HITAGI.id;

		public static final MotorValue SPEED = new MotorValue(0.8);
		public static final MotorValue SPEED_LOW = new MotorValue(0.3);

	}

	public static final class Shooter {
		public static final MotorValue REVERSE_SPEED = new MotorValue(-0.20);
		public static final boolean UBP = true;
		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0.00009, 0, 0.000001);
		public static final double UBPS = 0.000175;

		public static final int AGITATOR_PORT = TalonID.KAREN.id;
		public static final MotorValue AGITATOR_SPEED = new MotorValue(0.6);
	}

	public static final class LeftShooter {
		public static final int MOTOR_PORT = TalonID.JESSICA.id;
		public static final int HALL_PORT = 4;
		public static final double SPEED_RPM = 4850;
		public static final boolean MOTOR_REVERSED = false;

		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0.001, 0, 0);
		public static final MotorValue BASE_VALUE = new MotorValue(0);

		// Inches
		public static final TurtleMaths.AdvancedRangeShifter RPM_SHIFTER = new TurtleMaths.AdvancedRangeShifter(
				new double[] { 0.0 }, new double[] { 0.0 });
		public static final TurtleMaths.AdvancedRangeShifter OPENLOOP_SHIFTER = new TurtleMaths.AdvancedRangeShifter(
				new double[] { 0.0 }, new double[] { 0.0 });
	}

	public static final class RightShooter {
		public static final int MOTOR_PORT = TalonID.ISAAC.id;
		public static final int HALL_PORT = 5;
		public static final double SPEED_RPM = 4350;
		public static final boolean MOTOR_REVERSED = true;

		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0.001, 0, 0);
		public static final MotorValue BASE_VALUE = new MotorValue(0);

		// Inches
		public static final TurtleMaths.AdvancedRangeShifter RPM_SHIFTER = new TurtleMaths.AdvancedRangeShifter(
				new double[] { 0.0 }, new double[] { 0.0 });
		public static final TurtleMaths.AdvancedRangeShifter OPENLOOP_SHIFTER = new TurtleMaths.AdvancedRangeShifter(
				new double[] { 0.0 }, new double[] { 0.0 });
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

	public static final class DriverVision {
		public static final int CAMERA_WIDTH = 320;
		public static final int CAMERA_HEIGHT = 240;

		public static final String CAMERA_URL = "http://localhost:5801/?action=stream";
	}

	public static final class StraightDrivePID {
		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0, 0, 0);
		public static final PIDConstants TURN_PID_CONSTANTS = new PIDConstants(0, 0, 0);
		public static final double kLR = 0.00005;
		public static final double TOLERANCE = 2;

		public static final MotorValue SPEED = new MotorValue(0.65);
	}

	public static final class TurnPID {
		public static final PIDConstants PID_CONSTANTS = new PIDConstants(0.01, 0.001, 0.002);
		public static final double TOLERANCE = 3;

		public static final MotorValue SPEED = new MotorValue(0.65);
	}

	public static enum TalonID {
		ORIGINAL(60), ALLEN(10), BOB(11), CARTER(12), DILLON(13), ELSA(14), FITZ(15), GRANT(16), HITAGI(17), ISAAC(
				18), JESSICA(19), KAREN(20), LISA(21);
		public final int id;

		TalonID(int id) {
			this.id = id;
		}
	}

	/**
	 * Constructor so Constants can't be initialized
	 *
	 * @throws IllegalStateException
	 *             when called
	 */
	private Constants() {
		throw new IllegalStateException("Constants cannot be initialized. Something very bad has happened.");
	}
}