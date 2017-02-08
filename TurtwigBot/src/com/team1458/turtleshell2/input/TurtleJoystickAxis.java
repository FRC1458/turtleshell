package com.team1458.turtleshell2.input;

import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.TurtleMaths.InputFunction;

import edu.wpi.first.wpilibj.Joystick;

public class TurtleJoystickAxis implements AnalogInput {
	private final Joystick masterJoystick;
	private final int axisNum;
	private final boolean isReversed;
	private final boolean positive;
	private final InputFunction func;

	public TurtleJoystickAxis(Joystick j, Joystick.AxisType axis) {
		this(j, axis.value, false);
	}

	public TurtleJoystickAxis(Joystick j, Joystick.AxisType axis, boolean isReversed) {
		this(j, axis.value, isReversed);
	}

	public TurtleJoystickAxis(Joystick j, Joystick.AxisType axis, InputFunction func) {
		this(j, axis.value, false, false, func);
	}

	public TurtleJoystickAxis(Joystick j, int axisNum) {
		this(j, axisNum, false);
	}

	public TurtleJoystickAxis(Joystick j, int axisNum, boolean isReversed) {
		this(j, axisNum, isReversed, false);
	}

	public TurtleJoystickAxis(Joystick j, int axisNum, boolean isReversed, boolean positive) {
		this(j, axisNum, isReversed, positive, InputFunction.identity);
	}

	public TurtleJoystickAxis(Joystick j, int axisNum, boolean isReversed, boolean positive, InputFunction func) {
		this.masterJoystick = j;
		this.axisNum = axisNum;
		this.isReversed = isReversed;
		this.positive = positive;
		this.func = func;
	}

	@Override
	public double get() {
		if (positive) {
			return ((TurtleMaths.reverseBool(isReversed) * func.apply(masterJoystick.getRawAxis(axisNum))) + 1.0) / 2.0;
		}
		return TurtleMaths.reverseBool(isReversed) * func.apply(masterJoystick.getRawAxis(axisNum));
	}

	public TurtleJoystickAxis positive() {
		return new TurtleJoystickAxis(masterJoystick, axisNum, isReversed, true);
	}

}
