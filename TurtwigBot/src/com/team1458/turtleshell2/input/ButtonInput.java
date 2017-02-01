package com.team1458.turtleshell2.input;

public interface ButtonInput extends DigitalInput {
	public default boolean getButton() {
		return (this.get() == 1);
	}

	public boolean getDown();
	public boolean getUp();
}
