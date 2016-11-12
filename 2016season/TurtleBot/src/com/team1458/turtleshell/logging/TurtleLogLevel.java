package com.team1458.turtleshell.logging;

public enum TurtleLogLevel implements Comparable<TurtleLogLevel> {

	CRITICAL(4), SEVERE(3), WARNING(2), INFO(1), VERBOSE(0);
	private TurtleLogLevel(int i) {
		level = i;
	}

	public int level;

	public static TurtleLogLevel levelFromInt(int i) {
		switch (i) {
		case 4:
			return CRITICAL;
		case 3:
			return SEVERE;
		case 2:
			return WARNING;
		case 1:
			return INFO;
		case 0:
			return VERBOSE;
		default:
			return WARNING;
		}
	}
}
