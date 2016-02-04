package com.team1458.turtleshell.vision;

public class Particle {
	public final double percentArea;
	public final double area;
	public final double convexArea;
	public final double left;
	public final double top;
	public final double right;
	public final double bottom;
	
	public Particle(double percentArea, double area, double convexArea, double left, double top, double right, double bottom) {
		this.percentArea = percentArea;
		this.area = area;
		this.convexArea = convexArea;
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
}
