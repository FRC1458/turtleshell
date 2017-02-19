package com.team1458.turtleshell2.util.types;

/**
 * A class representing immutable Tuple with an X and Y, useful for returning
 * two values
 * 
 * @author mehnadnerd
 *
 * @param <X>
 *            The first type
 * @param <Y>
 *            The second type
 */
public class Tuple<X, Y> {
	public final X x;
	public final Y y;

	public Tuple(X x, Y y) {
		this.x = x;
		this.y = y;
	}

	public X getX() {
		return x;
	}

	public Y getY() {
		return y;
	}
}
