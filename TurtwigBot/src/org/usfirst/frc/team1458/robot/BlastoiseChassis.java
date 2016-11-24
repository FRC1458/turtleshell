package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.implementations.sensor.TurtleDistanceEncoder;
import com.team1458.turtleshell2.interfaces.Chassis;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.util.Random;

/**
 * New BlastoiseRobot Chassis
 *
 * @author asinghani
 */
public class BlastoiseChassis implements Chassis, TurtleComponent{

	/**
	 * Left drive motor(s)
	 */
    private final TurtleMotor leftDriveMotor1 = new TurtleVictor888(BlastoiseConstants.LeftDrive.MOTOR1);
	//private final TurtleMotor leftDriveMotor2 = new TurtleVictor888(BlastoiseConstants.LeftDrive.MOTOR2);

	/**
	 * Right drive motor(s)
	 */
	private final TurtleMotor rightDriveMotor1 = new TurtleVictor888(BlastoiseConstants.RightDrive.MOTOR1, true);
	//private final TurtleMotor rightDriveMotor2 = new TurtleVictor888(BlastoiseConstants.RightDrive.MOTOR2, true);

	private final TurtleDistanceEncoder leftDistance = null;
	/*new TurtleDistanceEncoder(
			BlastoiseConstants.LeftDrive.ENCODER_A,
			BlastoiseConstants.LeftDrive.ENCODER_B,
			BlastoiseConstants.LeftDrive.ENCODER_RATIO
	);*/

	private final TurtleDistanceEncoder rightDistance = null;
	/*new TurtleDistanceEncoder(
			BlastoiseConstants.RightDrive.ENCODER_A,
			BlastoiseConstants.RightDrive.ENCODER_B,
			BlastoiseConstants.RightDrive.ENCODER_RATIO
	);*/

	// Fix for bugs with SmartDashboard
	Random r = new Random();


	private TurtleAnalogInput rightJoystick;
	private TurtleAnalogInput leftJoystick;

	private TurtleLogger logger;

	/**
	 * Main constructor for BlastoiseChassis
	 * Accepts two TurtleAnalogInputs
	 */
	public BlastoiseChassis(TurtleAnalogInput leftJoystick, TurtleAnalogInput rightJoystick, TurtleLogger logger) {
		this.leftJoystick = leftJoystick;
		this.rightJoystick = rightJoystick;
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
				logger
		);
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
				logger
		);
	}


	/**
	 * @return Left distance encoder
	 */
	public TurtleDistanceEncoder getLeftDistance() {
		return leftDistance;
	}

	/**
	 * @return Right distance encoder
	 */
	public TurtleDistanceEncoder getRightDistance() {
		return rightDistance;
	}

	@Override
	public void teleUpdate() {
		MotorValue leftPower = new MotorValue(leftJoystick.get());
		MotorValue rightPower = new MotorValue(rightJoystick.get());

		updateMotors(leftPower, rightPower);
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
