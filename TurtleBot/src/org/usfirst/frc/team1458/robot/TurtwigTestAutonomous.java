package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.TurtleAutonomous;
import com.team1458.turtleshell.TurtlePhysicalRobot;
import com.team1458.turtleshell.TurtleSafeDriverStation;

public class TurtwigTestAutonomous implements TurtleAutonomous {
	TurtlePhysicalRobot physicalRobot;
	TurtwigSmartTankChassis chassis;

	@Override
	public void doAuto() {
		chassis = (TurtwigSmartTankChassis) physicalRobot.getComponent("Chassis");
		//chassis.setThetaTarget(90);
		//safeDo();
		chassis.setLinearTarget(24);
		safeDo();
		chassis.setThetaTarget(-90);
		safeDo();
		chassis.setLinearTarget(24);
		safeDo();
		chassis.setThetaTarget(90);
		safeDo();
		chassis.setLinearTarget(-24);
		safeDo();
		chassis.setThetaTarget(-90);
		safeDo();
		chassis.setLinearTarget(-24);
		safeDo();
		chassis.setThetaTarget(90);
		safeDo();
		System.out.println("Turtwig did it!");
	}

	@Override
	public void giveRobot(TurtlePhysicalRobot physicalRobot) {
		this.physicalRobot = physicalRobot;

	}
	
	private void safeDo() {
		while (TurtleSafeDriverStation.canAuto() && !chassis.atTarget()) {
			chassis.autoUpdate();
		}
		chassis.stop();
	}

}
