package com.team1458.turtleshell;

public class MotorValue {
	private final double value;

	public MotorValue(double value) {
		this.value = TurtleMaths.fitRange(value, -1, 1);
	}

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
}
