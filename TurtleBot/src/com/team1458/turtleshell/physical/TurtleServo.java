package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.Output;
import com.team1458.turtleshell.TurtleMaths;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurtleServo {

    private final Servo servo;
    private final double minRange = 0;
    private final double maxRange = 180;
    private double currentAngle;
    private final Timer timer;
    private final double degreesPerSecond;
    private final double max;

    public TurtleServo(int channel, double startingAngle, double degreesPerSec, double maxMovement) {
	servo = new Servo(channel);
	currentAngle = startingAngle;
	degreesPerSecond = degreesPerSec;
	max = maxMovement;
	timer = new Timer();
	servo.setAngle(currentAngle);
	timer.start();
    }

    public double getAngle() {
	return servo.getAngle();
    }

    public void setAngle(double angle) {
	angle = TurtleMaths.fitRange(angle, minRange, maxRange);
	currentAngle = angle;
	servo.setAngle(angle);
    }

    public void updateAngle(double angle) {
	double timeElapsed = timer.get();
	timer.reset();
	angle = angle*timeElapsed*degreesPerSecond;
	angle = TurtleMaths.fitRange(angle, -max, max);
	currentAngle += angle;
	currentAngle = TurtleMaths.fitRange(currentAngle, minRange, maxRange);
	servo.setAngle(currentAngle);
	Output.outputNumber("currentAngle", currentAngle);
    }
}
