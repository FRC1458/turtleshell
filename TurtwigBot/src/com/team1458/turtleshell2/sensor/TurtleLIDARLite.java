package com.team1458.turtleshell2.sensor;

import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.Rate;

import edu.wpi.first.wpilibj.I2C;

/**
 * LIDARLite v3 Sensor over I2C interface.
 * 
 * @author mehnadnerd
 *
 */
public class TurtleLIDARLite implements TurtleDistanceSensor {
	private final I2C ic;

	public TurtleLIDARLite(I2C.Port port) {
		ic = new I2C(port, 0x62);

		// Configure Sensor. See
		// http://static.garmin.com/pumac/LIDAR_Lite_v3_Operation_Manual_and_Technical_Specifications.pdf
		ic.write(0x02, 0x80);
		ic.write(0x04, 0x08);
		ic.write(0x1c, 0x00);

	}

	@Override
	public Distance getDistance() {
		byte[] h = { 0x00 };
		byte[] l = { 0x00 };
		if (ic.read(0x0f, 1, h)) {// mutates h
			if (ic.read(0x10, 1, l)) {// mutates l
				return Distance.createCentimetres(doubleByteToInt(new byte[] { h[0], l[0] }));
			}

		} // transaction failed
		return Distance.error;
	}

	@Override
	public Rate<Distance> getVelocity() {
		// not yet implemented
		return null;
	}

	@Override
	public void reset() {
		throw new UnsupportedOperationException("This is a distance sensor; it doesn't move the robot.");
	}

	/**
	 * This monstrosity converts the byte arrays found through the magnetometer
	 * into the integer. Don't try to understand it.
	 * 
	 * @author mehnadnerd - but don't try to get me to fix it
	 * 
	 * @param byteArray
	 *            MSB, LSB
	 * @return The int representing the two combined, as in signed two's
	 *         complement format
	 */
	private int doubleByteToInt(byte[] byteArray) {
		return (int) byteArray[0] > 0 ? (byteArray[0] * 256 + Byte.toUnsignedInt(byteArray[1]))
				: Byte.toUnsignedInt(byteArray[0]) * 256 + Byte.toUnsignedInt(byteArray[1]) - 65536;
	}
}
