package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.TurtleDistance;

import edu.wpi.first.wpilibj.AnalogInput;

public class TurtleMaxbotixUltrasonic implements TurtleDistance {
	AnalogInput sonic;
	
	
	/** 
	 *getDistance=
	 *(hopefully) gets the distance between Metal Sonic(the ultrasonic sensor) 
	 *and the nearest object in the direction in which the sensor is facing.
	 *Oh, by the way, its all in decimal inches. HAVE FUN! ;)
	 *~Valorzard Last edited 1/22/16
	 */
	@Override
	public double getDistance() {

		//SmartDashboard.putDouble("Ultrasonic Voltage", sonic.getVoltage());
		return (sonic.getVoltage()*42.946-0.4437);
		
		
		// TODO Auto-generated method stub

	}

	public TurtleMaxbotixUltrasonic(int port){
		sonic = new AnalogInput(port);
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
}
