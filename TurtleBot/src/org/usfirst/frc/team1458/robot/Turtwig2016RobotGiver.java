package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.core.TurtleAutonomous;
import com.team1458.turtleshell.core.TurtlePhysicalRobot;
import com.team1458.turtleshell.core.TurtleTeleop;
import com.team1458.turtleshell.core.TurtleThingGiver;
import com.team1458.turtleshell.logging.TurtleLogger;

public class Turtwig2016RobotGiver implements TurtleThingGiver {
	private TurtlePhysicalRobot phy = new TurtlePhysicalRobot();
	private TurtleTeleop tel = new TurtwigTestTeleop();
	private TurtleAutonomous aut = new TurtwigTestAutonomous();

	public Turtwig2016RobotGiver() {
		phy.addComponent("Chassis", TurtwigSmartTankChassis.getInstance());
		phy.addComponent("Intake", TurtwigPIDIntake.getInstance());
		//phy.addComponent("Climber", TurtwigClimber2.getInstance());
		phy.addUpdatable("Vision",TurtwigVision.getInstance());
		//TurtleCameraServer.getInstance().setVision(TurtwigVision.getInstance());
		tel.giveRobot(phy);
		aut.giveRobot(phy);
	}

	@Override
	public TurtleTeleop giveTeleop() {
		TurtleLogger.info("Giving teleoperated");
		return tel;
	}

	@Override
	public TurtleAutonomous giveAutonomous() {
		TurtleLogger.info("Giving autonomous");
		return aut;
	}

	@Override
	public TurtlePhysicalRobot givePhysicalRobot() {
		TurtleLogger.info("Giving physicalRobot");
		return phy;
	}
}