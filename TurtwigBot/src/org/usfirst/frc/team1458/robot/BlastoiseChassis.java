package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.types.MotorValue;

/**
 * New Robot Chassis
 *
 * @author asinghani
 */
public class BlastoiseChassis {

    private final TurtleMotor leftDriveTrain = new TurtleVictor888(Constants.LEFT_MOTOR_PORT);
    private final TurtleMotor rightDriveTrain = new TurtleVictor888(Constants.RIGHT_MOTOR_PORT);

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

        leftDriveTrain.set(leftPower);
        rightDriveTrain.set(rightPower);
    }

    /**
     * Unimplemented autonomous mode
     */
    public void autoUpdate() {
        // TODO: Implement autonomous mode
    }
}
