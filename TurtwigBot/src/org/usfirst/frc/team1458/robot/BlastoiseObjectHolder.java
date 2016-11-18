package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.implementations.sample.SampleRobotObjectHolder;
import com.team1458.turtleshell2.interfaces.AutoMode;
import com.team1458.turtleshell2.interfaces.TestMode;
import com.team1458.turtleshell2.interfaces.TurtleComponent;

/**
 * Object Holder for BlastoiseRobot
 *
 * @author asinghani
 */
public class BlastoiseObjectHolder extends SampleRobotObjectHolder {

	AutoMode autoMode;
	TestMode testMode;

	/**
	 * Instantiates BlastoiseObjectHolder
	 * @param autoMode Can be null
	 * @param testMode Can be null
	 */
	public BlastoiseObjectHolder(AutoMode autoMode, TestMode testMode) {
		this.autoMode = autoMode;
		this.testMode = testMode;
	}

	public void addComponent(TurtleComponent component) {
		components.add(component);
	}

	public void setAutoMode(AutoMode autoMode) {
		this.autoMode = autoMode;
	}

	public void setTestMode(TestMode testMode) {
		this.testMode = testMode;
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