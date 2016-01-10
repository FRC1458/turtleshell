package org.usfirst.frc.team1458.robot;

public class Chassis {
private static Chassis instance;

	
	private Chassis() {

	}
	
	public static Chassis getInstance() {
		if (instance == null) {
			instance = new Chassis();
		}
		return instance;
	}
}
