package com.team1458.turtleshell2.sensor;

import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.Rate;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.TimerTask;

/**
 * LIDARLite v3 Sensor over I2C interface. Uses a Timer / Thread in the background
 * 
 * @author mehnadnerd & asinghani
 */
public class LIDARLite implements TurtleDistanceSensor {
	private final I2C ic;

	private Distance distance = Distance.error;

	private static Object lock = new Object();

	public LIDARLite(I2C.Port port, long update) {
		ic = new I2C(port, 0x62);

		// Configure Sensor. See http://static.garmin.com/pumac/LIDAR_Lite_v3_Operation_Manual_and_Technical_Specifications.pdf

		ic.write(0x02, 0x80); // Maximum number of acquisitions during measurement
		ic.write(0x04, 0x08); // Acquisition mode control
		ic.write(0x1c, 0x00); // Peak detection threshold bypass

		new java.util.Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				synchronized (lock) {
					distance = measureDistance();
				}
			}
		}, update, update);
	}

	/**
	 * Measures distance and puts into distance variable
	 */
	public Distance measureDistance() {
		byte[] high = new byte[]{0x00};
		byte[] low = new byte[]{0x00};
		byte[] status = { Byte.MAX_VALUE }; // All bits are 1

		// This is the command for starting distance measurement with bias correction
		ic.write(0x00, 0x04);


		// TODO make this not fail and get stuck in a loop
		/**
		 * This block waits until the sensor is ready with new data
		 */
		int timeout = 500;
		while ((status[0] & 0b0000_0001) == 0b0000_0001 && timeout > 0) { // TODO need to check if this is correct for LSB
			ic.read(0x01, 1, status); // check status
			SmartDashboard.putString("Status", Integer.toBinaryString(status[0]));
			timeout--;
		}

		if((status[0] & 0b0000_0001) != 0b0000_0001) {
			return Distance.error;
		}

		if (!ic.read(0x0f, 1, high)) {
			if (!ic.read(0x10, 1, low)) {
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

	@Override
	public Distance getDistance() {
		synchronized (lock) {
			distance = measureDistance();
		}
		return distance;
	}

	@Override
	public Rate<Distance> getVelocity() {
		// not yet implemented
		return null;
	}

	@Override
	public void reset() {
		ic.write(0x00, 0x00);
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
