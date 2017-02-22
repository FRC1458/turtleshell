package org.usfirst.frc.team1458.robot.autonomous;

import com.team1458.turtleshell2.core.SampleAutoMode;
import com.team1458.turtleshell2.movement.TankDrive;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.Timer;

/**
 * This autonomous program will make the robot cross the baseline and place a gear in the left peg.
 * The robot must start against the alliance station wall, facing the alliance station wall.
 *
 * @author asinghani
 */
public class LeftGear extends SampleAutoMode {
	public LeftGear(TankDrive chassis, Logger logger, TurtleNavX navX) {
		super(chassis, logger, navX.getYawAxis());
	}

	/**
	 * Runs the autonomous program.
	 *
	 * This will drive the robot forward 100 inches at 0.8 speed and then 8 inches at 0.4 speed
	 */
	@Override
	public void auto(){
		chassis.driveBackward(Distance.createInches(100), new MotorValue(0.8), Distance.createInches(2));
		chassis.stop();
		Timer.delay(0.2);
		chassis.driveBackward(Distance.createInches(9.0), new MotorValue(0.4), Distance.createInches(1));
	}
}