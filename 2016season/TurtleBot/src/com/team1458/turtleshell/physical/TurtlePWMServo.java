package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.movement.TurtleSmartServo;
import com.team1458.turtleshell.util.Output;
import com.team1458.turtleshell.util.TurtleMaths;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;

public class TurtlePWMServo implements TurtleSmartServo {

	private final Servo servo;
	private final double minRange = 0;
	private final double maxRange = 180;
	private double currentAngle;
	private final Timer timer;
	private final double degreesPerSecond;
	private final double max;

	public TurtlePWMServo(int channel, double startingAngle, double degreesPerSec, double maxMovement) {
		servo = new Servo(channel);
		currentAngle = startingAngle;
		degreesPerSecond = degreesPerSec;
		max = maxMovement;
		timer = new Timer();
		servo.setAngle(currentAngle);
		timer.start();
	}

	@Override
	public double getAngle() {
		return servo.getAngle();
	}

	@Override
	public void setAngle(double angle) {
		angle = TurtleMaths.fitRange(angle, minRange, maxRange);
		currentAngle = angle;
		servo.setAngle(angle);
	}

	@Override
	public void updateAngle(double angle) {
		double timeElapsed = timer.get();
		timer.reset();
		angle = angle * timeElapsed * degreesPerSecond;
		angle = TurtleMaths.fitRange(angle, -max, max);
		currentAngle += angle;
		currentAngle = TurtleMaths.fitRange(currentAngle, minRange, maxRange);
		servo.setAngle(currentAngle);
		Output.outputNumber("currentAngle", currentAngle);
	}

	@Override
	public void stop() {
		servo.setAngle(servo.getAngle());
		
	}

}
