package com.team1458.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.team1458.turtleshell.TurtleMaths.RangeShifter;

public class RangeShifterTest {

	@Test
	public void test() {
		RangeShifter shift = new RangeShifter(500, 1500, -1, 1);
		System.out.println(shift.shift(1000));
		
	}

}
