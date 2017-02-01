package com.team1458.turtleshell2.util;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.team1458.turtleshell2.util.HttpSnippets.*;

/**
 * Logging utilities for the RoboRIO
 *
 * @author asinghani
 */
public class Logger {
	
	public static enum LogFormat {
		JSON, PLAINTEXT, COLOURED
	}

    private LogFormat mode;

    public ArrayList<LogServer> logServers = new ArrayList<>();

    private static String[] severityArray = {"VERBOSE", "DEBUG", "INFO", "WARN", "ERROR"};

    private static Logger instance;
    
    public static Logger getInstance() {
    	if(instance==null) {
    		instance = new Logger(LogFormat.PLAINTEXT);
    	}
    	return instance;
    }

    /**
     * Constructor for Logger
     *
     * @param mode Mode for log format
     */
    public Logger(LogFormat mode) {
        this.mode = mode;
    }

    /**
     * Logs a message
     * @param text
     * @param severity
     */
    public void log(String text, int severity) {
        String timestamp;
        if(mode == LogFormat.JSON){
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
            case COLOURED:
                logString = String.format("%s: [%s] %s", timestamp, severityString, text);
                break;
        }

        if(mode == LogFormat.COLOURED) {
            logString = getColorString(severity) + logString + "\u001B[0m";
        }

        for(LogServer server : logServers){
            server.log(timestamp, text, severity);
        }

        System.out.println(logString); // Send to STDOUT
    }

    public void attachServer(LogServer server){
        logServers.add(server);
    }

    private String getColorString(int severity) {
        switch(severity) {
            case LogEvent.VERBOSE:
                return "\u001B[97m"; // White

            case LogEvent.DEBUG:
                return "\u001B[32m"; // Green

            case LogEvent.INFO:
                return "\u001B[36m"; // Light Blue

            case LogEvent.WARN:
                return "\u001B[93m"; // Light Yellow

            case LogEvent.ERROR:
                return "\u001B[31m"; // Red

            default:
                return "\u001B[39m"; // Default
        }
    }

    public void verbose(String text){
        log(text, LogEvent.VERBOSE);
    }

    public void debug(String text){
        log(text, LogEvent.DEBUG);
    }
   
    public void info(String text){
        log(text, LogEvent.INFO);
    }

    public void warn(String text){
        log(text, LogEvent.WARN);
    }

    public void error(String text) {
	    log(text, LogEvent.ERROR);
    }

    static class LogEvent {
        public static final int VERBOSE = 0;
        public static final int DEBUG = 1;
        public static final int INFO = 2;
        public static final int WARN = 3;
        public static final int ERROR = 4;

        public final String text;
        public final int severity;
        public final String timestamp;

        public LogEvent(String timestamp, String text, int severity) {
            this.text = text;
            this.severity = severity;
            this.timestamp = timestamp;
        }
    }

    public static abstract class LogServer implements HttpHandler {

        ArrayList<LogEvent> events = new ArrayList<>();

        public void log(String timestamp, String text, int severity) {
            events.add(new LogEvent(timestamp, text, severity));
        }

        @Override
        public abstract void handle(HttpExchange httpExchange) throws IOException;
    }

    public static class PlainTextLogServer extends LogServer {
        String path;

        public PlainTextLogServer(int port, String path) throws IOException{
            this.path = path;

            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext(path, this);
            server.setExecutor(null);
            server.start();
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = logHead.replace("$REFRESHSCRIPT", refreshScript.replace("$PATH", path));

            for(LogEvent event : events){
                response += String.format("%s: [%s] %s<br>", event.timestamp, severityArray[event.severity], event.text);
            }

            response += logEnd;

            t.sendResponseHeaders(200, response.length());
            OutputStream out = t.getResponseBody();
            out.write(response.getBytes());
            out.close();
        }
    }

    public static class JSONLogServer extends LogServer {
        String path;

        public JSONLogServer(int port, String path) throws IOException{
            this.path = path;

            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext(path, this);
            server.setExecutor(null);
            server.start();
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = logHead.replace("$REFRESHSCRIPT", refreshScript.replace("$PATH", path));

            for(LogEvent event : events){
                try {
                    response += String.format("{\"timestamp\": \"%d\", \"severity\": \"%d\", \"message\": \"%s\"},<br>", new SimpleDateFormat("HH:mm:ss.S").parse(event.timestamp).getTime(), event.severity, event.text);
                } catch (Exception e){
                    System.out.println(e);
                }
            }

            response += logEnd;

            t.sendResponseHeaders(200, response.length());
            OutputStream out = t.getResponseBody();
            out.write(response.getBytes());
            out.close();
        }
    }

    public static class ColoredLogServer extends LogServer {
        String path;

        public ColoredLogServer(int port, String path) throws IOException{
            this.path = path;

            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            server.createContext(path, this);
            server.setExecutor(null);
            server.start();
        }

        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = HttpSnippets.logHead.replace("$REFRESHSCRIPT", refreshScript.replace("$PATH", path));

            for(LogEvent event : events){
                try {
                    response += String.format("<span style=\"color:%s\">%s: [<b>%s</b>] %s</span><br>", getColor(event.severity), event.timestamp, severityArray[event.severity], event.text);
                } catch (Exception e){
                    System.out.println(e);
                }
            }

            response += logEnd;

            t.sendResponseHeaders(200, response.length());
            OutputStream out = t.getResponseBody();
            out.write(response.getBytes());
            out.close();
        }

        private String getColor(int severity){
            switch(severity) {
                case LogEvent.VERBOSE:
                    return "white";

                case LogEvent.DEBUG:
                    return "#00bc8c";

                case LogEvent.INFO:
                    return "#3498db";

                case LogEvent.WARN:
                    return "#f39c12";

                case LogEvent.ERROR:
                    return "#e74c3c";

                default:
                    return "white";
            }
        }
    }
}
