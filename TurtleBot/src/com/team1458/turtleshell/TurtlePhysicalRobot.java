package com.team1458.turtleshell;

import java.util.HashMap;
import java.util.Map;

/**
 * A class representing the physical robot, it holds all of the robotComponents
 * for the robot.
 * 
 * @author mehnadnerd
 *
 */
public class TurtlePhysicalRobot {
	private Map<String,TurtleRobotComponent> robotComponents = new HashMap<String,TurtleRobotComponent>();

	public TurtlePhysicalRobot() {

	}

	/**
	 * Add a component to the robot, and initialise it. This method calls
	 * init().
	 * 
	 * @param component
	 *            The component to add.
	 */
	public void addComponent(String name, TurtleRobotComponent component) {
		component.init();
		robotComponents.put(name, component);
	}

	/**
	 * 
	 * @param s Name of component
	 * @return The component
	 */
	public TurtleRobotComponent getComponent(String s) {
		return robotComponents.get(s);
	}

}
