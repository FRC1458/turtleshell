package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.interfaces.ObjectHolder;

import edu.wpi.first.wpilibj.SampleRobot;

public class Robot extends SampleRobot {

	private ObjectHolder objholder;

	protected void robotInit() {
		objholder = new BlastoiseRobot();
	}

	protected void disabled() {
		System.out.println("Default disabled() method running, consider providing your own");
	}

	public void autonomous() {
		objholder.getAuto().auto();
	}

	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			objholder.teleUpdateAll();
		}
	}

	public void test() {
		objholder.getTest().test();
	}
}
