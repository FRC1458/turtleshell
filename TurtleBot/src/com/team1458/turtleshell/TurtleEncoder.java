package com.team1458.turtleshell;

public interface TurtleEncoder extends TurtleSensor {
	/**
	 * Gets the ticks an encoder has gone through, signed
	 * 
	 * @return The ticks an encoder has gone through
	 */
	public abstract int getTicks();

	/**
	 * The rate the encoder is ticking, in ticks/second (Hertz)
	 * 
	 * @return The rate in ticks/second an encoder is moving
	 */
	public abstract double getRate();

	/**
	 * Resets the encoder value to zero.
	 */
	public abstract void reset();

	/**
	 * Checks whether or not the Encoder is reversed
	 * 
	 * @return whether or not the encoder is reversed
	 */
	public boolean isReversed();
}
