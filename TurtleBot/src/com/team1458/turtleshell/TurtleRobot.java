package com.team1458.turtleshell;

public abstract class TurtleRobot extends TurtleAdvancedRobot {
	protected TurtleChassis chassis;
	protected TurtleAutonomous auto;
	
	@Override
	public abstract void robotInit();
	
	@Override
	public abstract void autonomous();
	
	@Override
	public abstract void operatorControl();
}
