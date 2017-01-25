package com.team1458.turtleshell2.interfaces;

import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * Basic Robot Chassis
 * Must be able to move 2 motors
 */
public interface Chassis {
	void updateMotors(MotorValue leftPower, MotorValue rightPower);
	void stopMotors();
}
