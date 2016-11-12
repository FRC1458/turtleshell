package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.sensor.TurtleEncoder;

import edu.wpi.first.wpilibj.Encoder;

/**
 * A wrapper for standard encoders to get them to implement TurtleEncoder.
 * @author mehnadnerd
 *
 */
public class TurtleBrokenEncoder implements TurtleEncoder {
	private Encoder encoder;
	private boolean isReversed;
	private double scale;

	public TurtleBrokenEncoder(int aChannel, int bChannel, double scale) {
		encoder = new Encoder(aChannel, bChannel);
		encoder.setDistancePerPulse(1.0);
		this.scale=scale;
	}
	public TurtleBrokenEncoder(int aChannel, int bChannel, boolean isReversed, double scale) {
		this(aChannel, bChannel, scale);
		this.isReversed=isReversed;
	}
	@Override
	public int getTicks() {
		return (int) (scale * (isReversed ? -1 : 1) * encoder.get());
	}

	@Override
	public double getRate() {
		return scale * (isReversed ? -1 : 1) * encoder.getRate();
	}

	@Override
	public void reset() {
		encoder.reset();
	}
	@Override
	public boolean isReversed() {
		return isReversed;
	}
	@Override
	public void update() {

	}
}
