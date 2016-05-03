package com.team1458.turtleshell2.implementations.input;

import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.util.TurtleMaths;

import edu.wpi.first.wpilibj.Joystick;

public class TurtleJoystickAxis implements TurtleAnalogInput {
	private final Joystick masterJoystick;
	private final int axisNum;
	private final boolean isReversed;

	public TurtleJoystickAxis(Joystick j, Joystick.AxisType axis) {
		this(j, axis.value, false);
	}

	public TurtleJoystickAxis(Joystick j, int axisNum, boolean isReversed) {
		this.masterJoystick = j;
		this.axisNum = axisNum;
		this.isReversed=isReversed;
	}
	
	public TurtleJoystickAxis(Joystick j, int axisNum) {
		this(j,axisNum,false);
	}

	@Override
	public double get() {
		return TurtleMaths.reverseBool(isReversed)*masterJoystick.getRawAxis(axisNum);
	}

}
