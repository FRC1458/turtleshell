package com.team1458.turtleshell2.util.types;

import com.team1458.turtleshell2.util.TurtleMaths;

/**
 * A class to move around motorValues. They are internally backed by a double,
 * but the class restricts it to -1 to 1, because those are the limits on the
 * range. Immutable.
 * 
 * @author mehnadnerd
 *
 */
public final class MotorValue implements Comparable<MotorValue>, Unit, Mathable<MotorValue> {
	private final double value;
	public static final MotorValue zero = new MotorValue(0);
	public static final MotorValue fullForward = new MotorValue(1);
	public static final MotorValue fullBackward = new MotorValue(-1);

	public MotorValue(double value) {
		this.value = TurtleMaths.fitRange(value, -1, 1);
	}

	/**
	 * Get the double associated with this MotorValue
	 * 
	 * @return The double that this motorValue holds
	 */
	@Override
	public double getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "MotorValue: " + value;
	}

	@Override
	public boolean equals(Object o) {
		if (o.getClass().isInstance(this)) {
			if (((MotorValue) o).getValue() == value) {
				return true;
			}
		}
		return false;
	}

	@Override
	public int compareTo(MotorValue o) {
		return new Double(this.getValue()).compareTo(o.getValue());
	}

	public MotorValue invert() {
		return this.scale(-1);
	}

	public MotorValue mapToSpeed(MotorValue speed) {
		return this.scale(speed.getValue());
	}

	public MotorValue halve() {
		return this.scale(0.5);
	}

	@Override
	public MotorValue plus(MotorValue toAdd) {
		return new MotorValue(this.getValue() + toAdd.getValue());
	}

	@Override
	public MotorValue minus(MotorValue toSubtract) {
		return new MotorValue(this.getValue() - toSubtract.getValue());
	}

	@Override
	public MotorValue scale(double scalar) {
		return new MotorValue(this.getValue() * scalar);
	}
}
