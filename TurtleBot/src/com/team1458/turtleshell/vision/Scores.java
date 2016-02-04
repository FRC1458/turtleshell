package com.team1458.turtleshell.vision;

public class Scores {
	public final double trapezoid;
	public final double longAspect;
	public final double shortAspect;
	public final double areaToConvexArea;
	
	public Scores(double trapezoid, double longAspect, double shortAspect, double areaToConvexArea) {
		this.trapezoid = trapezoid;
		this.longAspect = longAspect;
		this.shortAspect = shortAspect;
		this.areaToConvexArea = areaToConvexArea;
	}
}
