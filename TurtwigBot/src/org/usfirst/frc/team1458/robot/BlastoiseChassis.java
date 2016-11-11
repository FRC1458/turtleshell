package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.interfaces.Chassis;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * New BlastoiseRobot Chassis
 *
 * @author asinghani
 */
public class BlastoiseChassis implements Chassis{

    private final TurtleMotor leftDriveTrain1 = new TurtleVictor888(BlastoiseConstants.LEFT_MOTOR1_PORT);
    //private final TurtleMotor leftDriveTrain2 = new TurtleVictor888(BlastoiseConstants.LEFT_MOTOR2_PORT);
    private final TurtleMotor rightDriveTrain1 = new TurtleVictor888(BlastoiseConstants.RIGHT_MOTOR1_PORT);
    //private final TurtleMotor rightDriveTrain2 = new TurtleVictor888(BlastoiseConstants.RIGHT_MOTOR2_PORT);

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
