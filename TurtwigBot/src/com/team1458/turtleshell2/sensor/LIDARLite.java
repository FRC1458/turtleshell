package com.team1458.turtleshell2.sensor;

import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.Rate;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * LIDARLite v3 Sensor over I2C interface.
 * 
 * @author mehnadnerd
 *
 */
public class LIDARLite implements TurtleDistanceSensor {
	private final I2C ic;

	public LIDARLite(I2C.Port port) {
		ic = new I2C(port, 0x62);

		// Configure Sensor. See
		// http://static.garmin.com/pumac/LIDAR_Lite_v3_Operation_Manual_and_Technical_Specifications.pdf
		ic.write(0x02, 0x80);
		ic.write(0x04, 0x08);
		ic.write(0x1c, 0x00);

	}

	@Override
	public Distance getDistance() {
		byte[] high = new byte[]{0x00};
		byte[] low = new byte[]{0x00};
		byte[] status = { Byte.MAX_VALUE }; // All bits are 1

		// This is the command for taking distance measurement with bias correction
		ic.write(0x00, 0x04);


		// TODO make this not fail and get stuck in a loop
		while ((status[0] & 0b0000_0001) == 0b0000_0001) { // need to check if this is correct for LSB
			ic.read(0x01, 1, status); // check status
			SmartDashboard.putString("Status", Integer.toBinaryString(status[0]));
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
		return (int) byteArray[0] > 0 ? (byteArray[0] * 256 + Byte.toUnsignedInt(byteArray[1]))
				: Byte.toUnsignedInt(byteArray[0]) * 256 + Byte.toUnsignedInt(byteArray[1]) - 65536;
	}
}
