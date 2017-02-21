package com.team1458.turtleshell2.sensor;

import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.Rate;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;

/**
 * LIDARLite v3 Sensor over serial interface. Uses a Timer / Thread in the
 * background.
 * 
 * @author mehnadnerd & asinghani
 */
public class LIDARSerial implements TurtleDistanceSensor {
	private final SerialPort sensor;

	private double lastDistance = Double.MIN_VALUE;
	private double lastTime = 0;

	private double distance = Double.MIN_VALUE;

	/**
	 * Instantiates LIDARLite with given port and update speed
	 * 
	 * @param port
	 */
	public LIDARSerial(SerialPort.Port port) {
		sensor = new SerialPort(9600, port);

	}

	@Override
	public Distance getDistance() {
		try {
			distance = Double.parseDouble(getStr());
		} catch (Throwable e) {
			distance = Double.NaN;
		}
		measureVelocity();
		return Distance.createCentimetres(distance);
	}

	String datas = new String();
	String realValue = "";

	public String getStr() {
		
		String s = sensor.readString();
		
		datas += s;
		System.out.println("LIDAR THINGY: "+datas);
		String[] array = datas.split("\n");

		String value = "";
		if (array.length >= 2)
			value = array[array.length - 2];
		if (value.contains("START") && value.contains("END"))
			realValue = value.replaceAll("START", "").replaceAll("END", "");

		if (datas.length() > 250)
			datas = value + "\n";

		return realValue;
	}

	private double measureVelocity() {
		double rate = ((distance - lastDistance) / ((lastTime - Timer
				.getFPGATimestamp()) / 1000.0));
		lastTime = Timer.getFPGATimestamp();
		lastDistance = distance;
		return rate;
	}

	public void reset() {
		// Don't do the thing
	}

	@Override
	public Rate<Distance> getVelocity() {
		this.getDistance();
		return new Rate<>(measureVelocity());
	}

}
