package com.team1458.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.team1458.turtleshell.TurtlePID;

public class PIDTest {

	@Test
	public void test() {
		TurtlePID pid = new TurtlePID(.0015,.0001,.0001,5000);
		int dist = 0;
		double motorPower = 0;
		while(!pid.isDone()) {
			motorPower=pid.getNewValue(dist, motorPower*50);
			dist+=50*motorPower;
			System.out.println("D"+dist+"P"+motorPower);
		}
	}

}
