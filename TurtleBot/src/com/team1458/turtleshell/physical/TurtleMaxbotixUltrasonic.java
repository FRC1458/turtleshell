package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.TurtleDistance;

import edu.wpi.first.wpilibj.AnalogInput;

/**
 * A Maxbotix HRLV-EZ4 Ultrasonic sensor
 * 
 * @author mehnadnerd
 *
 */
public class TurtleMaxbotixUltrasonic implements TurtleDistance {
	AnalogInput sonic;

	@Override
	public double getDistance() {
		return (sonic.getVoltage() * 42.946 - 0.4437);
	}

	public TurtleMaxbotixUltrasonic(int port) {
		sonic = new AnalogInput(port);
	}

	@Override
	public void update() {

	}

}
