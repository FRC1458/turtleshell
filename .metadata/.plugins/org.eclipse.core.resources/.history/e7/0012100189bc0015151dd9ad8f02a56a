package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.TurtleEncoder;

import edu.wpi.first.wpilibj.Encoder;

public class Turtle4PinEncoder extends TurtleEncoder {
	private Encoder encoder;

	public Turtle4PinEncoder(int aChannel, int bChannel) {
		encoder = new Encoder(aChannel, bChannel);
		encoder.setDistancePerPulse(1.0);
	}
	public Turtle4PinEncoder(int aChannel, int bChannel, boolean isReversed) {
		this(aChannel, bChannel);
		this.isReversed=isReversed;
	}
	@Override
	public int getTicks() {
		return (isReversed ? -1 : 1) * encoder.get();
	}

	@Override
	public double getRate() {
		return (isReversed ? -1 : 1) * encoder.getRate();
	}

	@Override
	public void reset() {
		encoder.reset();
	}
}
