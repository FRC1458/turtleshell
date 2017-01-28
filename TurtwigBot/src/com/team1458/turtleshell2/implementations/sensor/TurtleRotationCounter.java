package com.team1458.turtleshell2.implementations.sensor;

import com.team1458.turtleshell2.interfaces.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Rate;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurtleRotationCounter implements TurtleRotationSensor {
	
	private final Counter c;
	private final Timer t;
	
	private Rate<Angle> prevRate;
	
	public TurtleRotationCounter(int port, boolean countHighs) {
		c = new Counter();
		c.setUpSource(port);
		c.setUpDownCounterMode();
		c.setSemiPeriodMode(countHighs);
		c.setMaxPeriod(1);
		c.setDistancePerPulse(1);
		c.setSamplesToAverage(1);
		
		t = new Timer();
	}

	@Override
	public Angle getRotation() {
		
		return Angle.createRevolutions(c.get()/2);
	}

	@Override
	public Rate<Angle> getRate() {
		prevRate = new Rate<>(c.getRate()/2);
		return prevRate;
	}

	@Override
	public void reset() {
		c.reset();
	}

}
