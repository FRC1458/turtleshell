package com.team1458.turtleshell2.util;

public class TurtlePIDConstants {
	public final double kP;
	public final double kI;
	public final double kD;
	public final double kDD;
	
	public TurtlePIDConstants(double kP, double kI, double kD, double kDD) {
		this.kP=kP;
		this.kI=kI;//kI is for intergral
		this.kD=kD;//kD is derititive
		this.kDD=kDD;//kDD is double derivitive
	}

}
