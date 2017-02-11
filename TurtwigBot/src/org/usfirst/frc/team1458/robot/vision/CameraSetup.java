package org.usfirst.frc.team1458.robot.vision;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Some utilities for configuring the camera for starting vision, this is very important stuff
 *
 * @author asinghani
 */
public class CameraSetup {
	protected static void initialSetup(String hostname, int port) throws IOException {
		get("http://localhost:"+5800+"/?action=command&dest=0&plugin=0&id=9963776&group=1&value=30");
		get("http://localhost:"+5800+"/?action=command&dest=0&plugin=0&id=9963776&group=1&value=30");
		get("http://localhost:"+5800+"/?action=command&dest=0&plugin=0&id=9963776&group=1&value=30");
		startHumanUsage(hostname, port);
	}

	protected static void startVision(String hostname, int port) throws IOException {
		get("http://localhost:"+5800+"/?action=command&dest=0&plugin=0&id=10094849&group=1&value=1");
		get("http://localhost:"+5800+"/?action=command&dest=0&plugin=0&id=10094850&group=1&value=5");
	}

	protected static void startHumanUsage(String hostname, int port) throws IOException {
		get("http://localhost:"+5800+"/?action=command&dest=0&plugin=0&id=10094849&group=1&value=1");
		get("http://localhost:"+5800+"/?action=command&dest=0&plugin=0&id=10094849&group=1&value=3");
	}

	protected static void get(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		connection.connect();
	}

	protected static void get(String url) throws IOException {
		get(new URL(url));
	}
}
