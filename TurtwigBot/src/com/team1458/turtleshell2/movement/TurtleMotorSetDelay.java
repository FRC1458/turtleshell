package com.team1458.turtleshell2.movement;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.team1458.turtleshell2.util.types.MotorValue;

public class TurtleMotorSetDelay implements TurtleMotor {
	private static final long delayMillis = 100;

	private ArrayList<DelayMotor> motors = new ArrayList<>();
	private MotorValue val = MotorValue.zero;
	private MotorValue prev = MotorValue.zero;

	public TurtleMotorSetDelay(TurtleMotor... motors) {
		for (int i = 0; i < motors.length; i++) {
			this.motors.add(new DelayMotor(motors[i], i * delayMillis));
		}
	}

	@Override
	public void set(MotorValue val) {
		this.prev = this.val;
		this.val = val;
		if (Math.signum(val.getValue()) != Math.signum(prev.getValue())) {
			for (DelayMotor m : motors) {
				m.delaySet(val);
			}
		} else {
			for (DelayMotor m : motors) {
				m.directSet(val);
			}
		}

	}

	@Override
	public MotorValue get() {
		return val;
	}

	private final static class DelayMotor implements TurtleMotor {
		private final TurtleMotor m;
		private final long delay;
		private MotorValue val;

		DelayMotor(TurtleMotor m, long delay) {
			this.m = m;
			this.delay = delay;
		}

		@Override
		public void set(MotorValue val) {
			this.directSet(val);
		}

		public void delaySet(MotorValue val) {
			this.val = val;
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					m.set(val);
				}
			}, delay);

		}

		public void directSet(MotorValue val) {
			this.val = val;
			m.set(val);
		}

		@Override
		public MotorValue get() {
			return val;
		}

	}

}
