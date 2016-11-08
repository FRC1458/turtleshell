package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.util.TurtleLogger;
import edu.wpi.first.wpilibj.SampleRobot;

/**
 * New Test Robot
 *
 * @author asinghani
 */
public class BlastoiseRobot extends SampleRobot {

    BlastoiseChassis chassis;
    TurtleLogger logger;

    /**
     * Constructor for robot
     */
    public BlastoiseRobot() {
        logger = new TurtleLogger(Constants.LOGGER_MODE);
    }

    @Override
    protected void robotInit() {
        TurtleFlightStick left = new TurtleFlightStick(Constants.LEFT_JOYSTICK_PORT);
        TurtleFlightStick right = new TurtleFlightStick(Constants.RIGHT_JOYSTICK_PORT);

        chassis = new BlastoiseChassis();
        chassis.setLeftJoystick(left.getAxis(TurtleFlightStick.FlightAxis.PITCH));
        chassis.setRightJoystick(right.getAxis(TurtleFlightStick.FlightAxis.PITCH));
    }

    private void teleUpdate() {
        logger.warn("Teleop mode not implemented");
    }

    @Override
    protected void disabled() {
        logger.info("Robot disabled");
    }

    @Override
    public void autonomous() {
        logger.warn("Autonomous mode not implemented");
    }

    @Override
    public void operatorControl() {
        logger.info("Entered operator control");

        while (isOperatorControl() && isEnabled()) {
            //logger.verbose("Running teleUpdate");
            teleUpdate();
        }
    }

    @Override
    public void test() {
        logger.warn("Test mode not implemented");
    }

}
