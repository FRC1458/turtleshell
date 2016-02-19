package com.team1458.turtleshell.base;

import java.util.ArrayList;

import com.team1458.turtleshell.util.TurtleUpdatable;

public class TurtleUpdatableBlob {
	private ArrayList<TurtleUpdatable> blob = new ArrayList<TurtleUpdatable>();

	public void addUpdatable(TurtleUpdatable updateable) {
		blob.add(updateable);
	}
	
	public void updateAll() {
		for (TurtleUpdatable b : blob) {
			b.update();
		}
	}
}
