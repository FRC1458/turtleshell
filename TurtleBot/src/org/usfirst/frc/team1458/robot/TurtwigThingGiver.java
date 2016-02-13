package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.TurtleAutonomous;
import com.team1458.turtleshell.TurtlePhysicalRobot;
import com.team1458.turtleshell.TurtleTeleop;
import com.team1458.turtleshell.TurtleThingGiver;
import com.team1458.turtleshell.TurtleUpdatableBlob;

public class TurtwigThingGiver implements TurtleThingGiver {
	private TurtlePhysicalRobot phy = new TurtlePhysicalRobot();
	private TurtleTeleop tel = new TurtwigTestTeleop();
	private TurtleAutonomous aut = new TurtwigTestAutonomous();
	private TurtleUpdatableBlob blo = new TurtleUpdatableBlob();

	public TurtwigThingGiver() {
		phy.addComponent("Chassis", new TurtwigSmartTankChassis());
		tel.giveRobot(phy);
		aut.giveRobot(phy);
	}

	@Override
	public TurtleTeleop giveTeleop() {
		return tel;
	}

	@Override
	public TurtleAutonomous giveAutonomous() {
		return aut;
	}

	@Override
	public TurtlePhysicalRobot givePhysicalRobot() {
		return phy;
	}

	@Override
	public TurtleUpdatableBlob giveUpdatableBlob() {
		return blo;
	}
}