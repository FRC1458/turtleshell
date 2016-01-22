package com.team1458.turtleshell;

import java.util.ArrayList;

public class TurtlePhysicalRobotComponents {
	private ArrayList<TurtleRobotComponent> robotComponents = new ArrayList<TurtleRobotComponent>();

	public TurtlePhysicalRobotComponents() {

	}

	/**
	 * Add a component to the robot, and initialise it. This method calls
	 * init().
	 * 
	 * @param component
	 *            The component to add.
	 */
	public void addComponent(TurtleRobotComponent component) {
		component.init();
		robotComponents.add(component);
	}

	/**
	 * Calls the update method for all components.
	 */
	public void updateAll() {
		for (TurtleRobotComponent c : robotComponents) {
			c.update();
		}
	}

	/**
	 * Calls the teleUpdate method for all components that implement it.
	 */
	public void teleUpdateAll() {
		for (TurtleRobotComponent c : robotComponents) {
			if (c instanceof TurtleTeleoperable) {
				((TurtleTeleoperable)c).teleUpdate();;
			}
		}
	}
	
	/**
	 * Calls the autoUpdate method for all components that update it
	 */
	public void autoUpdateAll() {
		for (TurtleRobotComponent c : robotComponents) {
			if (c instanceof TurtleAutoable) {
				((TurtleAutoable)c).autoUpdate();;
			}
		}
	}

}
