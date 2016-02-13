package com.team1458.turtleshell.vision;

import com.team1458.turtleshell.Input;
import com.team1458.turtleshell.TurtleMaths;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ScoreAnalyser {
	public static final double areaOverConvexAreaIdeal = 76.0 / 240;
	public static final double perimeterOverConvexPerimeterIdeal = 84.5 / 64;
	public static final double plenimeterIdeal = 84.5 * 84.5 / 76;

	public static final double errorRange = .2;
	public static double areaRange = .2;

	public static double periRange = .3;
	public static double pleniRange = .2;
	public static double rectRange = .3;

	public static boolean isAcceptable(Scores s) {
		boolean areaAcceptable = TurtleMaths.percentError(areaOverConvexAreaIdeal, s.areaToConvexArea) < areaRange;
		boolean perimeterAcceptable = TurtleMaths.percentError(perimeterOverConvexPerimeterIdeal,
				s.perimeterToConvexPerimeter) < periRange;
		boolean plenimeterAcceptable = TurtleMaths.percentError(plenimeterIdeal, s.plenimeter) < pleniRange;
		boolean rectanglinessAcceptable = TurtleMaths.percentError(s.rectangliness, 1) < rectRange;

		if (Input.isDebug()) {
			SmartDashboard.putNumber("AreaError",
					TurtleMaths.percentError(areaOverConvexAreaIdeal, s.areaToConvexArea));
			SmartDashboard.putNumber("PerimeterError",
					TurtleMaths.percentError(perimeterOverConvexPerimeterIdeal, s.perimeterToConvexPerimeter));
			SmartDashboard.putNumber("PlenimeterError", TurtleMaths.percentError(plenimeterIdeal, s.plenimeter));
			SmartDashboard.putNumber("RectanglinessError", TurtleMaths.percentError(s.rectangliness, 1));
			SmartDashboard.putNumber("areaRange", areaRange);
			SmartDashboard.putNumber("periRange", periRange);
			SmartDashboard.putNumber("pleniRange", pleniRange);
			SmartDashboard.putNumber("rectRange", rectRange);
			areaRange = SmartDashboard.getNumber("areaRange", areaRange);
			periRange = SmartDashboard.getNumber("periRange", periRange);
			pleniRange = SmartDashboard.getNumber("pleniRange", pleniRange);
			rectRange = SmartDashboard.getNumber("rectRange", rectRange);
		}

		return areaAcceptable && perimeterAcceptable && plenimeterAcceptable && rectanglinessAcceptable;
	}
}
