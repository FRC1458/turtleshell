package org.usfirst.frc.team1458.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ScoreAnalyser {
    public static final double areaOverConvexAreaIdeal = 88.0 / 280;
    public static final double perimeterOverConvexPerimeterIdeal = 94.0 / 68;
    public static final double plenimeterIdeal = 92.0 * 92.0 / 88;

    public static final double errorRange = .2;

    public static boolean isAcceptable(Scores s) {
	boolean areaAcceptable = TurtleMaths.percentError(areaOverConvexAreaIdeal, s.areaToConvexArea) < errorRange;
	SmartDashboard.putNumber("AreaError", TurtleMaths.percentError(areaOverConvexAreaIdeal, s.areaToConvexArea));
	boolean perimeterAcceptable = TurtleMaths.percentError(perimeterOverConvexPerimeterIdeal, s.perimeterToConvexPerimeter) < errorRange;
	SmartDashboard.putNumber("PerimeterError", TurtleMaths.percentError(perimeterOverConvexPerimeterIdeal, s.perimeterToConvexPerimeter));
	boolean plenimeterAcceptable = TurtleMaths.percentError(plenimeterIdeal, s.plenimeter) < errorRange;
	SmartDashboard.putNumber("PlenimeterError", TurtleMaths.percentError(plenimeterIdeal, s.plenimeter));
	boolean rectanglinessAcceptable = TurtleMaths.percentError(s.rectangliness, 1) < errorRange;
	SmartDashboard.putNumber("RectanglinessError", TurtleMaths.percentError(s.rectangliness, 1));
	return areaAcceptable && perimeterAcceptable && plenimeterAcceptable && rectanglinessAcceptable;
    }
}
