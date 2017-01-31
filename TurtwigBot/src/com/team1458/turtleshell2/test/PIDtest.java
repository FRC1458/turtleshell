package com.team1458.turtleshell2.test;

import com.team1458.turtleshell2.pid.PID;
import com.team1458.turtleshell2.util.PIDConstants;
import com.team1458.turtleshell2.util.TurtleMaths;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Unit Test for PID
 *
 * @author asinghani
 */
public class PIDtest {
	public static void main(String[] args) throws Exception {
		final int target = 90;
		final PIDConstants constants = new PIDConstants(1.0/90.0, 0.3/90.0, 0.1/90.0);

		PID pid = new PID(constants, target, 5);

		List<Double> pidHistory = new ArrayList<>();
		List<Double> positionHistory = new ArrayList<>();

		/**
		 * Simulation
		 */
		Random r = new Random();
		double position = 0;
		do {
			double pidValue = pid.newValue(position);

			pidValue = TurtleMaths.fitRange(pidValue, -1, 1);

			position += pidValue;

			System.out.println("position="+position+" pidValue="+pidValue);

			Thread.sleep(10);

			pidHistory.add(pidValue);
			positionHistory.add(position);

			position += 3*(r.nextDouble()-0.5);

		} while (!pid.atTarget());

		System.out.println("{"+pidHistory.toString().replace("[", "{").replace("]", "}")+","+
				positionHistory.toString().replace("[", "{").replace("]", "}")+"}");
	}
}
