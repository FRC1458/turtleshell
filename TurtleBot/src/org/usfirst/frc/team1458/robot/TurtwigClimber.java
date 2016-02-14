package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.TurtleMotor;
import com.team1458.turtleshell.TurtleRobotComponent;
import com.team1458.turtleshell.physical.TurtleVictor;

public class TurtwigClimber implements TurtleRobotComponent {
	TurtleMotor powerWinch = new TurtleVictor(TurtwigConstants.POWERWINCHVICTORPORT,false);
	TurtleMotor hookWinch = new TurtleVictor(TurtwigConstants.HOOKWINCHVICTORPORT,false);

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void teleUpdate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		powerWinch.stop();
		hookWinch.stop();
	}

}
