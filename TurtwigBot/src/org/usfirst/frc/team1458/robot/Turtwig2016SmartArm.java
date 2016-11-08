package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.movement.TurtleVictor888;
import com.team1458.turtleshell2.implementations.pid.TurtlePDD2;
import com.team1458.turtleshell2.implementations.sensor.TurtleRotationEncoder;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.input.TurtleDigitalInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.interfaces.pid.TurtlePID;
import com.team1458.turtleshell2.interfaces.sensor.TurtleRotationSensor;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.Timer;

/**
 * Created by mehnadnerd on 2016-09-09.
 */
public class Turtwig2016SmartArm implements TurtleComponent {
    // Xbox axis, when thing pulled, arm moves?
    private final TurtleMotor armRight = new TurtleVictor888(
            TurtwigConstants.RIGHTINTAKEVICTORPORT);
    private final TurtleMotor armLeft = new TurtleVictor888(
            TurtwigConstants.LEFTINTAKEVICTORPORT);
    private final TurtleMotor armIntake = new TurtleVictor888(
            TurtwigConstants.SPININTAKEVICTORSPPORT);

    private final TurtleRotationSensor rot =
            new TurtleRotationEncoder(TurtwigConstants.LEFTINTAKEENCODERPORT1, TurtwigConstants.LEFTINTAKEENCODERPORT2, 1);

    private TurtlePID armPID;
    private double armDesired;
    private final Timer timer = new Timer();

    private TurtleAnalogInput armPower;
    private TurtleDigitalInput spinForward;
    private TurtleDigitalInput spinBackward;

    public void setXBoxJoystick(TurtleAnalogInput a) {
        armPower = a;
    }

    public void setLeftXBoxButton(TurtleDigitalInput d) {
        spinForward = d;
    }

    public void setRightXBoxButton(TurtleDigitalInput d) {
        spinBackward = d;
    }

    public void teleUpdate() {
        //Control wheels
        MotorValue armWheelPower = new MotorValue(armPower.get());
        armRight.set(armWheelPower);
        armLeft.set(armWheelPower);
        if (spinForward.get() == 1) {
            armIntake.set(MotorValue.fullForward);
        } else if (spinBackward.get() == 1) {
            armIntake.set(MotorValue.fullBackward);
        } else {
            armIntake.set(MotorValue.zero);
        }

        //Check so timer is started if needed, and also so we don't get massive jumps
        if (timer.get() == 0) {
            timer.start();
        } else if (timer.get() > 1.0) {
            timer.reset();
        }

        //control arm
        //calc delta to armDesired
        armDesired += TurtwigConstants.INTAKEJOYSTICKSCALE * armPower.get() * timer.get();
        timer.reset();

        //use values
        armPID = new TurtlePDD2(TurtwigConstants.intakePIDConstants, armDesired, 0);
        MotorValue armMainPower = armPID.newValue(new double[]{rot.getRotation().getDegrees(), rot.getRate().getValue()});
        armRight.set(armMainPower);
        armLeft.set(armMainPower);

    }

    public void autoUpdate() {

    }
}
