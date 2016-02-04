package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.Output;
import com.team1458.turtleshell.TurtleMaths;
import com.team1458.turtleshell.TurtleMaths.RangeShifter;
import com.team1458.turtleshell.TurtleTheta;
import com.team1458.turtleshell.TurtleThetaCalibration;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;

/**
 * A Xtrinsic Magnetometer, implementation of TurtleTheta. Should be plugged
 * into the I2C port.
 * 
 * @author mehnadnerd
 *
 */
public class TurtleXtrinsicMagnetometer implements TurtleTheta {
	private boolean doDebug = true;
	private double angle;
	private double prevAngle;
	private double rateAngle;
	private Timer rateTimer = new Timer();
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
	private double[] calib = new double[4];

	// minimum and maximum values, set very leniently
	private int xMax = Integer.MIN_VALUE;
	private int yMax = Integer.MIN_VALUE;
	private int xMin = Integer.MAX_VALUE;
	private int yMin = Integer.MAX_VALUE;

	/**
	 * Magnetometer constructor, address is precoded. MAKE SURE TO CALIBRATE
	 * BEFORE USING
	 */
	public TurtleXtrinsicMagnetometer(I2C.Port port) {
		i2c = new I2C(port, address);
		if (i2c == null) {
			System.out.println("Null m_i2c");
		}

		// check to see if the I2C connection is working correctly
		i2c.read(0x07, 1, buffer);
		System.out.println("WHO_AM_I: " + buffer[0]);
		if ((Byte.toUnsignedInt(buffer[0])) != 0xc4) {
			System.out.println("Something has gone terribly wrong.");

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

		this.setCalibration(this.generateCalibration());
		// initial update
		update();
		prevAngle = angle = 0;
		rateTimer.start();
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
		// correct x axis direction
		// inputs[0]=-inputs[0];
		// correct the values
		axes[0] = xShifter.shift(inputs[0]);
		axes[1] = yShifter.shift(inputs[1]);
		// we aren't bothering with the z axis
		axes[2] = inputs[2];
		for (int i = 0; i < axes.length; i++) {
			Output.outputNumber("axes " + i, axes[i]);

		}

		angle = Math.toDegrees(Math.atan2(axes[1], axes[0]));
		angle -= baseAngle;
		rateAngle = rateAngle * .99 + 0.01 * angle;
		// Code for correcting the fact that it is discontinous
		if (angle - prevAngle > 200) {
			// This means it has crossed across the zero line in the
			// anticlockwise direction
			rotations--;
			prevAngle += 360;
			rateAngle += 360;

		} else if (angle - prevAngle < -200) {
			rotations++;
			prevAngle -= 360;
			rateAngle -= 360;
		}
		// code to handle max, min etc. with debug stuff, for calibration and
		// such
		if (doDebug) {
			xMax = TurtleMaths.biggerOf(xMax, inputs[0]);
			yMax = TurtleMaths.biggerOf(yMax, inputs[1]);
			xMin = TurtleMaths.smallerOf(xMin, inputs[0]);
			yMin = TurtleMaths.smallerOf(yMin, inputs[1]);
			Output.outputNumber("xMax", (xMax));
			Output.outputNumber("yMax", (yMax));
			Output.outputNumber("xMin", (xMin));
			Output.outputNumber("yMin", (yMin));
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
		double toRet = (angle - rateAngle) / rateTimer.get();
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

	@Override
	public TurtleThetaCalibration getCalibration() {
		return new TurtleXtrinsicMagnetometerCalibration(calib[0], calib[1], calib[2], calib[3]);
	}

	@Override
	public void setCalibration(TurtleThetaCalibration calibration) {
		calib = calibration.getValues();
		xShifter = new RangeShifter(calib[0], calib[1], -1, 1);
		yShifter = new RangeShifter(calib[2], calib[3], -1, 1);

	}

	@Override
	public TurtleThetaCalibration generateCalibration() {
		return new TurtleXtrinsicMagnetometerCalibration(xMin, xMax, yMin, yMax);
	}

}