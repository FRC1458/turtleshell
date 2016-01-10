package org.usfirst.frc.team1458.robot;

public class Autonomous {
//4 Autos, one for each position of obstacle
	//vision tracking for theta
	
	private static Autonomous instance;
	

	
	private Autonomous() {

	}
	
	public static Autonomous getInstance() {
		if (instance == null) {
			instance = new Autonomous();
		}
		return instance;
	}
	
}
