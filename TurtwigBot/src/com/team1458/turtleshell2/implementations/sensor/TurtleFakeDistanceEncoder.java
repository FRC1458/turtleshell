package com.team1458.turtleshell2.implementations.sensor;

import com.team1458.turtleshell2.interfaces.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.Rate;
import edu.wpi.first.wpilibj.Encoder;

/**
 * Created by mehnadnerd on 2016-09-09.
 */
public class TurtleFakeDistanceEncoder implements TurtleDistanceSensor {
	
	public TurtleFakeDistanceEncoder(Object... args){
		
	}
	
	@Override
	public Distance getDistance() {
		return new Distance(0);
	}

	@Override
	public Rate<Distance> getVelocity() {
		return new Rate<Distance>(0);
	}

	@Override
	public void reset() {
		// Do nothing
	}
    
}
