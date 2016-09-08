package com.team1458.turtleshell2.implementations.pid;

import com.team1458.turtleshell2.interfaces.pid.TurtlePID;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * A PID that does nothing, always returns zero and will always be at target.
 * Useful for disabling pid code.
 * 
 * @author mehnadnerd
 *
 */
public class TurtleZeroPID implements TurtlePID{

	public TurtleZeroPID() {

	}

	/**
	 * Constructor that can take any amount of objects, so can disable more easily
	 * @param o
     */
	public TurtleZeroPID(Object... o) {

	}

	@Override
	public boolean atTarget() {
		return true;
	}

	@Override
	public MotorValue newValue(double[] inputs) {
		return MotorValue.zero;
	}

}
