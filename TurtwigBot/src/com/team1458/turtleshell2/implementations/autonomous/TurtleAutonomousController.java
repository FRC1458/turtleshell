package com.team1458.turtleshell2.implementations.autonomous;

import com.team1458.turtleshell2.interfaces.Chassis;
import com.team1458.turtleshell2.util.types.MotorValue;

import java.util.ArrayList;

/**
 * Command-based controller for autonomous mode.
 * All commands are added to the queue and only run when the run() method is called.
 *
 * @author asinghani
 */
public class TurtleAutonomousController {
	Chassis chassis;

	public ArrayList<Runnable> commands;

	public TurtleAutonomousController(Chassis chassis) {
		this.chassis = chassis;
	}

	/**
	 * Move robot forward/backward for a certain amount of time at a certain speed.
	 * No PID controller or other stabilization methods are used.
	 *
	 * @param millis Amount of time for the robot to drive
	 * @param speed Speed for the robot, between -1 and 1
	 */
	public void moveMillis(long millis, double speed) {
		MotorValue motorValue = new MotorValue(speed);

		commands.add(() -> {
			chassis.updateMotors(motorValue, motorValue);
			try{
				Thread.sleep(millis);
			} catch(Exception e) {}

			chassis.stopMotors();
		});
	}

	/**
	 * Turn for a certain amount of time at a certain speed.
	 * Positive speeds represent right, negative represents left turning.
	 * No PID controller or other stabilization methods are used.
	 *
	 * @param millis Amount of time for the robot to turn
	 * @param speed Speed for the robot, between -1 and 1
	 */
	public void turnMillis(long millis, double speed) {
		MotorValue right = new MotorValue(speed);
		MotorValue left = new MotorValue(-1 * speed);

		commands.add(() -> {
			chassis.updateMotors(left, right);
			try{
				Thread.sleep(millis);
			} catch(Exception e) {}

			chassis.stopMotors();
		});
	}

	/**
	 * Run all commands in queue
	 */
	public synchronized void run() {
		for(int i = commands.size() - 1; i >= 0; i--){
			commands.get(i).run();
		}
		clear();
	}

	/**
	 * Delete all commands from the queue
	 */
	public void clear() {
		commands.clear();
	}
}
