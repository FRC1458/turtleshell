package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.util.TurtleLogger;
import edu.wpi.first.wpilibj.SampleRobot;

/**
 * Base Robot Code
 *
 * @author asinghani
 */
public class BlastoiseRobot extends SampleRobot {

    BlastoiseChassis chassis;
    BlastoiseController controller;
    TurtleLogger logger;

    /**
     * Constructor for robot
     */
    public BlastoiseRobot() {
        logger = TurtleLogger.getLogger(BlastoiseConstants.LOGGER_MODE);
    }

    @Override
    protected void robotInit() {
        chassis = new BlastoiseChassis();

        TurtleXboxController xboxController = new TurtleXboxController(BlastoiseConstants.UsbPorts.XBOX_CONTROLLER);
        controller = new BlastoiseController(xboxController, chassis);
    }

    private void teleUpdate() {
        controller.teleUpdate();
    }

    @Override
    protected void disabled() {
        logger.info("Robot disabled");
    }

    @Override
    public void autonomous() {
        logger.info("Entered autonomous control");
        controller.autonomous();
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
