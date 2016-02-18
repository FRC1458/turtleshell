package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.TurtleSolenoid;

import edu.wpi.first.wpilibj.DigitalOutput;

public class TurtleElectricalSolenoid implements TurtleSolenoid {
	private DigitalOutput d;
	private boolean ex;
	
	public TurtleElectricalSolenoid(int port) {
		d = new DigitalOutput(port);
	}
	
	@Override
	public void set(boolean isExtended) {
		ex=isExtended;
		d.set(isExtended);
	}

	@Override
	public boolean get() {
		return ex;
	}

}
