package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.TurtleMaths;

import edu.wpi.first.wpilibj.Servo;

public class TurtleServo {

	private final Servo servo;
	private final double minRange = 15;
	private final double maxRange = 135;

	public TurtleServo(int channel) {
		servo = new Servo(channel);
	}

	public double getAngle() {
		return servo.getAngle();
	}

	public void setAngle(double angle) {
		angle=TurtleMaths.fitRange(angle, minRange, maxRange);
		servo.setAngle(angle);
	}
}
