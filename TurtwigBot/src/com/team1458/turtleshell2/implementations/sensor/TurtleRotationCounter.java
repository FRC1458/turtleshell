package com.team1458.turtleshell2.implementations.sensor;

import com.team1458.turtleshell2.interfaces.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Rate;

import edu.wpi.first.wpilibj.Counter;

public class TurtleRotationCounter implements TurtleRotationSensor {
	
	private final Counter c;
	
	public TurtleRotationCounter(int port, boolean countHighs) {
		c = new Counter();
		c.setUpSource(port);
		c.setUpDownCounterMode();
		c.setSemiPeriodMode(countHighs);
	}

	@Override
	public Angle getRotation() {
		return Angle.createDegrees(360*c.get());
	}

	@Override
	public Rate<Angle> getRate() {
		return new Rate<>(360*c.getRate());
	}

	@Override
	public void reset() {
		c.reset();
	}

}
