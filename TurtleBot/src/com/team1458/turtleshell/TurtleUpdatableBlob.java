package com.team1458.turtleshell;

import java.util.ArrayList;

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
