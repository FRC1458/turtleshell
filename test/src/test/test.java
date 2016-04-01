package test;

import java.util.ArrayList;
import java.util.List;

public class test {
	public static void main(String[] args) {
		String s = "Turtwig";
		A b = new B();
		
		System.out.println((I2)b);
	}
	private static class A {
		
	}
	private static class B extends A implements I{
		
	}
	
	private static interface I {
		
	}
	
	private static interface I2 {
		
	}
}
