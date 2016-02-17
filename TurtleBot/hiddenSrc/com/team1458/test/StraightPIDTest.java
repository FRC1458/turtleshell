package com.team1458.test;


import java.util.Random;

import org.junit.Test;

import com.team1458.turtleshell.MotorValue;
import com.team1458.turtleshell.TurtleMaths;
import com.team1458.turtleshell.pid.TurtleDualPID;
import com.team1458.turtleshell.pid.TurtlePDD2Constants;
import com.team1458.turtleshell.pid.TurtleStraightDrivePID;

public class StraightPIDTest {

	@Test
	public void test() {
		TurtleDualPID pid = new TurtleStraightDrivePID(new TurtlePDD2Constants(.0015, .0001, .0001), 5000, 0.00005);
		double lSpeed = 0;
		double rSpeed = 0;
		double lDistance = 0;
		double rDistance = 0;
		Random r = new Random();
		MotorValue[] motors;
		double maxDiff = Double.MIN_VALUE;
		while (!pid.atTarget()) {
			lDistance += lSpeed;
			rDistance += rSpeed;
			System.out.println(lDistance + "|" + rDistance);
			motors = pid.newValue(new double[] { lDistance, rDistance, lSpeed, rSpeed });
			lSpeed = .3 * motors[0].getValue() + .7 * lSpeed;
			rSpeed = .3 * motors[1].getValue() + .7 * rSpeed;
			lSpeed *= (r.nextDouble() / 5 + .9);
			rSpeed *= (r.nextDouble() / 5 + .9);
			maxDiff=TurtleMaths.biggerOf(maxDiff, TurtleMaths.absDiff(lDistance, rDistance));
		}
		System.out.println("MaxDiff: "+maxDiff);
		assert(maxDiff<30);
	}

}
