package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.TurtleAutoable;
import com.team1458.turtleshell.TurtleAutonomous;
import com.team1458.turtleshell.TurtleSafeDriverStation;
import com.team1458.turtleshell.TurtleSmartChassis;

public class TurtwigTestAutonomous implements TurtleAutonomous {
	TurtleSmartChassis chassis = new TurtwigSmartTankChassis();
	@Override
	public void doAuto() {
		chassis.setLinearTarget(6*12);
		while (TurtleSafeDriverStation.canAuto()&&!chassis.atTarget()) {
			((TurtleAutoable) chassis).autoUpdate();
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

}
