package org.usfirst.frc.team1458.robot.autonomous;

import com.team1458.turtleshell2.implementations.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.TurtleLogger;

import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team1458.robot.BlastoiseAutoMode;
import org.usfirst.frc.team1458.robot.components.BlastoiseChassis;

/**
 * Time-based autonomous test program
 *
 * Drives the robot forward for 4 feet,
 * then turns right for one second,
 * then drives backward for 4 feet,
 * then turns left for one second
 *
 * @author asinghani
 */
public class BlastoiseTestAutonomous extends BlastoiseAutoMode {

	private static double SPEED = 0.5;
	
	TurtleNavX navX;

	public BlastoiseTestAutonomous(BlastoiseChassis chassis, TurtleLogger logger, TurtleNavX navX) {
		super(chassis.getDriveTrain(), logger, navX.getYawAxis());
		
		this.navX = navX;
	}

	/**
	 * Runs the autonomous program
	 */
	@Override
	public void auto(){
		navX.reset();
		navX.resetDisplacement();
		navX.zeroYaw();
		Timer.delay(1);
		logger.info("After 1 second: Yaw="+navX.getYaw().getDegrees()+" CompassHeading="+navX.getCompassHeading().getDegrees()+" FusedHeading="+navX.getFusedHeading());
		
		navX.reset();
		navX.resetDisplacement();
		navX.zeroYaw();
		Timer.delay(1);
		logger.info("After 2 second: Yaw="+navX.getYaw().getDegrees()+" CompassHeading="+navX.getCompassHeading().getDegrees()+" FusedHeading="+navX.getFusedHeading());
		
		navX.reset();
		navX.resetDisplacement();
		navX.zeroYaw();
		Timer.delay(1);
		logger.info("After 3 second: Yaw="+navX.getYaw().getDegrees()+" CompassHeading="+navX.getCompassHeading().getDegrees()+" FusedHeading="+navX.getFusedHeading());
	}
}
