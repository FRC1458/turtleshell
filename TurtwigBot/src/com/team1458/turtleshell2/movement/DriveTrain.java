package com.team1458.turtleshell2.movement;

import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * This is a generic class for any sort of robot drive train (tank, swerve, mecanum, etc).
 * Any unsupported operations should cause the implementation to throw an UnsupportedOperationException.
 */
public interface DriveTrain {

	void driveForward(Distance distance, MotorValue speed, PIDConstants constants, Distance tolerance);

	/**
	 * Convenience method to drive the robot backward.
	 * By default this has exact same behaviour as driveForward(), but with opposite direction.
	 * @param distance
	 * @param speed
	 * @param constants
	 * @param tolerance
	 */
	default void driveBackward(Distance distance, MotorValue speed, PIDConstants constants, Distance tolerance) {
		driveForward(distance.invert(), speed, constants, tolerance);
	}

	void driveRight(Distance distance, MotorValue speed, PIDConstants constants, Distance tolerance);

	/**
	 * Convenience method to drive the robot left.
	 * By default this has exact same behaviour as driveRight(), but with opposite direction.
	 * @param distance
	 * @param speed
	 * @param constants
	 * @param tolerance
	 */
	default void driveLeft(Distance distance, MotorValue speed, PIDConstants constants, Distance tolerance) {
		driveRight(distance.invert(), speed, constants, tolerance);
	}

	void turnRight(Angle angle, MotorValue speed, PIDConstants constants, Angle tolerance);

	/**
	 * Convenience method to turn the robot left.
	 * By default this has exact same behaviour as turnRight(), but with opposite direction.
	 * @param angle
	 * @param speed
	 * @param constants
	 * @param tolerance
	 */
	default void turnLeft(Angle angle, MotorValue speed, PIDConstants constants, Angle tolerance) {
		turnRight(angle.invert(), speed, constants, tolerance);
	}

	void stop();
}
