package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.sample.SampleRobotObjectHolder;
import com.team1458.turtleshell2.interfaces.AutoMode;
import com.team1458.turtleshell2.interfaces.AutoModeHolder;
import com.team1458.turtleshell2.interfaces.TestMode;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.util.TurtleDashboard;
import com.team1458.turtleshell2.util.TurtleLogger;

import org.usfirst.frc.team1458.robot.autonomous.BlastoiseTestDistanceAutonomous;
import org.usfirst.frc.team1458.robot.autonomous.BlastoiseTestTimedAutonomous;

import java.util.ArrayList;

/**
 * Object Holder for BlastoiseRobot.
 * This controls switching between multiple autonomous modes.
 *
 * @author asinghani
 */
public class BlastoiseObjectHolder extends SampleRobotObjectHolder implements AutoModeHolder {

	private ArrayList<BlastoiseAutoMode> autoModes = new ArrayList<>();
	private int selectedAutoMode = 0;

	private TestMode testMode;

	private TurtleLogger logger;
	private BlastoiseChassis chassis;
	
	/**
	 * Instantiates BlastoiseObjectHolder
	 * @param logger Logger
	 */
	public BlastoiseObjectHolder(TurtleLogger logger) {
		this.logger = logger;

		// Setup controller and chassis
        //TurtleXboxController xboxController = new TurtleXboxController(RobotConstants.UsbPorts.XBOX_CONTROLLER);
        TurtleFlightStick leftStick = new TurtleFlightStick(RobotConstants.UsbPorts.LEFT_STICK);
        TurtleFlightStick rightStick = new TurtleFlightStick(RobotConstants.UsbPorts.RIGHT_STICK);
		
		chassis = new BlastoiseChassis(leftStick, rightStick, logger);
	    addComponent(chassis);

		// Setup AutoModes
	    autoModes.add(new BlastoiseTestDistanceAutonomous(chassis, logger));
		autoModes.add(new BlastoiseTestTimedAutonomous(chassis, logger));

		selectedAutoMode = 0;

		// Setup TestMode
		testMode = () -> {}; // Creates a TestMode with empty test() function

		TurtleDashboard.setAutoModeHolder(this);
	}

	private void addComponent(TurtleComponent component) {
		if (component != null) components.add(component);
	}

	/**
	 * Get the list of auto modes
	 */
	public ArrayList<BlastoiseAutoMode> getAutoModes() {
		return autoModes;
	}

	/**
	 * Set the index of the selected auto mode
	 * @param index
	 */
	public void setSelectedAutoModeIndex(int index) {
		if(index < autoModes.size()){
			selectedAutoMode = index;
			logger.info("Selected auto mode "+index+": "+autoModes.get(index).getName());
		}
	}

	/**
	 * Get index of selected AutoMode
	 */
	public int getSelectedAutoModeIndex() {
		return selectedAutoMode;
	}

	@Override
	public TestMode getTest() {
		return testMode;
	}

	@Override
	public AutoMode getAuto() {
		return autoModes.get(selectedAutoMode);
	}
}