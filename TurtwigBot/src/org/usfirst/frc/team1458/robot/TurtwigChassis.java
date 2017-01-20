package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController.XboxButton;
import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.implementations.sensor.TurtleDistanceEncoder;
import com.team1458.turtleshell2.interfaces.Chassis;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.input.TurtleDigitalInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.interfaces.sensor.TurtleDistanceSensor;
import com.team1458.turtleshell2.interfaces.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.Distance;
import com.team1458.turtleshell2.util.types.MotorValue;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Random;

/**
 * New BlastoiseRobot Chassis
 *
 * @author asinghani
 */
public class TurtwigChassis implements Chassis, TurtleComponent{

	/**
	 * Left drive motor(s)
	 */
    private final TurtleMotor leftDriveMotor1 = new TurtleVictor888(TurtwigConstants.LeftDrive.MOTOR1);
	//private final TurtleMotor leftDriveMotor2 = new TurtleVictor888(BlastoiseConstants.LeftDrive.MOTOR2);

	/**
	 * Right drive motor(s)
	 */
	private final TurtleMotor rightDriveMotor1 = new TurtleVictor888(TurtwigConstants.RightDrive.MOTOR1, true);
	//private final TurtleMotor rightDriveMotor2 = new TurtleVictor888(BlastoiseConstants.RightDrive.MOTOR2, true);

	private final TurtleDistanceSensor leftDistance = new TurtleDistanceEncoder(
			TurtwigConstants.LeftDrive.ENCODER_A,
			TurtwigConstants.LeftDrive.ENCODER_B,
			TurtwigConstants.LeftDrive.ENCODER_RATIO,
			true
	);

	private final TurtleDistanceSensor rightDistance = new TurtleDistanceEncoder(
			TurtwigConstants.RightDrive.ENCODER_A,
			TurtwigConstants.RightDrive.ENCODER_B,
			TurtwigConstants.RightDrive.ENCODER_RATIO
	);

	private final TurtleRotationSensor orientationSensor = null; //new TurtleAnalogGyro(BlastoiseConstants.GYRO_PORT);

	// Fix for bugs with SmartDashboard
	Random r = new Random();

	private TurtleAnalogInput rightJoystick;
	private TurtleAnalogInput leftJoystick;

	private TurtleDigitalInput straightButton;
	private TurtleDigitalInput turnButton;

	private TurtleDigitalInput resetButton;
	

	private TurtleLogger logger;

	/**
	 * Main constructor for BlastoiseChassis
	 * Accepts two TurtleAnalogInputs
	 */
	public TurtwigChassis(TurtleAnalogInput leftJoystick, TurtleAnalogInput rightJoystick,
			TurtleDigitalInput straightButton, TurtleDigitalInput turnButton, TurtleDigitalInput resetButton, TurtleLogger logger) {
		this.leftJoystick = leftJoystick;
		this.rightJoystick = rightJoystick;

		this.straightButton = straightButton;
		this.turnButton = turnButton;
		this.resetButton = resetButton;
		
		TurtleDashboard.logAxis(leftJoystick, rightJoystick);

		this.logger = logger;
	}


	/**
	 * Convenience method for instantiating with Xbox controller
	 * @param controller Xbox controller
	 */
	public TurtwigChassis (TurtleXboxController controller, TurtleLogger logger) {
		this(
				controller.getAxis(TurtleXboxController.XboxAxis.LY),
				controller.getAxis(TurtleXboxController.XboxAxis.RY),
				controller.getButton(TurtleXboxController.XboxButton.RBUMP),
				controller.getButton(TurtleXboxController.XboxButton.LBUMP),
				controller.getButton(TurtleXboxController.XboxButton.A),
				logger
		);
	}

	/**
	 * Convenience method for instantiating with two flight sticks
	 * @param leftFlightStick
	 * @param rightFlightStick
	 */
	public TurtwigChassis (TurtleFlightStick leftFlightStick, TurtleFlightStick rightFlightStick, TurtleLogger logger) {
		this(
				leftFlightStick.getAxis(TurtleFlightStick.FlightAxis.PITCH),
				rightFlightStick.getAxis(TurtleFlightStick.FlightAxis.PITCH),
				rightFlightStick.getButton(TurtleFlightStick.FlightButton.TRIGGER),
				leftFlightStick.getButton(TurtleFlightStick.FlightButton.TRIGGER),
				rightFlightStick.getButton(TurtleFlightStick.FlightButton.TWO),
				logger
		);
	}


	/**
	 * @return Left distance
	 */
	public Distance getLeftDistance() {
		return leftDistance.getDistance();
	}

	/**
	 * @return Right distance
	 */
	public Distance getRightDistance() {
		return rightDistance.getDistance();
	}

	public TurtleRotationSensor getOrientationSensor() {
		return orientationSensor;
	}

	@Override
	public void teleUpdate() {
		MotorValue leftPower = new MotorValue(TurtleMaths.deadband(leftJoystick.get(), BlastoiseConstants.JOYSTICK_DEADBAND));
		MotorValue rightPower = new MotorValue(TurtleMaths.deadband(rightJoystick.get(), BlastoiseConstants.JOYSTICK_DEADBAND));

		if(turnButton.get() == 1) {
			updateMotors(leftPower, leftPower.invert());
		} else if(straightButton.get() == 1){
			updateMotors(leftPower, leftPower);
		} else{
			updateMotors(leftPower, rightPower);
		}
		
		if(resetButton.get() == 1){
			leftDistance.reset();
			rightDistance.reset();
		}
		
		SmartDashboard.putNumber("Left", leftDistance.getDistance().getInches());
		SmartDashboard.putNumber("Right", rightDistance.getDistance().getInches());
	}

    /**
     * Sends raw values directly to the drive system
     */
    public void updateMotors(MotorValue leftPower, MotorValue rightPower) {
        leftDriveMotor1.set(leftPower);
        rightDriveMotor1.set(rightPower);
        
        
        SmartDashboard.putNumber("RightMotor", rightPower.getValue()+r.nextDouble()*0.00001);
        SmartDashboard.putNumber("LeftMotor", leftPower.getValue()+r.nextDouble()*0.00001);

        //leftDriveMotor2.set(leftPower);
        //rightDriveMotor2.set(rightPower);
    }

	/**
	 * Set all drive motors to zero speed
	 */
	public void stopMotors() {
	    updateMotors(MotorValue.zero, MotorValue.zero);
    }
}
