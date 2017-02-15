package org.usfirst.frc.team1458.robot.vision;

import edu.wpi.cscore.HttpCamera;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team1458.robot.Constants;

import java.io.IOException;
import java.util.*;

/**
 * Vision detection code for 2017
 *
 * @author asinghani
 */
public class BlastoiseShooterVision {
	private VideoSource videoSource;
	private static int uniqueId = 0;

	public static final Object lock = new Object();

	private List<Rect> contours = new ArrayList<>();

	/**
	 * Instantiates BlastoiseVision with a VideoSource object. This method is not recommended.
	 * @param videoSource
	 */
	public BlastoiseShooterVision(VideoSource videoSource) {
		this.videoSource = videoSource;
		videoSource.setResolution(Constants.ShooterVision.Camera.WIDTH_PX, Constants.ShooterVision.Camera.HEIGHT_PX);
		
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
	public BlastoiseShooterVision(String streamUrl) {
		this(new HttpCamera(streamUrl+uniqueId, streamUrl, HttpCamera.HttpCameraKind.kMJPGStreamer));
		uniqueId++;
		try {
			CameraSetup.initialSetup("localhost", 5800);
			CameraSetup.startVision("localhost", 5800);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Instantiates BlastoiseVision with a USB camera. USE AT YOUR OWN RISK, THIS CAUSES MEMORY LEAKS!
	 * @param cameraNumber
	 */
	public BlastoiseShooterVision(int cameraNumber) {
		this(new UsbCamera(cameraNumber+""+uniqueId, cameraNumber));
	}

	protected void processContours(List<MatOfPoint> data) {
		contours.clear();
		if (!data.isEmpty()) {
			for(MatOfPoint matOfPoint : data) {
				contours.add(Imgproc.boundingRect(matOfPoint));
				matOfPoint.release();
			}
		}
	}

	public ArrayList<Rect> getContours() {
		synchronized (lock) {
			return new ArrayList<>(contours);
		}
	}

	public double getShooterTargetDistance() {
		ArrayList<Rect> contours = getCorrectContours();
		if(contours.size() != 2){
			return -1;
		}

		double x1 = contours.get(0).x + (contours.get(0).width/2.0);
		double x2 = contours.get(1).x + (contours.get(1).width/2.0);

		double y1 = contours.get(0).y + (contours.get(0).height/2.0);
		double y2 = contours.get(1).y + (contours.get(1).height/2.0);

		double xCoord = (x1 + x2) / 2.0;
		double yCoord = (y1 + y2) / 2.0;

		SmartDashboard.putNumber("X of target", xCoord);
		SmartDashboard.putNumber("Y of target", yCoord);

		double cameraHeightDifference = (78 - Constants.ShooterVision.Camera.MOUNT_HEIGHT);
		double pixelDifference = (Constants.ShooterVision.Camera.HEIGHT_PX - yCoord) / Constants.ShooterVision.Camera.HEIGHT_PX;
		double angle = Constants.ShooterVision.Camera.MOUNT_ANGLE +
				(Constants.ShooterVision.Camera.HEIGHT_FOV * pixelDifference);
		angle = Math.toRadians(angle);
		double distance = cameraHeightDifference * (1 / Math.tan(angle));

		SmartDashboard.putNumber("DistanceToBoiler", distance);

		return distance;
	}

	public ArrayList<Rect> getCorrectContours() {
		ArrayList<Rect> contours = getContours();
		if(contours.size() < 2){
			return new ArrayList<>();
		}

		Collections.sort(contours, (Rect r1, Rect r2) -> ((int) (r1.area() - r2.area())));
		Collections.reverse(contours);
		Rect contour1 = contours.get(0);
		Rect contour2 = new Rect(100000, 100000, 100000, 100000);
		for(int i = 1; i < contours.size(); i++) {
			if(Math.abs(contour1.x - contour2.x) > Math.abs(contour1.x - contours.get(i).x)){
				contour2 = contours.get(i);
			}
		}
		
		System.out.println(contour1.toString() + ", "+contour2.toString());
		SmartDashboard.putString("ContoursInfo", contour1.toString() + ", "+contour2.toString());
		
		return new ArrayList<>(Arrays.asList(new Rect[]{contour1, contour2}));
	}

	public double getShooterTargetX() {
		ArrayList<Rect> contours = getCorrectContours();
		if(contours.size() != 2){
			return -1;
		}

		double x1 = contours.get(0).x + (contours.get(0).width/2.0);
		double x2 = contours.get(1).x + (contours.get(1).width/2.0);

		double y1 = contours.get(0).y + (contours.get(0).height/2.0);
		double y2 = contours.get(1).y + (contours.get(1).height/2.0);

		double xCoord = (x1 + x2) / 2.0;
		double yCoord = (y1 + y2) / 2.0;

		SmartDashboard.putNumber("X of target", xCoord);
		SmartDashboard.putNumber("Y of target", yCoord);
	

		return xCoord;
	}
	
	public void close() {
		videoSource.free();
	}
}
