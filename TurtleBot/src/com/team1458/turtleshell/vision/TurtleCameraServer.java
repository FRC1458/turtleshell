package com.team1458.turtleshell.vision;

import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.sensor.TurtleVision;

import edu.wpi.first.wpilibj.CameraServer;

/**
 * Multiple camera server Extended from
 * https://gist.github.com/Wazzaps/bb9e72696f8980e7e727
 * 
 * @author mehnadnerd
 */
public class TurtleCameraServer implements Runnable {
	private static TurtleCameraServer instance;

	public static TurtleCameraServer getInstance() {
		if (instance == null) {
			instance = new TurtleCameraServer();
		}
		return instance;
	}

	private CameraServer cs;
	
	private TurtleVision v;

	private TurtleCameraServer() {
		cs = CameraServer.getInstance();
		new Thread(this).start();
	}

	/**
	 * Send the image to the dashboard
	 */
	@Override
	public void run() {
		while(true) {
			try {
				cs.setImage(v.getImage());
			} catch (NullPointerException e) {
				TurtleLogger.warning("Vision not providing image");
			}
			
			try {
				wait(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setVision(TurtleVision v) {
		this.v=v;
	}
}
