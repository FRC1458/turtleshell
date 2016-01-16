package org.usfirst.frc.team1458.robot;

import edu.wpi.first.wpilibj.Talon;

public class TurtleTalon implements TurtleMotorInterface {
	private Talon talon;
	public TurtleTalon(int port) {
		talon=new Talon(port);
	}
	@Override
	public void set(double power) {
		talon.set(power);
		
	}

	@Override
	public double get() {
		return talon.get();
	}
}
