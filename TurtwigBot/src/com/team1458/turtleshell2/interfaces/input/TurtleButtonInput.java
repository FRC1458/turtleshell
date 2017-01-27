package com.team1458.turtleshell2.interfaces.input;

public interface TurtleButtonInput extends TurtleDigitalInput {
	public default boolean getButton() {
		return (this.get() == 1);
	}
}
