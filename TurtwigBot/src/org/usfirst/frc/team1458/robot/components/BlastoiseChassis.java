package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.movement.*;
import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.sensor.TurtleDistanceEncoder;
import com.team1458.turtleshell2.sensor.TurtleNavX;
import com.team1458.turtleshell2.sensor.fake.TurtleFakeRotationEncoder;
import com.team1458.turtleshell2.util.Logger;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;
import org.usfirst.frc.team1458.robot.BlastoiseInputManager;
import org.usfirst.frc.team1458.robot.Robot;
import org.usfirst.frc.team1458.robot.constants.BlastoiseConstants;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;
import org.usfirst.frc.team1458.robot.constants.TurtwigConstants;

/**
 * New Robot Chassis
 *
 * @author asinghani
 */
public class BlastoiseChassis {

	/**
	 * Drive train
	 */
	private TankDrive tankDrive;

	// Sensors
	private TurtleNavX navX = null;

	// Input
	BlastoiseInputManager inputManager;

	private Logger logger;

	/**
	 * Main constructor for BlastoiseChassis
	 * Accepts TurtleAnalogInputs and TurtleDigitalInputs
	 */
	public BlastoiseChassis(BlastoiseInputManager inputManager, TurtleNavX navX, Logger logger) {
		this.inputManager = inputManager;
		this.navX = navX;

		TurtleDashboard.logAxis(inputManager.getLeftJoystick(), inputManager.getLeftJoystick());

		this.logger = logger;

		setupDrivetrain();
		TurtleDashboard.enablePidTuning(RobotConstants.TurnPID.PID_CONSTANTS, "TurnPID");
	}

	PID gearAlignPID = new PID(RobotConstants.GearPID.PID_CONSTANTS, RobotConstants.Vision.CAMERA_WIDTH, 0);

	/**
	 * Chassis Specific Initialization
	 * Called after constructor
	 */
	private void setupDrivetrain() {
		if(!Robot.isReal()) {
			// Simulation
			tankDrive = new TankDrive(
					new MotorSet(
							new TurtleTalonSR(1),
							new TurtleTalonSR(2)
					),
					new MotorSet(
							new TurtleTalonSR(3),
							new TurtleTalonSR(4)
					),
					new TurtleFakeRotationEncoder()
			);
		} else if(Robot.isPracticeRobot()) {
			// Turtwig Chassis
			tankDrive = new TankDrive(
					new MotorSet(
							new TurtleTalonSR(TurtwigConstants.LeftDrive.MOTOR1),
							new TurtleFakeMotor()
					),
					new MotorSet(
							new TurtleTalonSR(TurtwigConstants.RightDrive.MOTOR1, true),
							new TurtleFakeMotor()
					),
					new TurtleDistanceEncoder(
							TurtwigConstants.LeftDrive.ENCODER_A, TurtwigConstants.LeftDrive.ENCODER_B,
							TurtwigConstants.LeftDrive.ENCODER_RATIO, true
					),
					new TurtleDistanceEncoder(
							TurtwigConstants.RightDrive.ENCODER_A, TurtwigConstants.RightDrive.ENCODER_B,
							TurtwigConstants.RightDrive.ENCODER_RATIO, false
					),
					navX.getYawAxis()
			);
		} else {
			// Blastoise Chassis
			tankDrive = new TankDrive(
				new FollowerMotorSet(
						new TurtleTalonSRXCAN(BlastoiseConstants.LeftDrive.MOTOR1),
						new TurtleTalonSRXCAN(BlastoiseConstants.LeftDrive.MOTOR2),
						new TurtleTalonSRXCAN(BlastoiseConstants.LeftDrive.MOTOR3)
				),
				new FollowerMotorSet(
						new TurtleTalonSRXCAN(BlastoiseConstants.RightDrive.MOTOR1, true),
						new TurtleTalonSRXCAN(BlastoiseConstants.RightDrive.MOTOR2, true),
						new TurtleTalonSRXCAN(BlastoiseConstants.RightDrive.MOTOR3, true)
				),
				navX.getYawAxis()
			);
		}
	}

	public Distance getLeftDistance() {
		return tankDrive.getLeftDistance();
	}

	public Distance getRightDistance() {
		return tankDrive.getRightDistance();
	}

	public void updateMotors(MotorValue leftPower, MotorValue rightPower) {
		tankDrive.updateMotors(leftPower, rightPower);
	}

	public void stopMotors() {
		tankDrive.stopMotors();
	}
	
	public TankDrive getDriveTrain() {
		return tankDrive;
	}

	public void driveStraight(MotorValue speed, PIDConstants constants) {
		tankDrive.driveStraight(speed, constants);
	}

	public void stopDrivingStraight() {
		tankDrive.stopDrivingStraight();
	}

	public void turn(Angle angle, MotorValue speed, PIDConstants constants) {
		tankDrive.turn(angle, speed, constants);
	}

	public void stopTurn() {
		tankDrive.stopTurn();
	}

	public boolean isTurning() {
		return tankDrive.isTurning();
	}

	public void resetEncoders() {
		tankDrive.resetEncoders();
	}
}
