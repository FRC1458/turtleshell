package com.team1458.turtleshell2.input;

public interface TurtleButtonInput extends TurtleDigitalInput {
	public default boolean getButton() {
		return (this.get() == 1);
	}

	public boolean getDown();
	public boolean getUp();
}
