package org.usfirst.frc.team1458.robot.autonomous;

import org.usfirst.frc.team1458.robot.Robot;

import com.team1458.turtleshell2.movement.TankDrive;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.core.SampleAutoMode;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Test autonomous program
 *
 * @author asinghani
 */
public class TestAutonomous extends SampleAutoMode {
	private Robot r;

	public TestAutonomous(TankDrive chassis, Logger logger, TurtleNavX navX,
			Robot r) {
		super(chassis, logger, navX.getYawAxis());
		this.r = r;
	}

	/**
	 * Runs the autonomous program
	 */
	@Override
	public void auto() {
		Timer t = new Timer();
		t.start();
		while (t.get() < 1 && r.isAutonomous() && r.isEnabled()) {
			chassis.tankDrive(new MotorValue(-.8), new MotorValue(-.8));
			SmartDashboard.putNumber("TV", t.get());
		}
		chassis.stop();
		// chassis.driveForward(new Distance(6), MotorValue.fullForward, new
		// Distance(5));
		// chassis.turnRight(new Angle(90), new MotorValue(0.4), new Angle(5));
		// Timer.delay(3);
		/*
		 * chassis.driveForward(Distance.createInches(10), new MotorValue(0.4),
		 * Distance.createInches(1)); Timer.delay(3);
		 * chassis.driveBackward(Distance.createInches(10), new MotorValue(0.4),
		 * Distance.createInches(1)); Timer.delay(3);
		 */
		// chassis.turnLeft(new Angle(90), new MotorValue(0.4), new Angle(5));
		// Timer.delay(3);
	}
}