package org.usfirst.frc.team1458.robot;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Particle implements Comparable {

    public final double percentArea;
    public final double area;
    public final double convexArea;
    public final double perimeter;
    public final double convexPerimeter;
    public final double boundingArea;
    
    public final int left;
    public final int top;
    public final int right;
    public final int bottom;
    
    public final int xCentre;
    public final int yCentre;

    public Particle(Image binaryFrame, int particleIndex) {
	percentArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
	area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
	convexArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
	perimeter = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_PERIMETER);
	convexPerimeter = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CONVEX_HULL_PERIMETER);
	boundingArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH)
		* NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
	
	left = (int) Math.round(NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_LEFT));
	top = (int) Math.round(NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_TOP));
	right = (int) Math.round(NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_RIGHT));
	bottom = (int) Math.round(NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_BOUNDING_RECT_BOTTOM));
	
	xCentre = (int) Math.round(NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_X));
	
	yCentre = (int) Math.round(NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_CENTER_OF_MASS_Y));
	SmartDashboard.putNumber("Total Image area", NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_IMAGE_AREA));
    }

    @Override
    public int compareTo(Object o) {
	Particle particle = (Particle) o;

	if (particle.percentArea > percentArea) {
	    return 1;
	}
	return 0;
    }

}