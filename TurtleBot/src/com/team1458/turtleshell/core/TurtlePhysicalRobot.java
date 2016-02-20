package com.team1458.turtleshell.core;

import java.util.HashMap;
import java.util.Map;

import com.team1458.turtleshell.component.TurtleRobotComponent;
import com.team1458.turtleshell.component.TurtleSmartRobotComponent;
import com.team1458.turtleshell.util.TurtleUpdatable;

/**
 * A class representing the physical robot, it holds all of the robotComponents
 * for the robot.
 * 
 * @author mehnadnerd
 *
 */
public class TurtlePhysicalRobot {
	private Map<String, TurtleRobotComponent> robotComponents = new HashMap<String, TurtleRobotComponent>();
	private Map<String, TurtleUpdatable> robotUpdatable = new HashMap<String, TurtleUpdatable>();

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

	public void addUpdatable(String name, TurtleUpdatable updatable) {
		robotUpdatable.put(name, updatable);
	}

	/**
	 * 
	 * @param s
	 *            Name of component
	 * @return The component
	 */
	public TurtleRobotComponent getComponent(String s) {
		return robotComponents.get(s);
	}

	/**
	 * Updates all updatable components
	 */
	public void updateAll() {
		for (TurtleUpdatable u : robotUpdatable.values()) {
			u.update();
		}
	}

	/**
	 * Updates all updatable components and all autoUpdatableComponents
	 */
	public void autoUpdateAll() {
		updateAll();
		for (TurtleRobotComponent c : robotComponents.values()) {
			if (c instanceof TurtleSmartRobotComponent) {
				((TurtleSmartRobotComponent) c).autoUpdate();
			}
		}
	}

	/**
	 * 
	 */
	public void teleUpdateAll() {
		updateAll();
		for (TurtleRobotComponent c : robotComponents.values()) {
			c.teleUpdate();
		}
	}

	public void stopAll() {
		for (TurtleRobotComponent c : robotComponents.values()) {
			c.stop();
		}
	}

}
