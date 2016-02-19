package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.core.TurtleRobot;
import com.team1458.turtleshell.physical.TurtleXtrinsicMagnetometer;
import com.team1458.turtleshell.physical.TurtleXtrinsicMagnetometerCalibration;
import com.team1458.turtleshell.sensor.TurtleTheta;
import com.team1458.turtleshell.util.Output;
import com.team1458.turtleshell.util.TurtleSafeDriverStation;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Utility;

public class Robot extends TurtleRobot {
	TurtleTheta maggie;
	

	public Robot() {

	}

	public void initRobot() {
		thingGiver=new Turtwig2016RobotGiver();
		physicalRobot=thingGiver.givePhysicalRobot();
	}

	public void autonomous() {
		// Put the code to initialise autonomous here.
		auto = thingGiver.giveAutonomous();
		auto.doAuto();
	}

	public void operatorControl() {
		// Put the code to initialise operator control here.
		maggie = new TurtleXtrinsicMagnetometer(I2C.Port.kOnboard);
		((TurtleXtrinsicMagnetometer) maggie).setCalibration(new TurtleXtrinsicMagnetometerCalibration(-1614,
				-874, 763, 1649));
		tele = thingGiver.giveTeleop();

		while (TurtleSafeDriverStation.canTele()) {
			tele.tick();
			maggie.update();
			if (Utility.getUserButton()) {
				((TurtleXtrinsicMagnetometer) maggie).setCalibration(((TurtleXtrinsicMagnetometer) maggie).generateCalibration());
			}
			Output.outputNumber("MagAngle", maggie.getContinousTheta());
			Output.outputNumber("MagRate", maggie.getRate());
		}
	}

	public void test() {
		// Code for testing mode
	}
}