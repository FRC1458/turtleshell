package com.team1458.turtleshell2.implementations.sensor;

import java.util.TimerTask;

import com.team1458.turtleshell2.interfaces.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.TurtleMaths.RangeShifter;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Rate;
import com.team1458.turtleshell2.util.types.Time;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* THIS SPAWNS A THREAD TO CONTINOUSLY UPDATE THE MAGNETOMETER
 * USE AT OWN RISK
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */
/**
 * A Xtrinsic Magnetometer, implementation of TurtleTheta. Should be plugged
 * into the I2C port.
 * 
 * X axis points towards front of robot, z points up, and y points to right/left
 * 
 * @author mehnadnerd
 */
public class TurtleXtrinsicMagnetometer {
	public static class TurtleXtrinsicMagnetometerCalibration {
		public final double xMin;
		public final double yMin;
		public final double zMin;
		public final double xMax;
		public final double yMax;
		public final double zMax;

		public TurtleXtrinsicMagnetometerCalibration(double xMin, double yMin, double zMin, double xMax, double yMax,
				double zMax) {
			this.xMin = xMin;
			this.yMin = yMin;
			this.zMin = zMin;
			this.xMax = xMax;
			this.yMax = yMax;
			this.zMax = zMax;
		}
	}

	public class TurtleXtrinsicMagnetometerAxis implements TurtleRotationSensor {
		/**
		 * In degrees
		 */
		private double base = 0;
		/**
		 * Index of first linear axis this uses
		 */
		private final int axisA;
		/**
		 * Index of second lineaer axis this uses
		 */
		private final int axisB;

		private TurtleXtrinsicMagnetometerAxis(int axisA, int axisB) {
			this.axisA = axisA;
			this.axisB = axisB;
		}

		private Angle getPrevRotation() {
			return Angle.createDegrees(360 * rotations[getUnusedAxis()]
					+ Math.toDegrees(Math.atan2(prevAxes[axisB], prevAxes[axisA])) - base);
		}

		@Override
		public Angle getRotation() {
			return Angle.createDegrees(
					360 * rotations[getUnusedAxis()] + Math.toDegrees(Math.atan2(axes[axisB], axes[axisA])) - base);
		}

		@Override
		public Rate<Angle> getRate() {
			return new Rate<Angle>(
					Angle.createDegrees(this.getRotation().getValue() - this.getPrevRotation().getValue()),
					new Time(rateTimer.get()));
		}

		@Override
		public void reset() {
			base = this.getRotation().getValue();

		}

		/**
		 * A hacky thing to get which rotations the axis should use
		 * 
		 * @param axisA
		 * @param axisB
		 * @return
		 */
		private final int getUnusedAxis() {
			return 3 - axisA - axisB;
		}

	}

	private class TurtleXtrinsicMagnetometerUpdater extends TimerTask {
		@Override
		public void run() {
			update();
		}

	}

	/*
	 * Class code begins here
	 */
	/**
	 * Amount that if change in one cycle is great than, means has crossover
	 * over zero and so increment or decrement rotations
	 */
	private final static double CROSSOVERTHRESHOLD = 200;

	/**
	 * Time, in ms, that should be between updates
	 */
	private final static long UPDATETIME = 100;

	private final java.util.Timer updateTimer;

	private final TurtleXtrinsicMagnetometerAxis yawAxis = new TurtleXtrinsicMagnetometerAxis(0, 1);
	private final TurtleXtrinsicMagnetometerAxis pitchAxis = new TurtleXtrinsicMagnetometerAxis(0, 2);
	private final TurtleXtrinsicMagnetometerAxis rollAxis = new TurtleXtrinsicMagnetometerAxis(1, 2);

	private boolean doDebug = true;
	private Timer rateTimer = new Timer();

	private I2C i2c;
	/**
	 * Byte array used to read the raw input
	 */
	private byte[] rawInput = new byte[6];
	private final int address = 0x0e;// specific to magnetometer

	/**
	 * Array storring processed int inputs (x, y, z respectively)
	 */
	private int[] inputs = new int[3];
	/**
	 * array storing the magnetic fields, corrected to -1 to 1 (x, y, z
	 * respectively)
	 */
	private double[] axes = new double[3]; // x y z
	/**
	 * Array for how many times has rotated around each axis (yaw, pitch, roll
	 * respectively)
	 */
	private int[] rotations = new int[3];// yaw pitch roll

	/**
	 * Stores the last value of the axes, for rate calculations
	 */
	private double[] prevAxes = new double[3];
	/**
	 * buffer of one byte used for configuration magic.
	 */
	private byte[] buffer = new byte[1];
	// Configuration variables
	private RangeShifter xShifter;
	private RangeShifter yShifter;
	private RangeShifter zShifter;

	// minimum and maximum values, set very leniently
	private int xMax = Integer.MIN_VALUE;
	private int yMax = Integer.MIN_VALUE;
	private int zMax = Integer.MIN_VALUE;
	private int xMin = Integer.MAX_VALUE;
	private int yMin = Integer.MAX_VALUE;
	private int zMin = Integer.MAX_VALUE;

