package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.TurtleSolenoid;

import edu.wpi.first.wpilibj.Relay;

public class TurtleElectricalSolenoid implements TurtleSolenoid {
	private Relay r;
	private boolean ex;

	public TurtleElectricalSolenoid(int port) {
		r = new Relay(port);
	}

	@Override
	public void set(boolean isExtended) {
		ex = isExtended;
		r.set(isExtended ? Relay.Value.kOn : Relay.Value.kOff);
	}

	@Override
	public boolean get() {
		return ex;
	}

}
