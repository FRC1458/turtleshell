package org.usfirst.frc.team1458.robot;


import com.team1458.turtleshell.core.TurtleRobot;
import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.util.Output;
import com.team1458.turtleshell.util.TurtleSafeDriverStation;

import edu.wpi.first.wpilibj.Timer;


public class Robot extends TurtleRobot {
	private Timer heartbeat = new Timer();

	public Robot() {

	}

	public void initRobot() {
		thingGiver=new Turtwig2016RobotGiver();
		physicalRobot=thingGiver.givePhysicalRobot();
		((TurtwigPIDIntake)physicalRobot.getComponent("Intake")).resetEncoders();
	}

	public void autonomous() {
		// Put the code to initialise autonomous here.
		TurtleLogger.info("Autonomous Starting");
		auto = thingGiver.giveAutonomous();
		physicalRobot.refreshAll();
		((TurtwigPIDIntake)physicalRobot.getComponent("Intake")).resetEncoders();
		auto.doAuto();
	}

	public void operatorControl() {
	    heartbeat.start();
	    physicalRobot.refreshAll();
		// Put the code to initialise operator control here.
		TurtleLogger.info("Teleop starting");
		tele = thingGiver.giveTeleop();
		//((TurtwigPIDIntake)physicalRobot.getComponent("Intake")).resetEncoders();
		while (TurtleSafeDriverStation.canTele()) {
			tele.tick();
			Output.outputNumber("Heartbeat",heartbeat.get());
			if(heartbeat.get()>0.1) {
			    TurtleLogger.warning("Large Heartbeat: "+heartbeat.get());
			}
			heartbeat.reset();
		}
	}

	public void test() {
		// Code for testing mode
	    ((TurtwigPIDIntake)physicalRobot.getComponent("Intake")).resetEncoders();
	}
}