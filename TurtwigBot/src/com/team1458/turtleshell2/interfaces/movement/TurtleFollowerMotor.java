package com.team1458.turtleshell2.interfaces.movement;

/**
 * A motor which can follow other motors without seperate signals being sent
 */
public interface TurtleFollowerMotor extends TurtleMotor {
	void follow(int masterId);

	int getID();
}
