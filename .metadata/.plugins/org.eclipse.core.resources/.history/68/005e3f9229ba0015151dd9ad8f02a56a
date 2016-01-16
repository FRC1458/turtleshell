package com.team1458.turtleshell;

import edu.wpi.first.wpilibj.Victor;

public class TurtleVictor implements TurtleMotor {
	private Victor victor;
	
	public TurtleVictor(int port) {
		victor=new Victor(port);
	}

	@Override
	public void set(double power) {
		victor.set(power);
	}

	@Override
	public double get() {
		return victor.get();
	}
}
