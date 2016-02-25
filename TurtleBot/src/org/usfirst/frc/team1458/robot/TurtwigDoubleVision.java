package org.usfirst.frc.team1458.robot;

import java.util.ArrayList;
import java.util.List;

import com.ni.vision.NIVision;
import com.ni.vision.NIVision.Image;
import com.ni.vision.NIVision.ImageType;
import com.ni.vision.VisionException;
import com.team1458.turtleshell.logging.TurtleLogger;
import com.team1458.turtleshell.sensor.TurtleVision;
import com.team1458.turtleshell.util.Input;
import com.team1458.turtleshell.util.Input.XboxButton;
import com.team1458.turtleshell.vision.TurtleCameraServer;

import edu.wpi.first.wpilibj.vision.USBCamera;

public class TurtwigDoubleVision implements TurtleVision {
	private static TurtwigDoubleVision instance;
	private List<USBCamera> cameras = new ArrayList<USBCamera>();
	private Image sendImage;
	private int currentCamera = 0;

	public static TurtwigDoubleVision getInstance() {
		if (instance == null) {
			instance = new TurtwigDoubleVision();
		}
		return instance;
	}

	private TurtwigDoubleVision() {
		try {
			sendImage = NIVision.imaqCreateImage(ImageType.IMAGE_RGB, 0);
			cameras.add(new USBCamera("cam0"));
		} catch (Exception e) {
			TurtleLogger.critical("Camera Not found");
			e.printStackTrace();
		}
	}

	private synchronized void setCamera(int i) {
		if (i == currentCamera) {
			// already at that camera
			return;
		}
		if (i >= cameras.size()) {
			// trying to go too big
			TurtleLogger.severe("Requesting to switch to camera that is not created");
			return;
		}
		// disabling current camera
		cameras.get(currentCamera).stopCapture();
		cameras.get(currentCamera).closeCamera();

		// enabling new camera
		cameras.get(i).openCamera();
		cameras.get(i).startCapture();

		currentCamera = i;
	}

	@Override
	public void update() {

		if (Input.getXboxButton(XboxButton.X)) {
			this.setCamera(0);
		}
		if (Input.getXboxButton(XboxButton.Y)) {
			this.setCamera(1);
		}

		try {
			TurtleCameraServer.getInstance().setImage(getImage());
		} catch (Exception e) {
			TurtleLogger.severe("Camera code failed");
		}

	}

	@Override
	public boolean targetRecognised() {
		return false;
	}

	@Override
	public synchronized Image getImage() {
		try {
			cameras.get(currentCamera).getImage(sendImage);
		} catch (NullPointerException e) {
			e.printStackTrace();
			TurtleLogger.critical("Camera does not exist or other null pointer in camera update");
		} catch (VisionException e) {
			e.printStackTrace();
			TurtleLogger.severe("Vision Exception in camera code");
		} catch (Exception e) {
			e.printStackTrace();
			TurtleLogger.severe("Unspecified exception in camera code");
		}

		return sendImage;
	}
}