package org.usfirst.frc.team1458.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ScoreAnalyser {
    public static final double areaOverConvexAreaIdeal = 88.0 / 280;
    public static final double perimeterOverConvexPerimeterIdeal = 94.0 / 68;
    public static final double plenimeterIdeal = 92.0 * 92.0 / 88;

    public static final double errorRange = .2;
    public static double areaRange = .2;
    
    public static double periRange = .3;
    public static double pleniRange = .2;
    public static double rectRange = .3;
    
    
    public static boolean isAcceptable(Scores s) {
	boolean areaAcceptable = TurtleMaths.percentError(areaOverConvexAreaIdeal, s.areaToConvexArea) < areaRange;
	SmartDashboard.putNumber("AreaError", TurtleMaths.percentError(areaOverConvexAreaIdeal, s.areaToConvexArea));
	boolean perimeterAcceptable = TurtleMaths.percentError(perimeterOverConvexPerimeterIdeal, s.perimeterToConvexPerimeter) < periRange;
	SmartDashboard.putNumber("PerimeterError", TurtleMaths.percentError(perimeterOverConvexPerimeterIdeal, s.perimeterToConvexPerimeter));
	boolean plenimeterAcceptable = TurtleMaths.percentError(plenimeterIdeal, s.plenimeter) < pleniRange;
	SmartDashboard.putNumber("PlenimeterError", TurtleMaths.percentError(plenimeterIdeal, s.plenimeter));
	boolean rectanglinessAcceptable = TurtleMaths.percentError(s.rectangliness, 1) < rectRange;
	
	areaRange = SmartDashboard.getNumber("areaRange", areaRange);
	periRange = SmartDashboard.getNumber("periRange", periRange);
	pleniRange = SmartDashboard.getNumber("pleniRange", pleniRange);
	rectRange = SmartDashboard.getNumber("rectRange", rectRange);
	SmartDashboard.putNumber("areaRange", areaRange);
	SmartDashboard.putNumber("periRange", periRange);
	SmartDashboard.putNumber("pleniRange", pleniRange);
	SmartDashboard.putNumber("rectRange", rectRange);
	return areaAcceptable && perimeterAcceptable && plenimeterAcceptable && rectanglinessAcceptable;
    }
}
