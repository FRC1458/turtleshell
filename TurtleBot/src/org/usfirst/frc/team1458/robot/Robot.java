package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.Output;
import com.team1458.turtleshell.TurtleRobot;
import com.team1458.turtleshell.TurtleSafeDriverStation;
import com.team1458.turtleshell.TurtleTheta;
import com.team1458.turtleshell.physical.TurtleXtrinsicMagnetometer;
import com.team1458.turtleshell.physical.TurtleXtrinsicMagnetometerCalibration;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Utility;

public class Robot extends TurtleRobot {
	TurtleTheta maggie;
	

	public Robot() {

	}

	public void initRobot() {
		thingGiver=new TurtwigThingGiver();
		physicalRobot=thingGiver.givePhysicalRobot();
		updateBlob=thingGiver.giveUpdatableBlob();
	}

	public void autonomous() {
		// Put the code to initialise autonomous here.
		auto = thingGiver.giveAutonomous();
		auto.doAuto();
	}

	public void operatorControl() {
		// Put the code to initialise operator control here.
		maggie = new TurtleXtrinsicMagnetometer(I2C.Port.kOnboard);
		maggie.setCalibration(new TurtleXtrinsicMagnetometerCalibration(-1614,
				-874, 763, 1649));
		tele = new TurtwigTestTeleop();
		tele.giveRobot(physicalRobot);

		while (TurtleSafeDriverStation.canTele()) {
			doUpdates();
			maggie.update();
			if (Utility.getUserButton()) {
				maggie.setCalibration(maggie.generateCalibration());
			}
			Output.outputNumber("MagAngle", maggie.getContinousTheta());
			Output.outputNumber("MagRate", maggie.getRate());
		}
	}

	public void test() {
		// Code for testing mode
	}
}
