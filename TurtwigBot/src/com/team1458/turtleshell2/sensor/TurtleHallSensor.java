package com.team1458.turtleshell2.sensor;

import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Rate;

import edu.wpi.first.wpilibj.Counter;

/**
 * Represents hall sensor. Outputs data in RPM
 */
public class TurtleHallSensor implements TurtleRotationSensor {

	private static final double HALL_TO_RPM = 16.425;

	private final Counter c;
	
	private Rate<Angle> prevRate;

	public TurtleHallSensor(int port) {
		this(port, false);
	}

	public TurtleHallSensor(int port, boolean countHighs) {
		c = new Counter();
		c.setUpSource(port);
		c.setUpDownCounterMode();
		c.setSemiPeriodMode(countHighs);
		c.setMaxPeriod(1);
		c.setDistancePerPulse(1);
		c.setSamplesToAverage(1);
	}

	@Override
	public Angle getRotation() {
		return Angle.createRevolutions(c.get()/2);
	}

	/**
	 * Get the RPM rate of the sensor
	 * @return RPM
	 */
	@Override
	public Rate<Angle> getRate() {
		prevRate = new Rate<>(c.getRate()/2);
		return new Rate<>(prevRate.getValue() * HALL_TO_RPM);
	}

	public double getHall() {
		prevRate = new Rate<>(c.getRate()/2);
		return prevRate.getValue();
	}

	public double getRPM() {
		prevRate = new Rate<>(c.getRate()/2);
		return prevRate.getValue() * HALL_TO_RPM;
	}

	@Override
	public void reset() {
		c.reset();
	}

	public static double hallToRpm(double hall) {
		return hall * HALL_TO_RPM;
	}

	public static double rpmToHall(double rpm) {
		return rpm / HALL_TO_RPM;
	}
}
