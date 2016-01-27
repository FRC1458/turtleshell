package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.TurtleTheta;

import edu.wpi.first.wpilibj.I2C;

public class TurtleXtrinsicMagnetometer implements TurtleTheta {
	private double angle;
	private double prevAngle;
	private int rotations = 0;
	I2C m_i2c;
	private byte[] rawInput = new byte[6];
	private final int address = 0x0e;// specific to magnetometer
	private double[] axes = new double[3]; // x y z
	byte[] buffer = new byte[1];
	private int xCentre = 0;
	private int yCentre = 0;
	private int xScale = 1;
	private int yScale = 1;
	double xMax = -10000;
	double yMax = -10000;
	double xMin = 10000;
	double yMin = 10000;

	/**
	 * Initialise the magnetometer, should only be run by the constructor.
	 */
	private void initMagnetometer() {
		// checks to see if it is null
		if (m_i2c == null) {
			System.out.println("Null m_i2c");
		}

		// interrogate the buffer to make sure that we are not trying to read
		// the values before they are
		// ready to be read, so we're getting a gibberish value
		m_i2c.read(0x07, 1, buffer);
		if ((int) buffer[0] != 0xc4) {
			System.out.println("Something has gone terribly wrong.");
			System.out.println(buffer[0]);
		} else {
			System.out.println("Found who am i");
		}

		// settings for rate and measuring data
		m_i2c.write(0x10, 0b00011001);
		m_i2c.write(0x11, 0b10000000);

		// calibration (done in code, so set to 0)
		m_i2c.write(0x09, 0b00000000);
		m_i2c.write(0x0a, 0b00000000);
		m_i2c.write(0x0b, 0b00000000);
		m_i2c.write(0x0c, 0b00000000);
		m_i2c.write(0x0d, 0b00000000);
		m_i2c.write(0x0e, 0b00000000);

		// starting values for max and min that should allow for comparison
		// while being easily exceeded
		xMax = -10000;
		yMax = -10000;
		yMin = 10000;
		xMin = 10000;

		// initial update
		calcAngle();
		prevAngle = angle;
		update();
		zero();

	}

	/**
	 * Magnetometer constructor, address is precoded
	 */
	public TurtleXtrinsicMagnetometer() {
		// m_analog = new AnalogChannel(channel);
		m_i2c = new I2C(I2C.Port.kOnboard, address);
		// m_channelAllocated = true;
		initMagnetometer();
	}

	/**
	 * Return the actual angle in degrees that the robot is currently facing.
	 *
	 * The angle is based on the values, it is NOT continuous.
	 *
	 * @return the current heading of the robot in degrees. This heading is
	 *         based on the magnetometer.
	 */
	public double getAngle() {
		update();
		// do math stuff to make more accurate;
		return angle;
	}

	/**
	 * Return the continous angle in degrees the robot is facing. It is
	 * continous.
	 * 
	 * @return Continous heading based on magnetometer
	 */
	public double getContinousAngle() {
		update();
		return angle + (360 * rotations);
	}

	/**
	 * Sets the calibration of the magnetometer
	 * 
	 * @param xC
	 *            amount to subtract from x values to get range centred at 0
	 * @param yC
	 *            amount to subtract from y values to get range centred at 0
	 * @param xS
	 *            amount to divide x values by to get range -1 to 1
	 * @param yS
	 *            amount to divide y values by to get range -1 to 1
	 * @return nothing
	 */
	public void setCalibration(int xC, int yC, int xS, int yS) {
		xCentre = xC;
		yCentre = yC;
		xScale = xS;
		yScale = yS;
		zero();
	}

	/**
	 * Resets the max and minimum values for the SmartDashboard display
	 * 
	 * @return nothing
	 */
	public void zero() {

		xMax = -10000;
		yMax = -10000;
		yMin = 10000;
		xMin = 10000;

	}

	/**
	 * Returns whether the magnetometer is ready to output new data. Currently
	 * set to automatically return true.
	 * 
	 * @return Always true;
	 */

	public boolean isReady() {
		return true;
		// m_i2c.read(0x0, 1, buffer);
		// return (((buffer[0]>>3)&1)==1);
	}

	private void calcAngle() {
		// get values from magnetometer
		m_i2c.read(0x01, 6, rawInput);
		//SmartDashboard.putString("Bytes", rawInput[0]+","+rawInput[1]+","+rawInput[2]+","+rawInput[3]+","+rawInput[4]+","+rawInput[5]);
		// convert it into number format
		// for loop for each axis
		for (int i = 0; i < 3; i++) {
			// sets int to first byte
			int f = (int) rawInput[0 + 2 * i];
			// shifts so room for second byte
			f *= 256;
			// because of how 1st digit in 2nd byte is interpreted as a sign
			// rather than 128, have to have if statement
			if ((int) rawInput[1 + 2 * i] < 0) {
				f += (256 + (int) rawInput[1 + 2 * i]);
			} else {
				f += (int) rawInput[1 + 2 * i];
			}
			// set axis to calculated value
			axes[i] = f;
			// axes[i] = 0.8*f+0.2*axes[i];
		}

		axes[0] /= -1;// flip x to be right

		// calibration
		axes[0] -= xCentre; // maggie 1247, margaret 270
		axes[1] -= yCentre; // maggie 1186, margaret 1025
		// axes[2]-=0;
		axes[0] /= xScale; // maggie 239, margaret 298
		axes[1] /= yScale; // maggie 241, margaret 340
		// axes[2]/=1;
		angle = Math.atan(axes[1] / axes[0]);
		if (axes[0] < 0) {
			angle += Math.PI;
		}
		angle *= (180 / Math.PI);

		angle = Math.round(angle * 10) / 10.0;
		angle += 90;

	}

	/**
	 *  Has the magnetometer calculate all of its values. Needs to be called
	 * each time to update, so should be called in main program loop.
	 * 
	 * Also outputs values to SmartDashboard.
	 * @return nothing
	 */
	@Override
	public void update() {
		prevAngle = angle;
		calcAngle();
		// output the data

		if (xMax < axes[0]) {
			xMax = axes[0];
		}
		if (xMin > axes[0]) {
			xMin = axes[0];
		}
		if (yMax < axes[1]) {
			yMax = axes[1];
		}
		if (yMin > axes[1]) {
			yMin = axes[1];
		}



		// assuming that positive x is to the right of the robot, positive y is
		// straight forward
		// rotation count code
		if (prevAngle - angle > 200) {
			rotations++;
		} else if (prevAngle - angle < -200) {
			rotations--;
		}

	}
	@Override
	public double getContinousTheta() {
		return this.angle;
	}

	@Override
	public double getRate() {
		return 0;
	}

	@Override
	public void reset() {
		this.angle=0;
	}

}
