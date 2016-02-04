package org.usfirst.frc.team1458.robot;

import java.util.Comparator;



public class Particle {
	double PercentAreaToImageArea;
	double Area;
	double BoundingRectLeft;
	double BoundingRectTop;
	double BoundingRectRight;
	double BoundingRectBottom;
	
	public int compareTo(Particle r)
	{
		return (int)(r.Area - this.Area);
	}
	
	public int compare(Particle r1, Particle r2)
	{
		return (int)(r1.Area - r2.Area);
	}
}