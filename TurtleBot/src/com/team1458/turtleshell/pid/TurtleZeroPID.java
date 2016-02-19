package com.team1458.turtleshell.pid;

import com.team1458.turtleshell.util.MotorValue;

/**
 * A PID that does nothing, always returns zero and will always be at target.
 * Used for disabling motor in TurtleManualDualPID.
 * 
 * @author mehnadnerd
 *
 */
public class TurtleZeroPID implements TurtlePID{
	private static TurtleZeroPID instance;
	public static TurtleZeroPID getInstance() {
		if(instance==null) {
			instance = new TurtleZeroPID();
		}
		return instance;
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
