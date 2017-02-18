package com.team1458.turtleshell2.sensor;

import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.Rate;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.TimerTask;

/**
 * LIDARLite v3 Sensor over I2C interface. Uses a Timer / Thread in the background.
 * 
 * @author mehnadnerd & asinghani
 */
public class LIDARLite implements TurtleDistanceSensor {
	private final I2C sensor;

	private Distance lastDistance = Distance.error;
	private Rate<Distance> velocity = Rate.distanceZero;
	private double lastTime = 0; 

	private Distance distance = Distance.error;

	private static Object lock = new Object();
	
	/**
	 * Instantiates LIDARLite with given port and update speed
	 * @param port
	 * @param updateMillis
	 */
	public LIDARLite(I2C.Port port, long updateMillis) {
		sensor = new I2C(port, 0x62);

		// Configure Sensor. See http://static.garmin.com/pumac/LIDAR_Lite_v3_Operation_Manual_and_Technical_Specifications.pdf

		sensor.write(0x02, 0x80); // Maximum number of acquisitions during measurement
		sensor.write(0x04, 0x08); // Acquisition mode control
		sensor.write(0x1c, 0x00); // Peak detection threshold bypass

		new java.util.Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				synchronized (lock) {
					distance = measureDistance();
					velocity = measureVelocity();
				}
			}
		}, updateMillis, updateMillis);
	}

	/**
	 * Instantiates LIDARLite with given port and 25Hz update speed
	 * @param port
	 */
	public LIDARLite(I2C.Port port) {
		this(port, 40);
	}

	/**
	 * Measures distance and puts into distance variable
	 */
	private Distance measureDistance() {
		byte[] high = new byte[]{0x00};
		byte[] low = new byte[]{0x00};
		byte[] status = { Byte.MAX_VALUE }; // All bits are 1

		// This is the command for starting distance measurement with bias correction
		sensor.write(0x00, 0x04);


		// TODO make this not fail and get stuck in a loop
		/**
		 * This block waits until the sensor is ready with new data
		 */
		int timeout = 500;
		while ((status[0] & 0b0000_0001) == 0b0000_0001 && timeout > 0) { // TODO need to check if this is correct for LSB
			sensor.read(0x01, 1, status); // check status
			SmartDashboard.putString("Status", Integer.toBinaryString(status[0]));
			timeout--;
		}

		if((status[0] & 0b0000_0001) != 0b0000_0001) {
			return Distance.error;
		}

		if (!sensor.read(0x0f, 1, high)) {
			if (!sensor.read(0x10, 1, low)) {
				Timer.delay(0.01);
				SmartDashboard.putString("High", Integer.toBinaryString(high[0]));
				SmartDashboard.putString("Low", Integer.toBinaryString(low[0]));
				int value = doubleByteToInt(new byte[] { high[0], low[0] });
				System.out.println(value);
				return Distance.createCentimetres(value);
			}
		}

		return Distance.error;
	}
	
	private Rate<Distance> measureVelocity() {
		Rate<Distance> rate = new Rate<>((distance.getInches() - lastDistance.getInches()) / ((lastTime - Timer.getFPGATimestamp())/1000.0));
		lastTime = Timer.getFPGATimestamp();
		lastDistance = distance;
		return rate;
	}

	@Override
	public Distance getDistance() {
		return distance;
	}

	/**
	 * Returns the calculated velocity between the last 2 data points.
	 */
	@Override
	public Rate<Distance> getVelocity() {
		return velocity;
	}

	@Override
	public void reset() {
		sensor.write(0x00, 0x00);
	}

	/**
	 * This monstrosity converts a 2-byte array (LSB and MSB) into an integer.
	 * Don't try to understand it
	 * 
	 * @author mehnadnerd - but don't try to get me to fix it
	 * 
	 * @param byteArray MSB, LSB
	 * @return The int representing the two combined, as in signed two's complement format
	 */
	private int doubleByteToInt(byte[] byteArray) {
		// TODO everyone else has a much simpler way to do this, we are over-complicating this
		return (int) byteArray[0] > 0 ?
				(byteArray[0] * 256 + Byte.toUnsignedInt(byteArray[1])) :
				Byte.toUnsignedInt(byteArray[0]) * 256 + Byte.toUnsignedInt(byteArray[1]) - 65536;
	}
}
