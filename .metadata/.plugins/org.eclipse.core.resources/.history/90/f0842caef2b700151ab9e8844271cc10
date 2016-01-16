package org.usfirst.frc.team1458.robot;

import edu.wpi.first.wpilibj.Victor;

public class TurtleVictor implements TurtleMotorInterface {
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
