package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.core.TurtleAutonomous;
import com.team1458.turtleshell.core.TurtlePhysicalRobot;
import com.team1458.turtleshell.util.TurtleSafeDriverStation;

public class TurtwigTestAutonomous implements TurtleAutonomous {
	TurtlePhysicalRobot physicalRobot;
	TurtwigSmartTankChassis chassis;

	@Override
	public void doAuto() {
	    while(TurtleSafeDriverStation.canAuto()) {
		physicalRobot.getUpdatable("Vision").update();
	    }
	    /*
		chassis = (TurtwigSmartTankChassis) physicalRobot
				.getComponent("Chassis");
		// chassis.setThetaTarget(90);
		// safeDo();
		int n = 4;
		for (int i = 0; i < n; i++) {
			chassis.setLinearTarget(36);
			safeDo();
			chassis.setThetaTarget(-(360.0 / n));
			safeDo();
		}

		System.out.println("Turtwig did it!");*/
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
