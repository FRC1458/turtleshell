package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.Output;
import com.team1458.turtleshell.TurtleRobot;
import com.team1458.turtleshell.TurtleSafeDriverStation;
import com.team1458.turtleshell.TurtleTheta;
import com.team1458.turtleshell.physical.TurtleXtrinsicMagnetometer;

import edu.wpi.first.wpilibj.I2C;

public class Robot extends TurtleRobot {
	TurtleXtrinsicMagnetometer maggie;
	public Robot() {

	}

	public void initRobot() {
		physicalRobot.addComponent("Chassis", new TurtwigSmartTankChassis());
	}

	public void autonomous() {
		// Put the code to initialise autonomous here.
		auto = new TurtwigTestAutonomous();
		auto.giveRobot(physicalRobot);
		auto.doAuto();

	}

	public void operatorControl() {
		// Put the code to initialise operator control here.
		maggie = new TurtleXtrinsicMagnetometer(I2C.Port.kOnboard);
		maggie.setCalibration(-1, 1, -1, 1);
		tele = new TurtwigTestTeleop();
		tele.giveRobot(physicalRobot);
		
		while (TurtleSafeDriverStation.canTele()) {
			physicalRobot.teleUpdateAll();
			//tele.tick();
			maggie.update();
			Output.outputNumber("MagAngle", maggie.getContinousTheta());
			Output.outputNumber("MagRate", maggie.getRate());
		}
	}

	public void test() {
		// Code for testing mode
	}
}
