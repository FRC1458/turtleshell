package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.TurtleEncoder;
import com.team1458.turtleshell.TurtleSmartTankChassis;
import com.team1458.turtleshell.physical.Turtle4PinEncoder;
import com.team1458.turtleshell.physical.TurtleVictor;

public class RedTies2015Chassis implements TurtleSmartTankChassis {
	private static RedTies2015Chassis instance;
	private TurtleVictor rDrive1;
	private TurtleVictor lDrive1;
	//private TurtleVictor rDrive2;
	//private TurtleVictor lDrive2;

	private TurtleEncoder lEncoder;
	private TurtleEncoder rEncoder;

	private RedTies2015Chassis() {
		rDrive1 = new TurtleVictor(Constants.RIGHT1VICTORPORT, true);
		lDrive1 = new TurtleVictor(Constants.LEFT1VICTORPORT, false);
		//rDrive2 = new TurtleVictor(Constants.RIGHT2VICTORPORT, true);
		//lDrive2 = new TurtleVictor(Constants.LEFT2VICTORPORT, false);

		lEncoder = new Turtle4PinEncoder(Constants.LEFTENCODERPORT1, Constants.LEFTENCODERPORT2, false);
		rEncoder = new Turtle4PinEncoder(Constants.RIGHTENCODERPORT1, Constants.RIGHTENCODERPORT2, true);
	}

	public static RedTies2015Chassis getInstance() {
		if (instance == null) {
			instance = new RedTies2015Chassis();
		}
		return instance;
	}

	@Override
	public void rightDrive(double power) {
		rDrive1.set(power);
		//rDrive2.set(power);
	}

	@Override
	public void leftDrive(double power) {
		lDrive1.set(power);
		//lDrive2.set(power);
	}

	@Override
	public void init() {
		lEncoder.reset();
		rEncoder.reset();

	}

	@Override
	public int getLeftEncoder() {
		return lEncoder.getTicks();
	}

	@Override
	public int getRightEncoder() {
		return rEncoder.getTicks();
	}

	@Override
	public double getTheta() {
		return 360 * (lEncoder.getTicks() - rEncoder.getTicks()) * getTicksToInches() / (Math.PI * getWheelBase());
	}

	@Override
	public double getTicksToInches() {
		return (this.getWheelDiameter() * Math.PI) / 360;
	}

	@Override
	public double getWheelBase() {
		return 26.5;
	}

	@Override
	public double getWheelDiameter() {
		return 8.0;
	}

	@Override
	public int[] getAllEncoders() {
		return new int[]{getLeftEncoder(),getRightEncoder()};
	}
}
