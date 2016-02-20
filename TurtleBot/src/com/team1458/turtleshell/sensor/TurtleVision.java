package com.team1458.turtleshell.sensor;

import com.ni.vision.NIVision.Image;

public interface TurtleVision {
	/**
	 * Whether or not the target has been recognised.
	 * @return
	 */
	public boolean targetRecognised();
	
	/**
	 * Get the image that should be sent to the SmartDashboard
	 * @return The image that should be sent to the SmartDashboard.
	 */
	public Image getImage();
}
