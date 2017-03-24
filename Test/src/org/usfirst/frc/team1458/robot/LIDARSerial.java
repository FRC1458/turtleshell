package org.usfirst.frc.team1458.robot;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.TimerTask;

/**
 * LIDARLite v3 Sensor over I2C interface. Uses a Timer / Thread in the
 * background.
 * 
 * @author mehnadnerd & asinghani
 */
public class LIDARSerial {
	private final I2C sensor;

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
	 * @param updateMillis
	 */
	public LIDARSerial(I2C.Port port, long updateMillis) {
		sensor = new I2C(port, 0x62);
		
		Timer.delay(1.5);

		// sensor

		SmartDashboard.putBoolean("SensorAddress", sensor.addressOnly());

		// Configure Sensor. See
		// http://static.garmin.com/pumac/LIDAR_Lite_v3_Operation_Manual_and_Technical_Specifications.pdf

		sensor.write(0x02, 0x80); // Maximum number of acquisitions during
									// measurement
		sensor.write(0x04, 0x08); // Acquisition mode control
		sensor.write(0x1c, 0x00); // Peak detection threshold bypass

		reset();

		t = new java.util.Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				distance = measureDistance();
				velocity = measureVelocity();
				// System.out.println(distance.getInches()+" "+velocity.getValue());
			}
		}, updateMillis, updateMillis);
	}

	/**
	 * Instantiates LIDARLite with given port and 25Hz update speed
	 * 
	 * @param port
	 */
	public LIDARSerial(I2C.Port port) {
		this(port, 1000);
	}

	/**
	 * Measures distance and puts into distance variable
	 */
	private double measureDistance() {
		byte[] val = new byte[] { 0x13 };
		sensor.read(0x04, 1, val);
		Timer.delay(0.1);
		//System.out.println(Integer.toHexString(val[0]));

		byte[] high = new byte[] { 0x00, 0x00 };
		byte[] status = { Byte.MAX_VALUE }; // All bits are 1

		// This is the command for starting distance measurement with bias
		// correction
		sensor.write(0x00, 0x04);

		// TODO make this not fail and get stuck in a loop
		/**
		 * This block waits until the sensor is ready with new data
		 */
		int timeout = 500000;
		// sensor.read(0x01, 1, status); // check status
		/*
		 * while ((status[0] & 0b0000_0001) != 0b0000_0001) { // TODO need to
		 * check if this is correct for LSB sensor.read(0x01, 1, status); //
		 * check status if(status[0] != 0)
		 * System.out.println("Status "+Integer.toBinaryString(status[0]));
		 * timeout--; }
		 */

		Timer.delay(0.01);
		/*
		 * if((status[0] & 0b0000_0001) != 0b0000_0001) { return Distance.error;
		 * }
		 */
		Timer.delay(0.01);

		// System.out.println(sensor.transaction(dataToSend, sendSize,
		// dataReceived, receiveSize))

		if (1 == 1) {
			sensor.transaction(new byte[] { 0x01 }, 1, high, 2);
			/*
			 * if (!sensor.read(0x10, 1, low)) {
			 * 
			 * }
			 */
			Timer.delay(0.01);
			SmartDashboard.putString("High", Integer.toBinaryString(high[0]));
			SmartDashboard.putString("Low", Integer.toBinaryString(high[1]));
			int value = doubleByteToInt(new byte[] { high[0], high[1] });
			// System.out.println(value);
			return (value) / 2.54;
		}

		return Double.MIN_VALUE;
	}

	public double getDistance() {
		return distance;
	}

	private double measureVelocity() {
		double rate = ((distance - lastDistance) / ((lastTime - Timer
				.getFPGATimestamp()) / 1000.0));
		lastTime = Timer.getFPGATimestamp();
		lastDistance = distance;
		return rate;
	}

	public void reset() {
		sensor.write(0x00, 0x00);
	}

	/**
	 * This monstrosity converts a 2-byte array (LSB and MSB) into an integer.
	 * Don't try to understand it
	 * 
	 * @author mehnadnerd - but don't try to get me to fix it
	 * 
	 * @param byteArray
	 *            MSB, LSB
	 * @return The int representing the two combined, as in signed two's
	 *         complement format
	 */
	private int doubleByteToInt(byte[] byteArray) {
		// TODO everyone else has a much simpler way to do this, we are
		// over-complicating this
		return (int) byteArray[0] > 0 ? (byteArray[0] * 256 + Byte
				.toUnsignedInt(byteArray[1])) : Byte
				.toUnsignedInt(byteArray[0])
				* 256
				+ Byte.toUnsignedInt(byteArray[1]) - 65536;
	}
}
