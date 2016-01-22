package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.TurtleAutoable;
import com.team1458.turtleshell.TurtleAutonomous;
import com.team1458.turtleshell.TurtlePhysicalRobot;
import com.team1458.turtleshell.TurtleSafeDriverStation;
import com.team1458.turtleshell.TurtleSmartChassis;

public class TurtwigTestAutonomous implements TurtleAutonomous {
	TurtlePhysicalRobot physicalRobot;

	@Override
	public void doAuto() {
<<<<<<< HEAD
		((TurtleSmartChassis) physicalRobot.getComponent(TurtleSmartChassis.class).get(0)).setLinearTarget(36);
		while (TurtleSafeDriverStation.canAuto()
				&& !((TurtleSmartChassis) physicalRobot.getComponent(TurtleSmartChassis.class).get(0)).atTarget()) {
			((TurtleAutoable) ((TurtleSmartChassis) physicalRobot.getComponent(TurtleSmartChassis.class).get(0)))
					.autoUpdate();
=======
		chassis.setLinearTarget(6*12);
		while (TurtleSafeDriverStation.canAuto()&&!chassis.atTarget()) {
			((TurtleAutoable) chassis).autoUpdate();
>>>>>>> master
		}
		chassis.setLinearTarget(-3*12);
		while (TurtleSafeDriverStation.canAuto()&&!chassis.atTarget()) {
			((TurtleAutoable) chassis).autoUpdate();
		}
		chassis.setLinearTarget(4*12);
		while (TurtleSafeDriverStation.canAuto()&&!chassis.atTarget()) {
			((TurtleAutoable) chassis).autoUpdate();
		}
		
		System.out.println("Turtwig did it!");
		chassis.stop();
	}

	@Override
	public void giveRobot(TurtlePhysicalRobot physicalRobot) {
		this.physicalRobot=physicalRobot;
		
	}

}
