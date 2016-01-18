package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.Input;
import com.team1458.turtleshell.TurtleRobot;
import com.team1458.turtleshell.TurtleSafeDriverStation;

public class Robot extends TurtleRobot {

	public Robot() {

	}

	public void robotInit() {
		TurtleSafeDriverStation.setDS(m_ds);
	}

	public void autonomous() {
		// Put the code to initialise autonomous here.
		TurtwigTestAutonomous auto = new TurtwigTestAutonomous();
		auto.doAuto();
	}

	public void operatorControl() {
		// Put the code to initialise operator control here.

		while (isOperatorControl() && isEnabled()) {
			// This is the main loop for operator control.
		}
	}

	public void test() {
		// Code for testing mode
	}
}
