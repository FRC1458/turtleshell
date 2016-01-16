package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.TurtleTankChassis;
import com.team1458.turtleshell.TurtleVictor;

public class RedTies2016Chassis implements TurtleTankChassis {
private static RedTies2016Chassis instance;
	private TurtleVictor rDrive1;
	private TurtleVictor lDrive1;
	private TurtleVictor rDrive2;
	private TurtleVictor lDrive2;
	
	private RedTies2016Chassis() {
		rDrive1=new TurtleVictor(Constants.RIGHT1VICTORPORT);
		lDrive1=new TurtleVictor(Constants.LEFT1VICTORPORT);
		rDrive2=new TurtleVictor(Constants.RIGHT2VICTORPORT);
		lDrive2=new TurtleVictor(Constants.LEFT2VICTORPORT);
	}
	
	public static RedTies2016Chassis getInstance() {
		if (instance == null) {
			instance = new RedTies2016Chassis();
		}
		return instance;
	}

	@Override
	public void rightDrive(double power) {
		rDrive1.set(power);
		rDrive2.set(power);
	}

	@Override
	public void leftDrive(double power) {
		lDrive1.set(power);
		lDrive2.set(power);
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
}
