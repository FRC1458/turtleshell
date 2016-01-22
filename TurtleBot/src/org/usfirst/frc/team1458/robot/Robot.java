package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.TurtleRobot;
import com.team1458.turtleshell.TurtleSafeDriverStation;

public class Robot extends TurtleRobot {
TurtwigTestAutonomous auto = new TurtwigTestAutonomous();
	public Robot() {

	}

	public void initRobot() {
		physicalRobot.addComponent(new TurtwigSmartTankChassis());
	}

	public void autonomous() {
		// Put the code to initialise autonomous here.
<<<<<<< HEAD
		auto = new TurtwigTestAutonomous();
=======
		
>>>>>>> master
		auto.doAuto();
	}

	public void operatorControl() {
		// Put the code to initialise operator control here.
		tele = new TurtwigTestTeleop();
		while (TurtleSafeDriverStation.canTele()) {
			physicalRobot.teleUpdateAll();
			tele.tick();
		}
	}

	public void test() {
		// Code for testing mode
	}
}