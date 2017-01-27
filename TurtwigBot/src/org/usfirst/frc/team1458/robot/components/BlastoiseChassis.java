package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.implementations.drive.TankDrive;
import com.team1458.turtleshell2.implementations.drive.TurtleMotorSet;
import com.team1458.turtleshell2.implementations.input.TurtleSampleButtonInput;
import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.movement.TurtleFakeMotor;
import com.team1458.turtleshell2.implementations.movement.TurtleSpark;
import com.team1458.turtleshell2.implementations.movement.TurtleTalonSR;
import com.team1458.turtleshell2.implementations.pid.TurtlePDD2;
import com.team1458.turtleshell2.implementations.sensor.TurtleDistanceEncoder;
import com.team1458.turtleshell2.implementations.sensor.TurtleNavX;
import com.team1458.turtleshell2.implementations.vision.Contour;
import com.team1458.turtleshell2.implementations.vision.GripInterface;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.input.TurtleDigitalInput;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.Angle;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1458.robot.Robot;
import org.usfirst.frc.team1458.robot.BlastoiseVision;
import org.usfirst.frc.team1458.robot.BlastoiseRobot;
import org.usfirst.frc.team1458.robot.constants.BlastoiseConstants;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;
import org.usfirst.frc.team1458.robot.constants.TurtwigConstants;

import java.util.Arrays;

/**
 * New BlastoiseRobot Chassis
 *
 * @author asinghani
 */
public class BlastoiseChassis implements TurtleComponent {

	/**
	 * Drive train
	 */
	private TankDrive tankDrive;

	private final TurtleNavX navX = TurtleNavX.getInstanceI2C(); //TurtleNavX.getInstanceUSB(RobotConstants.Sensors.NAVX_PORT);

