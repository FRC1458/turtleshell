package com.team1458.turtleshell2.implementations.input;

import com.team1458.turtleshell2.interfaces.input.TurtleDigitalInput;

import edu.wpi.first.wpilibj.Joystick;

public class TurtleJoystickButton implements TurtleDigitalInput {
	private final Joystick masterJoystick;
	private final int buttonNum;
	
	public TurtleJoystickButton(Joystick j, int buttonNum) {
		this.masterJoystick=j;
		this.buttonNum=buttonNum;
	}
	
	@Override
	public int get() {
		return masterJoystick.getRawButton(buttonNum) ? 1 : 0;//Ternary operator to map true ->1 false ->0
	}
}
