package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.sensor.TurtleEncoder;
import com.team1458.turtleshell.sensor.TurtleLimitSwitch;

public class TurtleEncoderLimit implements TurtleLimitSwitch {
	private TurtleEncoder encoder;
	private double limit;
	private boolean direction;

	/**
	 * 
	 * @param encoder
	 * @param limit
	 * @param direction
	 *            true if worried about being over, false if worried about being
	 *            under
	 */
	public TurtleEncoderLimit(TurtleEncoder encoder, double limit, boolean direction) {
		this.encoder = encoder;
		this.limit = limit;
		this.direction = direction;

	}

	@Override
	public void update() {
		// nothing to do

	}

	@Override
	public boolean isPressed() {
		return (direction ? encoder.getTicks() >= limit : encoder.getTicks() <= limit);
	}

}
