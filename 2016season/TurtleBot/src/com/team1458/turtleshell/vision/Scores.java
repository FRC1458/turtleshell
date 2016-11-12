package com.team1458.turtleshell.vision;

//Structure to represent the scores for the various tests used for target identification
public class Scores {

    public final double areaToConvexArea;
    public final double plenimeter;
    public final double perimeterToConvexPerimeter;
    public final double rectangliness;

    public Scores(Particle p) {

	this.areaToConvexArea = p.area / p.convexArea;
	this.plenimeter = (p.perimeter) * (p.perimeter) / p.area;
	this.perimeterToConvexPerimeter = p.perimeter / p.convexPerimeter;
	this.rectangliness = p.convexArea / p.boundingArea;

    }
}
