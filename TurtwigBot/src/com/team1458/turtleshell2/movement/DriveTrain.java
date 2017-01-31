package com.team1458.turtleshell2.movement;

import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * Basic Robot Drive Train
 * Must be able to move 2 motors
 */
public interface DriveTrain {
	void updateMotors(MotorValue leftPower, MotorValue rightPower);
	void stopMotors();
}
