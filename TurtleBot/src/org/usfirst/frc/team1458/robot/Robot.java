package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.TurtleRobot;
import com.team1458.turtleshell.TurtleSafeDriverStation;

public class Robot extends TurtleRobot {

	public Robot() {

	}

	public void initRobot() {
		physicalRobot.addComponent("Chassis",new TurtwigSmartTankChassis());
	}

	public void autonomous() {
		// Put the code to initialise autonomous here.
		auto = new TurtwigTestAutonomous();
		auto.giveRobot(physicalRobot);
		auto.doAuto();
	}

	public void operatorControl() {
		// Put the code to initialise operator control here.
		//tele = new TurtwigTestTeleop();
		while (TurtleSafeDriverStation.canTele()) {
			//tele.tick();
			physicalRobot.getComponent("Chassis").teleUpdate();
		}
	}

	public void test() {
		// Code for testing mode
	}
}