package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.implementations.pid.TurtleStraightDrivePID;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.TurtlePIDConstants;
import com.team1458.turtleshell2.util.types.MotorValue;

import java.util.Random;

/**
 * New Robot Chassis
 *
 * @author asinghani
 */
public class BlastoiseChassis {

    private final TurtleMotor leftDriveTrain1 = new TurtleVictor888(BlastoiseConstants.LEFT_MOTOR1_PORT);
    //private final TurtleMotor leftDriveTrain2 = new TurtleVictor888(BlastoiseConstants.LEFT_MOTOR2_PORT);
    private final TurtleMotor rightDriveTrain1 = new TurtleVictor888(BlastoiseConstants.RIGHT_MOTOR1_PORT);
    //private final TurtleMotor rightDriveTrain2 = new TurtleVictor888(BlastoiseConstants.RIGHT_MOTOR2_PORT);

    private TurtleAnalogInput rightJoystick;
    private TurtleAnalogInput leftJoystick;

    public void setRightJoystick(TurtleAnalogInput a) {
        rightJoystick = a;
    }

    public void setLeftJoystick(TurtleAnalogInput a) {
        leftJoystick = a;
    }

    /**
     * Basic tele-op mode, joystick values (forward/backward) sent directly to drive train
     */
    public void teleUpdate() {
        MotorValue leftPower = new MotorValue(leftJoystick.get());
        MotorValue rightPower = new MotorValue(rightJoystick.get());

        leftDriveTrain1.set(leftPower);
        //leftDriveTrain2.set(leftPower);
        rightDriveTrain1.set(rightPower);
        //rightDriveTrain2.set(rightPower);
    }

    /**
     * Autonomous mode
     *
     * Drives forward
     */
    public void autonomous() {
        /*TurtleStraightDrivePID pid = new TurtleStraightDrivePID(new TurtlePIDConstants(.0015, 0, .0001, .0001), 5000, 0.00005, 0);
        double lSpeed = 0;
        double rSpeed = 0;
        double lDistance = 0;
        double rDistance = 0;
        Random r = new Random();
        MotorValue[] motors;
        while (!pid.atTarget()) {
            lDistance += lSpeed;
            rDistance += rSpeed;
            System.out.println(lDistance + "|" + rDistance);
            motors = pid.newValue(new double[] { lDistance, rDistance, lSpeed, rSpeed });
            lSpeed = .3 * motors[0].getValue() + .7 * lSpeed;
            rSpeed = .3 * motors[1].getValue() + .7 * rSpeed;
            lSpeed *= (r.nextDouble() / 5 + .9);
            rSpeed *= (r.nextDouble() / 5 + .9);
        }*/
    }
}
