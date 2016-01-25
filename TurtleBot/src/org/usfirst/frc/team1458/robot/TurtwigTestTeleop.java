package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.Output;
import com.team1458.turtleshell.TurtleDistance;
import com.team1458.turtleshell.TurtlePhysicalRobot;
import com.team1458.turtleshell.TurtleTeleop;
import com.team1458.turtleshell.physical.TurtleMaxbotixUltrasonic;

public class TurtwigTestTeleop implements TurtleTeleop {
	TurtlePhysicalRobot physicalRobot;
	TurtleDistance metalSonic = new TurtleMaxbotixUltrasonic(0);
	double distance = 0;
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		physicalRobot.teleUpdateAll();
		distance = metalSonic.getDistance(); 
		
		Output.outputNumber("distance =", distance);
	}

	@Override
	public void giveRobot(TurtlePhysicalRobot physicalRobot) {
		this.physicalRobot=physicalRobot;
		
	}

}
