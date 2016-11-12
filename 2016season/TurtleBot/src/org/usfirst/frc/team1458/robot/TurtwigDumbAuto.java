package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.core.TurtleAutonomous;
import com.team1458.turtleshell.core.TurtlePhysicalRobot;
import com.team1458.turtleshell.util.Output;
import com.team1458.turtleshell.util.TurtleSafeDriverStation;

public class TurtwigDumbAuto implements TurtleAutonomous {
	TurtlePhysicalRobot physicalRobot;
	TurtwigSmartTankChassis chassis;

	@Override
	public void doAuto() {
		chassis = (TurtwigSmartTankChassis) physicalRobot
				.getComponent("Chassis");
		chassis.setLinearTarget(120);
		safeDo();
		
		Output.syso("Turtwig did it!");
	}

	@Override
	public void giveRobot(TurtlePhysicalRobot physicalRobot) {
		this.physicalRobot = physicalRobot;

	}

	private void safeDo() {
		while (TurtleSafeDriverStation.canAuto() && !chassis.atTarget()) {
		    physicalRobot.autoUpdateAll();
		}
		chassis.stop();
	}

}