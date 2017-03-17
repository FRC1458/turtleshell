package org.usfirst.frc.team1458.robot.autonomous;

import com.team1458.turtleshell2.movement.TankDrive;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.core.SampleAutoMode;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.Timer;

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
		chassis.turnRight(new Angle(90), new MotorValue(0.4), new Angle(5));
		Timer.delay(3);
		/*chassis.driveForward(Distance.createInches(10), new MotorValue(0.4), Distance.createInches(1));
		Timer.delay(3);
		chassis.driveBackward(Distance.createInches(10), new MotorValue(0.4), Distance.createInches(1));
		Timer.delay(3);*/
		chassis.turnLeft(new Angle(90), new MotorValue(0.4), new Angle(5));
		Timer.delay(3);
	}
}