	/**
	 * Chassis Specific Initialization
	 */
	{
		if(!Robot.isReal()) {
			// Simulation
			tankDrive = new TankDrive(
					new TurtleMotorSet(
							new TurtleTalonSR(1),
							new TurtleTalonSR(2)
					),
					new TurtleMotorSet(
							new TurtleTalonSR(3),
							new TurtleTalonSR(4)
					),
					null
			);
		} else if(BlastoiseRobot.isPracticeRobot()) {
			// Turtwig Chassis
			tankDrive = new TankDrive(
					new TurtleMotorSet(
							new TurtleTalonSR(TurtwigConstants.LeftDrive.MOTOR1),
							new TurtleFakeMotor()
					),
					new TurtleMotorSet(
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
					new TurtleMotorSet(
							new TurtleSpark(BlastoiseConstants.LeftDrive.MOTOR1),
							new TurtleTalonSR(BlastoiseConstants.LeftDrive.MOTOR2)
					),
					new TurtleMotorSet(
							new TurtleSpark(BlastoiseConstants.RightDrive.MOTOR1, true),
							new TurtleTalonSR(BlastoiseConstants.RightDrive.MOTOR2, true)
					),
					navX.getYawAxis()
			);
		}
	}

	// Input joysticks and buttons
	private TurtleAnalogInput rightJoystick;
	private TurtleAnalogInput leftJoystick;

	private TurtleSampleButtonInput straightButton;
	private TurtleSampleButtonInput turnButton;
	private TurtleSampleButtonInput resetButton;

	private TurtleSampleButtonInput gearButton;

	private TurtleDigitalInput pov;
	private TurtleXboxController rumbleController; // Very experimental

	private TurtleLogger logger;

	/**
	 * Main constructor for BlastoiseChassis
	 * Accepts TurtleAnalogInputs and TurtleDigitalInputs
	 */
	public BlastoiseChassis(TurtleAnalogInput leftJoystick, TurtleAnalogInput rightJoystick,
							TurtleSampleButtonInput straightButton, TurtleSampleButtonInput turnButton,
							TurtleSampleButtonInput resetButton, TurtleSampleButtonInput gearButton,
							TurtleDigitalInput pov, TurtleLogger logger) {
		this.leftJoystick = leftJoystick;
		this.rightJoystick = rightJoystick;

		this.straightButton = straightButton;
		this.turnButton = turnButton;
		this.resetButton = resetButton;
		this.gearButton = gearButton;
		this.pov = pov;

		TurtleDashboard.logAxis(leftJoystick, rightJoystick);

		this.logger = logger;
	}


	/**
	 * Convenience method for instantiating with Xbox controller
	 * @param controller Xbox controller
	 */
	public BlastoiseChassis (TurtleXboxController controller, TurtleLogger logger) {
		this(
				controller.getAxis(TurtleXboxController.XboxAxis.LY),
				controller.getAxis(TurtleXboxController.XboxAxis.RY),
				controller.getButton(TurtleXboxController.XboxButton.RBUMP),
				controller.getButton(TurtleXboxController.XboxButton.LBUMP),
				controller.getButton(TurtleXboxController.XboxButton.A),
				controller.getButton(TurtleXboxController.XboxButton.Y),
				controller.getDPad(),
				logger
		);
		rumbleController = controller;
	}

	/**
	 * Convenience method for instantiating with two flight sticks
	 * @param leftFlightStick
	 * @param rightFlightStick
	 */
	public BlastoiseChassis (TurtleFlightStick leftFlightStick, TurtleFlightStick rightFlightStick, TurtleLogger logger) {
		this(
				leftFlightStick.getAxis(TurtleFlightStick.FlightAxis.PITCH),
				rightFlightStick.getAxis(TurtleFlightStick.FlightAxis.PITCH),
				rightFlightStick.getButton(TurtleFlightStick.FlightButton.TRIGGER),
				leftFlightStick.getButton(TurtleFlightStick.FlightButton.TRIGGER),
				rightFlightStick.getButton(TurtleFlightStick.FlightButton.TWO),
				rightFlightStick.getButton(TurtleFlightStick.FlightButton.THREE),
				rightFlightStick.getPOVSwitch(),
				logger
		);
	}

	TurtlePDD2 gearAlignPID = new TurtlePDD2(RobotConstants.GearPID.PID_CONSTANTS, RobotConstants.GearPID.CAMERA_WIDTH, 0);

	@Override
	public void teleUpdate() {
		/**
		 * User-control
		 */
		MotorValue leftPower = new MotorValue(TurtleMaths.deadband(leftJoystick.get(), RobotConstants.JOYSTICK_DEADBAND));
		MotorValue rightPower = new MotorValue(TurtleMaths.deadband(rightJoystick.get(), RobotConstants.JOYSTICK_DEADBAND));

		if(turnButton.getButton()) {
			updateMotors(leftPower, leftPower.invert());
		} else if(straightButton.getButton()){
			updateMotors(leftPower, leftPower);
		} else{
			updateMotors(leftPower, rightPower);
		}

		/**
		 * 45, 90, etc. degree turns
		 */
		double povDegrees = pov.get();
		double lastPovDegrees = povDegrees;
		//logger.info("POV: "+degrees);
		if(povDegrees != -1){
			while(pov.get() != -1){
				lastPovDegrees = povDegrees;
				povDegrees = pov.get();
			}
			povDegrees = lastPovDegrees;
			if(povDegrees > 180) povDegrees = (povDegrees - 360);
			logger.info("Turning: "+povDegrees);
			tankDrive.turn(new Angle(povDegrees), new MotorValue(RobotConstants.TurnPID.TURN_SPEED),
					RobotConstants.TurnPID.PID_CONSTANTS);
		}

		/**
		 * Gear alignment
		 */
		if(gearButton.getButton()){
			int springX = BlastoiseVision.getSpringX();
			if(springX > -1) {
				MotorValue val = gearAlignPID.newValue(springX, 0);
				updateMotors(val, val.invert());
			}
		}

		if(resetButton.getButton()){
			tankDrive.resetEncoders();
		}

		SmartDashboard.putNumber("SpringX", BlastoiseVision.getSpringX());

		tankDrive.teleUpdate();

		SmartDashboard.putNumber("LeftEncoder", tankDrive.getLeftDistance().getInches());
		SmartDashboard.putNumber("RightEncoder", tankDrive.getRightDistance().getInches());


		SmartDashboard.putNumber("Yaw", navX.getYawAxis().getRotation().getDegrees());
		SmartDashboard.putNumber("CompassHeading", navX.getCompassHeading().getDegrees());
		SmartDashboard.putNumber("FusedHeading", navX.getFusedHeading().getDegrees());

		SmartDashboard.putNumber("SpringX", BlastoiseVision.getSpringX());
		
		if(navX.isInCollision(RobotConstants.COLLISION_THRESHOLD)){
			rumbleController.rumble(1.0f, 250); // Very experimental
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
}
