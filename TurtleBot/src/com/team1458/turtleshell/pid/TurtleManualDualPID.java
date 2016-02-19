package com.team1458.turtleshell.pid;

import com.team1458.turtleshell.util.MotorValue;

public class TurtleManualDualPID implements TurtleDualPID {

	private final TurtlePID pid1;
	private final TurtlePID pid2;

	public TurtleManualDualPID(TurtlePID pid1, TurtlePID pid2) {
		this.pid1 = pid1;
		this.pid2 = pid2;
	}

	@Override
	public boolean atTarget() {
		return pid1.atTarget() && pid2.atTarget();
	}

	/**
	 * Array will be split one, two, one, two...
	 */
	@Override
	public MotorValue[] newValue(double[] inputs) {
		double[] inputs1 = new double[inputs.length / 2];
		double[] inputs2 = new double[inputs.length / 2];
		for (int i = 0; i < inputs.length / 2; i++) {
			inputs1[i] = inputs[2 * i];
			inputs2[i] = inputs[2 * i + 1];
		}
		return new MotorValue[] { pid1.newValue(inputs1), pid2.newValue(inputs2) };
	}

}
