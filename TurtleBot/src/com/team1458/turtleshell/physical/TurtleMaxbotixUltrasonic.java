package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.TurtleDistance;

import edu.wpi.first.wpilibj.AnalogInput;

public class TurtleMaxbotixUltrasonic implements TurtleDistance {
	AnalogInput sonic;
	
	
	
	@Override
	public double getDistance() {

		//SmartDashboard.putDouble("Ultrasonic Voltage", sonic.getVoltage());
		return (sonic.getVoltage() * 3.47826087) - 0.2;
		
		
		// TODO Auto-generated method stub

	}

	public TurtleMaxbotixUltrasonic(int port){
		sonic = new AnalogInput(port);
	}
	
}
