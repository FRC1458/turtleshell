package org.usfirst.frc.team1458.robot.autonomous;

import com.team1458.turtleshell2.movement.TankDrive;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.core.SampleAutoMode;

/**
 * Test autonomous program
 *
 * @author asinghani
 */
public class TestAutonomous extends SampleAutoMode {

	public TestAutonomous(TankDrive chassis, Logger logger, TurtleNavX navX) {
		super(chassis, logger, navX.getYawAxis());
	}

	/**
	 * Runs the autonomous program
	 */
	@Override
	public void auto(){
		
	}
}