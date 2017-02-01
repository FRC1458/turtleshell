package com.team1458.turtleshell2.input;

import com.team1458.turtleshell2.util.TurtleMaths;

import edu.wpi.first.wpilibj.Joystick;

public class TurtleJoystickAxis implements AnalogInput {
	private final Joystick masterJoystick;
	private final int axisNum;
	private final boolean isReversed;
	private final boolean positive;

	public TurtleJoystickAxis(Joystick j, Joystick.AxisType axis) {
		this(j, axis.value, false);
	}

	public TurtleJoystickAxis(Joystick j, int axisNum, boolean isReversed) {
		this.masterJoystick = j;
		this.axisNum = axisNum;
		this.isReversed = isReversed;
		positive = false;
	}

	public TurtleJoystickAxis(Joystick j, int axisNum, boolean isReversed, boolean positive) {
		this.masterJoystick = j;
		this.axisNum = axisNum;
		this.isReversed = isReversed;
		this.positive = positive;
	}
	
	public TurtleJoystickAxis(Joystick j, int axisNum) {
		this(j,axisNum,false);
	}

	@Override
	public double get() {
		if(positive) return ((TurtleMaths.reverseBool(isReversed) * masterJoystick.getRawAxis(axisNum)) + 1.0) / 2.0;
		return TurtleMaths.reverseBool(isReversed)*masterJoystick.getRawAxis(axisNum);
	}

	public TurtleJoystickAxis positive() {
		return new TurtleJoystickAxis(masterJoystick, axisNum, isReversed, true);
	}

}
