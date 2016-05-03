package com.team1458.turtleshell2.implementations.sample;

import java.util.ArrayList;

import com.team1458.turtleshell2.interfaces.ObjectHolder;
import com.team1458.turtleshell2.interfaces.TurtleComponent;

public abstract class SampleRobotObjectHolder implements ObjectHolder {
	protected ArrayList<TurtleComponent> components = new ArrayList<TurtleComponent>();
	@Override
	public void teleUpdateAll() {
		for (TurtleComponent c : components) {
			c.teleUpdate();
		}

	}

	@Override
	public void autoUpdateAll() {
		for (TurtleComponent c : components) {
			c.autoUpdate();
		}

	}
}
