package org.usfirst.frc.team1458.robot.autonomous;

import com.team1458.turtleshell2.core.SampleAutoMode;
import com.team1458.turtleshell2.movement.TankDrive;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.Timer;

/**
 * This autonomous program will make the robot cross the baseline and nothing else.
 * The robot must start against the alliance station wall, facing the airship.
 *
 * @author asinghani
 */
public class CrossBaseline extends SampleAutoMode {
	public CrossBaseline(TankDrive chassis, Logger logger, TurtleNavX navX) {
		super(chassis, logger, navX.getYawAxis());
	}

	/**
	 * Runs the autonomous program.
	 *
	 * This will drive the robot forward 9.5 feet at 0.8 speed and then 2 feet at 0.3 speed
	 */
	@Override
	public void auto(){
		chassis.driveForward(Distance.createFeet(9.5), new MotorValue(0.8), Distance.createFeet(0.5));
		chassis.stop();
		Timer.delay(0.2);
		chassis.driveForward(Distance.createFeet(2.0), new MotorValue(0.3), Distance.createFeet(0.3));
	}
}