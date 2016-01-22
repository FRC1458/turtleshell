package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.TurtlePhysicalRobot;
import com.team1458.turtleshell.TurtleTeleop;

public class TurtwigTestTeleop implements TurtleTeleop {
	TurtlePhysicalRobot physicalRobot;
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void giveRobot(TurtlePhysicalRobot physicalRobot) {
		this.physicalRobot=physicalRobot;
		
	}

}
