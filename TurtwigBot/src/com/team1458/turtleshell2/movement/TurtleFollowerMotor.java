package com.team1458.turtleshell2.movement;

/**
 * A motor which can follow other motors without seperate signals being sent
 */
public interface TurtleFollowerMotor extends TurtleMotor {
	/**
	 * Set this motor to follow another Motor
	 * @param master The TurtleFollowerMotor to follow
	 */
	void follow(TurtleFollowerMotor master);

	int getID();
}
