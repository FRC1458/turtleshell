package com.team1458.turtleshell.physical;

import com.team1458.turtleshell.TurtleSmartAccelerometer;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;

public class TurtleOnboardAccelerometer implements TurtleSmartAccelerometer {
	BuiltInAccelerometer acc;
	
	public TurtleOnboardAccelerometer(){
		acc = new BuiltInAccelerometer();  
	}
	
	@Override
	public double[] getAcceleration() {

		return new double[] {acc.getX(),acc.getY(),acc.getZ()};
		

	}

	@Override
	public double[] getDown() {

		return this.getDown();

	}

}
