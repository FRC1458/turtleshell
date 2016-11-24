package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.interfaces.AutoMode;
import com.team1458.turtleshell2.interfaces.TestMode;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleLogger;
import edu.wpi.first.wpilibj.SampleRobot;

/**
 * Base Robot Code
 *
 * @author asinghani
 */
public class BlastoiseRobot extends SampleRobot {

    BlastoiseChassis chassis;
    TurtleLogger logger;

    BlastoiseObjectHolder objectHolder;

    /**
     * Constructor for robot
     */
    public BlastoiseRobot() {
        logger = new TurtleLogger(BlastoiseConstants.LOGGER_MODE);
        objectHolder = new BlastoiseObjectHolder(null, null);
    }

    @Override
    protected void robotInit() {
        TurtleXboxController xboxController = new TurtleXboxController(BlastoiseConstants.UsbPorts.XBOX_CONTROLLER);
        chassis = new BlastoiseChassis(xboxController, logger);

	    objectHolder.addComponent(chassis);
	    objectHolder.setAutoMode(new BlastoiseTestTimedAutonomous(chassis));

	    TurtleDashboard.setup();
    }

    @Override
    protected void disabled() {
        logger.info("Robot disabled");
        TurtleDashboard.disabled();
    }

    @Override
    public void autonomous() {
        logger.info("Entered autonomous control");
        TurtleDashboard.autonomous();

        AutoMode autoMode = objectHolder.getAuto();
        if(autoMode == null) {
            logger.warn("Autonomous mode not implemented");
        } else {
            autoMode.auto();
        }
    }

    @Override
    public void operatorControl() {
        logger.info("Entered operator control");
        TurtleDashboard.teleop();

        while (isOperatorControl() && isEnabled()) {
            //logger.verbose("Running teleUpdate");
            objectHolder.teleUpdateAll();
        }
    }

    @Override
    public void test() {
        logger.info("Entered test mode");
        TurtleDashboard.test();

        TestMode testMode = objectHolder.getTest();
        if(testMode == null) {
            logger.warn("Test mode not implemented");
        } else {
            testMode.test();
        }
    }
}