	public TurtleXtrinsicMagnetometer(I2C.Port port, TurtleXtrinsicMagnetometerCalibration calib) {
		updateTimer = new java.util.Timer(true);
		i2c = new I2C(port, address);
		if (i2c == null) {
			System.err.println("Null m_i2c");
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

		this.setCalibration(calib);
		// initial update
		rateTimer.start();
		update();

		// Schedule the java.util.Timer to repeatedly update this sensor
		updateTimer.schedule(new TurtleXtrinsicMagnetometerUpdater(), UPDATETIME, UPDATETIME);
	}

	/**
	 * Magnetometer constructor, address is precoded. MAKE SURE TO CALIBRATE
	 * BEFORE USING
	 */
	public TurtleXtrinsicMagnetometer(I2C.Port port) {
		this(port, new TurtleXtrinsicMagnetometerCalibration(-1, -1, -1, 1, 1, 1));
	}

	/**
	 * This monstrosity converts the byte arrays found through the magnetometer
	 * into the integer. Don't try to understand itß.
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

	/**
	 * Update the magnetometer
	 *
	 * Is a synchronized method, which means that it can only be accessed by one thread at a time.
	 */
	private synchronized void update() {
		// Set old values to new values
		for (int i = 0; i < axes.length; i++) {
			prevAxes[i] = axes[i];
		}
		// do the read
		i2c.read(0x01, 6, rawInput);

		// reads the axes two at a time, so 0 and 1 are combined to inputs[0], 2
		// and 3 to inputs[1], etc.
		for (int i = 0; i < 3; i++) {
			inputs[i] = doubleByteToInt(new byte[] { rawInput[2 * i], rawInput[2 * i + 1] });
		}
		// correct x axis direction
		// inputs[0]=-inputs[0];
		// correct the values
		axes[0] = xShifter.shift(inputs[0]);
		axes[1] = yShifter.shift(inputs[1]);
		axes[2] = zShifter.shift(inputs[2]);
		/*
		 * for (int i = 0; i < axes.length; i++) { SmartDashboard.putNumber(
		 * "axes " + i, axes[i]);
		 * 
		 * }
		 */
		if (TurtleMaths.absDiff(yawAxis.getPrevRotation().getValue(),
				yawAxis.getRotation().getValue()) > TurtleXtrinsicMagnetometer.CROSSOVERTHRESHOLD) {
			if (yawAxis.getPrevRotation().getValue() > yawAxis.getRotation().getValue()) {
				rotations[0]++;
			} else {
				rotations[0]--;
			}
		}
		if (TurtleMaths.absDiff(pitchAxis.getPrevRotation().getValue(),
				pitchAxis.getRotation().getValue()) > TurtleXtrinsicMagnetometer.CROSSOVERTHRESHOLD) {
			if (pitchAxis.getPrevRotation().getValue() > pitchAxis.getRotation().getValue()) {
				rotations[1]++;
			} else {
				rotations[1]--;
			}
		}
		if (TurtleMaths.absDiff(rollAxis.getPrevRotation().getValue(),
				rollAxis.getRotation().getValue()) > TurtleXtrinsicMagnetometer.CROSSOVERTHRESHOLD) {
			if (rollAxis.getPrevRotation().getValue() > rollAxis.getRotation().getValue()) {
				rotations[2]++;
			} else {
				rotations[2]--;
			}
		}
		// code to handle max, min etc. with debug stuff, for calibration and
		// such
		xMax = TurtleMaths.biggerOf(xMax, inputs[0]);
		yMax = TurtleMaths.biggerOf(yMax, inputs[1]);
		zMax = TurtleMaths.biggerOf(zMax, inputs[2]);
		xMin = TurtleMaths.smallerOf(xMin, inputs[0]);
		yMin = TurtleMaths.smallerOf(yMin, inputs[1]);
		zMin = TurtleMaths.smallerOf(zMin, inputs[2]);

		if (doDebug) {

			SmartDashboard.putNumber("xMax", (xMax));
			SmartDashboard.putNumber("yMax", (yMax));
			SmartDashboard.putNumber("zMax", (zMax));
			SmartDashboard.putNumber("xMin", (xMin));
			SmartDashboard.putNumber("yMin", (yMin));
			SmartDashboard.putNumber("zMin", (zMin));

		}
	}

	public void setCalibration(TurtleXtrinsicMagnetometerCalibration calibration) {
		xShifter = new RangeShifter(calibration.xMin, calibration.xMax, -1, 1);
		yShifter = new RangeShifter(calibration.yMin, calibration.yMax, -1, 1);
		zShifter = new RangeShifter(calibration.zMin, calibration.zMax, -1, 1);
	}

	public TurtleXtrinsicMagnetometerCalibration generateCalibration() {
		return new TurtleXtrinsicMagnetometerCalibration(xMin, yMin, zMin, zMax, yMax, zMax);
	}

	public TurtleXtrinsicMagnetometerAxis getYawAxis() {
		return yawAxis;
	}

	public TurtleXtrinsicMagnetometerAxis getPitchAxis() {
		return pitchAxis;
	}

	public TurtleXtrinsicMagnetometerAxis getRollAxis() {
		return rollAxis;
	}
}