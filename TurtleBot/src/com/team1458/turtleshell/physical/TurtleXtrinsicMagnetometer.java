package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.Output;
import com.team1458.turtleshell.TurtleMaths;
import com.team1458.turtleshell.TurtleMaths.RangeShifter;
import com.team1458.turtleshell.TurtleTheta;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;

public class TurtleXtrinsicMagnetometer implements TurtleTheta {
	private boolean doDebug = true;
	private double angle;
	private double prevAngle;
	private Timer rateTimer;
	private int rotations = 0;

	private double baseAngle;

	private I2C i2c;
	/**
	 * Byte array used to read the raw input
	 */
	private byte[] rawInput = new byte[6];
	private final int address = 0x0e;// specific to magnetometer

	private int[] inputs = new int[3];
	/**
	 * array storing the magnetic fields, corrected to -1 to 1
	 */
	private double[] axes = new double[3]; // x y z
	/**
	 * buffer of one byte used for some configuration things.
	 */
	private byte[] buffer = new byte[1];
	// Configuration variables
	private RangeShifter xShifter;
	private RangeShifter yShifter;

	// minimum and maximum values, set very leniently
	private double xMax = Double.MIN_VALUE;
	private double yMax = Double.MIN_VALUE;
	private double xMin = Double.MAX_VALUE;
	private double yMin = Double.MAX_VALUE;

	/**
	 * Magnetometer constructor, address is precoded. MAKE SURE TO CALIBRATE IT FIRST
	 */
	public TurtleXtrinsicMagnetometer(I2C.Port port) {
		i2c = new I2C(port, address);
		if (i2c == null) {
			System.out.println("Null m_i2c");
		}

		// check to see if the I2C connection is working correctly
		i2c.read(0x07, 1, buffer);
		if ((int) buffer[0] != 0xc4) {
			System.out.println("Something has gone terribly wrong.");
			System.out.println(buffer[0]);
		} else {
			System.out.println("Found WHO_AM_I");
		}

		// settings for rate and measuring data
		i2c.write(0x10, 0b00011001);
		i2c.write(0x11, 0b10000000);

		// calibration (done in code, so set to 0)
		i2c.write(0x09, 0b00000000);
		i2c.write(0x0a, 0b00000000);
		i2c.write(0x0b, 0b00000000);
		i2c.write(0x0c, 0b00000000);
		i2c.write(0x0d, 0b00000000);
		i2c.write(0x0e, 0b00000000);

		// initial update
		update();
		prevAngle = angle;
		rateTimer.start();
	}

	public void setCalibration(double lMin, double lMax, double rMin, double rMax) {
		xShifter = new RangeShifter(lMin, lMax, -1, 1);
		yShifter = new RangeShifter(rMin, rMax, -1, 1);
	}

	/**
	 * This monstrosity converts the byte arrays found through the magnetometer
	 * into the integer. Don't try to understand it.
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

	@Override
	public void update() {
		prevAngle = angle;
		// do the read
		i2c.read(0x01, 6, rawInput);
		for (int i = 0; i < 3; i++) {
			inputs[i] = doubleByteToInt(new byte[] { rawInput[2 * i], rawInput[2 * i + 1] });
		}
		// correct the values
		axes[0] = xShifter.shift(inputs[0]);
		axes[1] = yShifter.shift(inputs[1]);
		// we aren't bothering with the z axis
		axes[2] = inputs[2];

		angle = Math.toDegrees(Math.atan2(axes[1], axes[0]));
		angle -= baseAngle;
		// Code for correcting the fact that it is discontinous
		if (angle - prevAngle > 200) {
			// This means it has crossed across the zero line in the
			// anticlockwise direction
			rotations--;
			prevAngle += 360;
		} else if (angle - prevAngle < -200) {
			rotations++;
			prevAngle -= 360;
		}
		//code to handle max, min etc. with debug stuff, for calibration and such
		if(doDebug) {
			xMax=TurtleMaths.biggerOf(xMax, axes[0]);
			yMax=TurtleMaths.biggerOf(yMax, axes[1]);
			xMin=TurtleMaths.smallerOf(xMin, axes[0]);
			yMin=TurtleMaths.smallerOf(yMin, axes[1]);
			Output.outputNumber("xMax", xMax);
			Output.outputNumber("yMax", yMax);
			Output.outputNumber("xMin", xMin);
			Output.outputNumber("yMin", yMin);
		}
	}

	@Override
	public double getContinousTheta() {
		this.update();
		return angle + (360 * rotations);
	}

	@Override
	public double getRate() {
		this.update();
		double toRet = (angle - prevAngle) / rateTimer.get();
		rateTimer.reset();
		return toRet;
	}

	@Override
	public void reset() {
		baseAngle = angle;
		prevAngle = angle;
		this.update();
		rateTimer.reset();
	}

}
