package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.sample.SampleRobotObjectHolder;
import com.team1458.turtleshell2.interfaces.AutoMode;
import com.team1458.turtleshell2.interfaces.TestMode;

/**
 * Test Robot
 *
 * @author asinghani
 */
public class BlastoiseRobot extends SampleRobotObjectHolder {

    public BlastoiseRobot() {
        TurtleFlightStick left = new TurtleFlightStick(
                Constants.LJOYSTICKPORT);
        TurtleFlightStick right = new TurtleFlightStick(
                Constants.RJOYSTICKPORT);

        components.add(chassis);
    }

    /**
     * TODO: Add functionality
     * @return null
     */
    @Override
    public TestMode getTest() {
        return null;
    }

    /**
     * TODO: Add functionality
     * @return null
     */
    @Override
    public AutoMode getAuto() {
        return null;
    }
}
