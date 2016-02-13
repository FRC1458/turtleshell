package com.team1458.turtleshell.vision;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

public class Particle implements Comparable {

	public final double percentArea;
	public final double area;
	public final double convexArea;
	public final double perimeter;
	public final double convexPerimeter;
	public final double boundingArea;

	public final double xCentre;
	public final double yCentre;

	public Particle(Image binaryFrame, int particleIndex) {
		percentArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
				NIVision.MeasurementType.MT_AREA_BY_IMAGE_AREA);
		area = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_AREA);
		convexArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
				NIVision.MeasurementType.MT_CONVEX_HULL_AREA);
		perimeter = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0, NIVision.MeasurementType.MT_PERIMETER);
		convexPerimeter = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
				NIVision.MeasurementType.MT_CONVEX_HULL_PERIMETER);
		boundingArea = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
				NIVision.MeasurementType.MT_BOUNDING_RECT_WIDTH)
				* NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
						NIVision.MeasurementType.MT_BOUNDING_RECT_HEIGHT);
		xCentre = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
				NIVision.MeasurementType.MT_CENTER_OF_MASS_X);
		yCentre = NIVision.imaqMeasureParticle(binaryFrame, particleIndex, 0,
				NIVision.MeasurementType.MT_CENTER_OF_MASS_Y);
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