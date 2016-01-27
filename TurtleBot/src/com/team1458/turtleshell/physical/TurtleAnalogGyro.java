package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.TurtleTheta;

import edu.wpi.first.wpilibj.AnalogGyro;

public class TurtleAnalogGyro implements TurtleTheta{
	private AnalogGyro gyro;
	
	public TurtleAnalogGyro(int port) {
		gyro = new AnalogGyro(port);
	}
	
	@Override
	public double getContinousTheta() {
		return gyro.getAngle();
	}

	@Override
	public void reset() {
		gyro.reset();
	}

	@Override
	public double getRate() {
		return gyro.getRate();
	}

}
