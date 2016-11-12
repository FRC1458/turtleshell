package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.implementations.sensor.TurtleDistanceEncoder;
import com.team1458.turtleshell2.interfaces.Chassis;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * New BlastoiseRobot Chassis
 *
 * @author asinghani
 */
public class BlastoiseChassis implements Chassis{

    private final TurtleMotor leftDriveTrain1 = new TurtleVictor888(BlastoiseConstants.LeftDrive.MOTOR1);
	private final TurtleMotor rightDriveTrain1 = new TurtleVictor888(BlastoiseConstants.RightDrive.MOTOR1);
	//private final TurtleMotor leftDriveTrain2 = new TurtleVictor888(BlastoiseConstants.LEFT_MOTOR2_PORT);
	//private final TurtleMotor rightDriveTrain2 = new TurtleVictor888(BlastoiseConstants.RIGHT_MOTOR2_PORT);

	private final TurtleDistanceEncoder leftDistance = new TurtleDistanceEncoder(
			BlastoiseConstants.LeftDrive.ENCODER_A,
			BlastoiseConstants.LeftDrive.ENCODER_B,
			BlastoiseConstants.LeftDrive.ENCODER_RATIO
	);

	private final TurtleDistanceEncoder rightDistance = new TurtleDistanceEncoder(
			BlastoiseConstants.RightDrive.ENCODER_A,
			BlastoiseConstants.RightDrive.ENCODER_B,
			BlastoiseConstants.RightDrive.ENCODER_RATIO
	);

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

    /**
     * Sends raw values directly to the drive system
     */
    public void updateMotors(MotorValue leftPower, MotorValue rightPower) {
        leftDriveTrain1.set(leftPower);
        rightDriveTrain1.set(rightPower);

        //leftDriveTrain2.set(leftPower);
        //rightDriveTrain2.set(rightPower);
    }

	/**
	 * Set all drive motors to zero speed
	 */
	public void stopMotors() {
	    updateMotors(MotorValue.zero, MotorValue.zero);
    }
}
