package com.team1458.turtleshell2.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Logging utilities for the RoboRIO
 *
 * @author asinghani
 */
public class TurtleLogger {

    /**
     * Logs in the form of JSON data
     */
    public static final int JSON = 0;

    /**
     * Logs in plaintext with severity before message
     */
    public static final int PLAINTEXT = 1;

    /**
     * Logs in plaintext with severity shown with color codes
     */
    public static final int COLORED = 2;

    private int mode;


    public static final int VERBOSE = 0;
    public static final int DEBUG = 1;
    public static final int INFO = 2;
    public static final int WARN = 3;
    public static final int ERROR = 4;

    private String[] severityArray = {"VERBOSE", "DEBUG", "INFO", "WARN", "ERROR"};

    private static TurtleLogger instance;

    /**
     * Constructor for TurtleLogger
     *
     * @param mode Mode for log format
     */
    private TurtleLogger(int mode) {
        this.mode = mode;
    }

    /**
     * Get instance of TurtleLogger, to be used project-wide
     * @param mode Mode for log format
     * @return TurtleLogger
     */
    public static TurtleLogger getLogger(int mode) {
        if(instance != null){
            if(instance.mode != mode){
                instance.warn("Trying to reinstantiate with different mode");
            }
            return instance;
        } else {
            instance = new TurtleLogger(mode);
            return instance;
        }
    }

	/**
	 * Get instance of TurtleLogger, to be used project-wide
	 * @return TurtleLogger
	 */
	public static TurtleLogger getLogger() {
		if(instance != null){
			return instance;
		} else {
			instance = new TurtleLogger(PLAINTEXT);
			return instance;
		}
	}

    /**
     * Logs a message
     * @param text
     * @param severity
     */
    public void log(String text, int severity) {
        String timestamp;
        if(mode == JSON){
            timestamp = String.valueOf(new Date().getTime()); // Epoch time in milliseconds
        } else {
            timestamp = new SimpleDateFormat("HH:mm:ss.S").format(new Date()); // Hours (0-23), Minutes, Seconds, Milliseconds
        }
        String severityString = severityArray[severity];

        String logString = "";

        switch(mode) {
            case JSON:
                logString = String.format("{ timestamp: %s, severity: %d, message: \"%s\" }", timestamp, severity, text);
                break;
            case PLAINTEXT:
            case COLORED:
                logString = String.format("%s: [%s] %s", timestamp, severityString, text);
                break;
        }

        if(mode == COLORED) {
            logString = getColorString(severity) + logString + "\u001B[0m";
        }

        System.out.println(logString); // Send to STDOUT
    }

    private String getColorString(int severity) {
        switch(severity) {
            case VERBOSE:
                return "\u001B[97m"; // White

            case DEBUG:
                return "\u001B[32m"; // Green

            case INFO:
                return "\u001B[36m"; // Light Blue

            case WARN:
                return "\u001B[93m"; // Light Yellow

            case ERROR:
                return "\u001B[31m"; // Red

            default:
                return "\u001B[39m"; // Default
        }
    }

    public void verbose(String text){
        log(text, VERBOSE);
    }

    public void debug(String text){
        log(text, DEBUG);
    }
   
    public void info(String text){
        log(text, INFO);
    }

    public void warn(String text){
        log(text, WARN);
    }

    public void error(String text) {
	    log(text, ERROR);
    }
}
