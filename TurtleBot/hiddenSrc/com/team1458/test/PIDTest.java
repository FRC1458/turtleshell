package com.team1458.test;

import java.util.Random;

import org.junit.Test;

import com.team1458.turtleshell.TurtlePDD2;
import com.team1458.turtleshell.TurtlePDD2Constants;
import com.team1458.turtleshell.TurtlePID;

public class PIDTest {

	@Test
	public void test() {
		
		TurtlePID pid = new TurtlePDD2(new TurtlePDD2Constants(.0015,.0001,.0001),5000,10);
		double dist = 0;
		double motorPower = 0;
		Random r = new Random();
		while(!pid.atTarget()) {
			dist += motorPower;
			System.out.println(dist + "|" + motorPower);
			motorPower = .3*pid.newValue(new double[] { dist, motorPower }).getValue()+.7*motorPower;
			motorPower *= (r.nextDouble() / 5 + .9);
		}
	}

}
