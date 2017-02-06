package org.usfirst.frc.team1458.robot.vision;

import edu.wpi.cscore.HttpCamera;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Vision detection code for 2017
 *
 * @author asinghani
 */
public class BlastoiseVision {
	private VideoSource videoSource;
	private static int uniqueId = 0;

	public static final Object lock = new Object();

	private List<Rect> contours = new ArrayList<>();

	/**
	 * Instantiates BlastoiseVision with a VideoSource object. This method is not recommended.
	 * @param videoSource
	 */
	public BlastoiseVision(VideoSource videoSource) {
		this.videoSource = videoSource;
		videoSource.setResolution(RobotConstants.Vision.CAMERA_WIDTH, RobotConstants.Vision.CAMERA_HEIGHT);

		VisionThread visionThread = new VisionThread(videoSource, new DetectTargetPipeline(), pipeline -> {
			synchronized (lock) {
				processContours(pipeline.filterContours0Output());
			}
		});
		visionThread.start();
	}

	/**
	 * Instantiates BlastoiseVision with a MJPG stream url. This is the recommended method.
	 * @param streamUrl
	 */
	public BlastoiseVision(String streamUrl) {
		this(new HttpCamera(streamUrl+uniqueId, streamUrl, HttpCamera.HttpCameraKind.kMJPGStreamer));
		uniqueId++;
	}

	/**
	 * Instantiates BlastoiseVision with a USB camera. USE AT YOUR OWN RISK, THIS HAS A HIGH CHANCE OF CAUSING MEMORY LEAKS!
	 * Also, drivers will not be able to see camera feed on driver station if this is used.
	 * @param cameraNumber
	 */
	public BlastoiseVision(int cameraNumber) {
		this(new UsbCamera(cameraNumber+""+uniqueId, cameraNumber));
	}

	protected void processContours(List<MatOfPoint> data) {
		contours.clear();
		if (!data.isEmpty()) {
			for(MatOfPoint matOfPoint : data) {
				contours.add(Imgproc.boundingRect(matOfPoint));
			}
		}
	}

	public List<Rect> getContours() {
		synchronized (lock) {
			return new ArrayList<>(contours);
		}
	}
}
