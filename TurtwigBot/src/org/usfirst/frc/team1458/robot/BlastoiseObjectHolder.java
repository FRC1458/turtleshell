package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.sample.SampleRobotObjectHolder;
import com.team1458.turtleshell2.interfaces.AutoMode;
import com.team1458.turtleshell2.interfaces.TestMode;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.util.TurtleLogger;

/**
 * Object Holder for BlastoiseRobot
 *
 * @author asinghani
 */
public class BlastoiseObjectHolder extends SampleRobotObjectHolder {

	private AutoMode autoMode;
	private TestMode testMode;
	
	private TurtleLogger logger;

	private BlastoiseChassis chassis;
	
	/**
	 * Instantiates BlastoiseObjectHolder
	 * @param logger The Logger
	 */
	public BlastoiseObjectHolder(TurtleLogger logger) {
		this.logger = logger;

        TurtleXboxController xboxController = new TurtleXboxController(BlastoiseConstants.UsbPorts.XBOX_CONTROLLER);
        chassis = new BlastoiseChassis(xboxController, logger);

	    addComponent(chassis);
	    this.autoMode = new BlastoiseTestTimedAutonomous(chassis);
	}

	private void addComponent(TurtleComponent component) {
		if (component != null) components.add(component);
	}

	@Override
	public TestMode getTest() {
		return testMode;
	}

	@Override
	public AutoMode getAuto() {
		return autoMode;
	}
}