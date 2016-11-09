package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.util.TurtleLogger;
import edu.wpi.first.wpilibj.SampleRobot;

/**
 * New Test Robot
 *
 * @author asinghani
 */
public class Robot extends SampleRobot {

    BlastoiseChassis chassis;
    TurtleLogger logger;

    /**
     * Constructor for robot
     */
    public Robot() {
        logger = new TurtleLogger(BlastoiseConstants.LOGGER_MODE);
    }

    @Override
    protected void robotInit() {
        //TurtleFlightStick left = new TurtleFlightStick(BlastoiseConstants.LEFT_JOYSTICK_PORT);
        //TurtleFlightStick right = new TurtleFlightStick(BlastoiseConstants.RIGHT_JOYSTICK_PORT);

        TurtleXboxController controller = new TurtleXboxController(BlastoiseConstants.XBOX_CONTROLLER_PORT);

        chassis = new BlastoiseChassis();
        chassis.setLeftJoystick(controller.getAxis(TurtleXboxController.XboxAxis.LY));
        chassis.setRightJoystick(controller.getAxis(TurtleXboxController.XboxAxis.RY));
    }

    private void teleUpdate() {
        chassis.teleUpdate();
    }

    @Override
    protected void disabled() {
        logger.info("Robot disabled");
    }

    @Override
    public void autonomous() {
        logger.info("Entered autonomous control");
        chassis.autonomous();
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
