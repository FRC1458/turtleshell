package org.usfirst.frc.team1458.robot;

public class ScoreAnalyser {
    public static final double areaOverConvexAreaIdeal = 88.0 / 280;
    public static final double perimeterOverConvexPerimeterIdeal = 94.0 / 68;
    public static final double plenimeterIdeal = 92.0 * 92.0 / 88;

    public static final double errorRange = .2;

    public static boolean isAcceptable(Scores s) {
	boolean areaAcceptable = TurtleMaths.percentError(areaOverConvexAreaIdeal, s.areaToConvexArea) < errorRange;
	boolean perimeterAcceptable = TurtleMaths.percentError(perimeterOverConvexPerimeterIdeal, s.perimeterToConvexPerimeter) < errorRange;
	boolean plenimeterAcceptable = TurtleMaths.percentError(plenimeterIdeal, s.plenimeter) < errorRange;
	boolean rectanglinessAcceptable = TurtleMaths.percentError(s.rectangliness, 1) < errorRange;
	return areaAcceptable && perimeterAcceptable && plenimeterAcceptable && rectanglinessAcceptable;
    }
}
