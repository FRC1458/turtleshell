package com.team1458.test;

import static org.junit.Assert.*;

import org.junit.Test;

public class ByteToIntTest {
	// http://www.cs.umd.edu/~meesh/cmsc311/links/handouts/numbers/node5.html
	@Test
	public void test() {
		byte[] a = new byte[] { (byte) 0b11111111, (byte) 0b11111110 };

		System.out.println(doubleByteToInt(a));
	}

	/**
	 * This monstrosity converts the byte arrays found through the magnetometer into the integer. Don't try to understand it.
	 * @param byteArray MSB, LSB
	 * @return The int representing the two combined, as in signed two's complement format
	 */
	private int doubleByteToInt(byte[] byteArray) {
		return (int) byteArray[0] > 0 ? (byteArray[0] * 256 + Byte.toUnsignedInt(byteArray[1]))
				: Byte.toUnsignedInt(byteArray[0]) * 256 + Byte.toUnsignedInt(byteArray[1]) - 65536;
	}

}
