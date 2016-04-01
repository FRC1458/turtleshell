package com.team1458.turtleshell2.implementations.sensor;

import com.team1458.turtleshell2.interfaces.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Rate;

import edu.wpi.first.wpilibj.AnalogGyro;

public class TurtleAnalogGyro implements TurtleRotationSensor {
	private final AnalogGyro g;
	
	public TurtleAnalogGyro(int port) {
		g = new AnalogGyro(port);
	}
	
	@Override
	public Angle getRotation() {
		return Angle.createDegrees(g.getAngle());
	}

	@Override
	public Rate<Angle> getRate() {
		return new Rate<Angle>(g.getRate());
	}

	@Override
	public void reset() {
		g.reset();
	}

}
