package com.team1458.turtleshell2.sensor;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.TimerTask;

/**
 * LIDARLite v3 Sensor over serial interface. Uses a Timer / Thread in the
 * background.
 * 
 * @author mehnadnerd & asinghani
 */
public class LIDARLite {
	private final SerialPort sensor;

	private double lastDistance = Double.MIN_VALUE;
	private double velocity = 0;
	private double lastTime = 0;

	private double distance = Double.MIN_VALUE;

	private static Object lock = new Object();
	private java.util.Timer t;

	/**
	 * Instantiates LIDARLite with given port and update speed
	 * 
	 * @param port
	 */
	public LIDARLite(SerialPort.Port port) {
		sensor = new SerialPort(9600, port);
		
	}

	public double getDistance() {
		try {
			distance = Double.parseDouble(getStr());
		} catch (Throwable e) {
			distance = Double.NaN;
		}
		measureVelocity();
		return distance;
	}
	
	String datas = new String();
	String realValue = "";
	
	public String getStr() {

		String s = sensor.readString();
		
		datas += s;
		
		String[] array = datas.split("\n");
		
		
		String value = "";
		if(array.length >= 2) value = array[array.length - 2];
		if(value.contains("START") && value.contains("END")) realValue = value.replaceAll("START", "").replaceAll("END", "");
		
		if(datas.length() > 250) datas = value+"\n";
		
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

	
}
