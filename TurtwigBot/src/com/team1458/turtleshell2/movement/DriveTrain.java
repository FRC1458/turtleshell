package com.team1458.turtleshell2.movement;

import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * This is a generic class for any sort of robot chassis train (tank, swerve, mecanum, etc).
 * Any unsupported operations should cause the implementation to throw an UnsupportedOperationException.
 */
public interface DriveTrain {

	void driveForward(Distance distance, MotorValue speed, Distance tolerance);

	/**
	 * Convenience method to chassis the robot backward.
	 * By default this has exact same behaviour as driveForward(), but with opposite direction.
	 * @param distance
	 * @param speed
	 * @param tolerance
	 */
	default void driveBackward(Distance distance, MotorValue speed, Distance tolerance) {
		driveForward(distance.invert(), speed, tolerance);
	}

	void driveRight(Distance distance, MotorValue speed, Distance tolerance);

	/**
	 * Convenience method to chassis the robot left.
	 * By default this has exact same behaviour as driveRight(), but with opposite direction.
	 * @param distance
	 * @param speed
	 * @param tolerance
	 */
	default void driveLeft(Distance distance, MotorValue speed, Distance tolerance) {
		driveRight(distance.invert(), speed, tolerance);
	}

	void turnRight(Angle angle, MotorValue speed, Angle tolerance);

	/**
	 * Convenience method to turn the robot left.
	 * By default this has exact same behaviour as turnRight(), but with opposite direction.
	 * @param angle
	 * @param speed
	 * @param tolerance
	 */
	default void turnLeft(Angle angle, MotorValue speed, Angle tolerance) {
		turnRight(angle.invert(), speed, tolerance);
	}

	void stop();
}
