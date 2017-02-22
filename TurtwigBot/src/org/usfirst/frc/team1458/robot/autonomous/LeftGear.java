package org.usfirst.frc.team1458.robot.autonomous;

import com.team1458.turtleshell2.core.SampleAutoMode;
import com.team1458.turtleshell2.movement.TankDrive;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.types.Angle;
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
	
	// start touching the retrieval zone
	// forward 106 inches
	// 60 degree left
	// forward 41.5 inches

	/**
	 * Runs the autonomous program.
	 */
	@Override
	public void auto(){
		chassis.driveBackward(Distance.createInches(106), new MotorValue(0.5), Distance.createInches(4));
		Timer.delay(0.2);
		chassis.turnRight(new Angle(60), new MotorValue(0.5), new Angle(2));
		Timer.delay(0.2);
		chassis.driveBackward(Distance.createInches(41.5), new MotorValue(0.5), Distance.createInches(2));
	}
}