package com.team1458.turtleshell;

import java.util.ArrayList;

/**
 * A class representing the physical robot, it holds all of the robotComponents for the robot.
 * @author mehnadnerd
 *
 */
public class TurtlePhysicalRobot {
	private ArrayList<TurtleRobotComponent> robotComponents = new ArrayList<TurtleRobotComponent>();

	public TurtlePhysicalRobot() {

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
				((TurtleTeleoperable) c).teleUpdate();
				;
			}
		}
	}

	/**
	 * Calls the autoUpdate method for all components that update it
	 */
	public void autoUpdateAll() {
		for (TurtleRobotComponent c : robotComponents) {
			if (c instanceof TurtleAutoable) {
				((TurtleAutoable) c).autoUpdate();
				;
			}
		}
	}

	/**
	 * Unbelievably sketchy way of getting robotComponents that implement a particular interface
	 * @param interfac Interface to get robotComponents that are
	 * @return An arraylist holding all robotComponents that extend that interface
	 */
	public ArrayList<TurtleRobotComponent> getComponent(Class<? extends TurtleRobotComponent> interfac) {
		ArrayList<TurtleRobotComponent> toRet = new ArrayList<TurtleRobotComponent>();
		for (TurtleRobotComponent c : robotComponents) {
			if (interfac.isInstance(c)) {
				toRet.add(c);
			}
		}

		return toRet;
	}

}
