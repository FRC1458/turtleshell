package com.team1458.turtleshell.vision;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;

import edu.wpi.first.wpilibj.CameraServer;

/**
 * Multiple camera server Extended from
 * https://gist.github.com/Wazzaps/bb9e72696f8980e7e727
 * 
 * @author mehnadnerd
 */
public class TurtleCameraServer {
	private static TurtleCameraServer instance;

	public TurtleCameraServer getInstance() {
		if (instance == null) {
			instance = new TurtleCameraServer();
		}
		return instance;
	}

	private CameraServer cs;

	private TurtleCameraServer() {
		cs = CameraServer.getInstance();
		frame = NIVision.imaqCreateImage(NIVision.ImageType.IMAGE_RGB, 0);
	}

	private int _session;
	private Image frame;
	private int selectedCamera = 0;
	private boolean firstTime = true;

	/**
	 * Send the image to the dashboard
	 */
	public void run() {
		NIVision.IMAQdxGrab(_session, frame, 1);
		cs.setImage(frame);
	}

	/**
	 * Change the current camera
	 * 
	 * @param cam
	 *            new camera
	 */
	public void setCamera(int cam) {

		if (!firstTime) {
			NIVision.IMAQdxStopAcquisition(_session);
			NIVision.IMAQdxCloseCamera(_session);
		} else {
			firstTime = false;
		}
		selectedCamera = cam;
		_session = NIVision.IMAQdxOpenCamera("cam" + selectedCamera,
				NIVision.IMAQdxCameraControlMode.CameraControlModeController);
		NIVision.IMAQdxConfigureGrab(_session);
		NIVision.IMAQdxStartAcquisition(_session);
	}

	/**
	 * Get currently selected camera
	 * 
	 * @return currently selected camera
	 */
	public int getCamera() {
		return selectedCamera;
	}
}
