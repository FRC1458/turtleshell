package org.usfirst.frc.team1458.robot.autonomous;

import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.Logger;

import org.usfirst.frc.team1458.robot.BlastoiseAutoMode;
import org.usfirst.frc.team1458.robot.components.BlastoiseChassis;

/**
 * Time-based autonomous test program
 *
 * Drives the robot forward for 1 second,
 * then turns right for one second,
 * then drives backward for one second,
 * then turns left for one second
 *
 * @author asinghani
 */
public class TestAutonomous extends BlastoiseAutoMode {

	public TestAutonomous(BlastoiseChassis chassis, Logger logger, TurtleNavX navX) {
		super(chassis.getDriveTrain(), logger, navX.getYawAxis());
	}

	/**
	 * Runs the autonomous program
	 */
	@Override
	public void auto(){
		//moveMillis(1000, 0.7);
		//turnMillis(500, 0.7);
		//moveMillis(1000, -0.7);
		//turnMillis(500, -0.7);
		
		turnDegrees(90, 0.7);
	}
}