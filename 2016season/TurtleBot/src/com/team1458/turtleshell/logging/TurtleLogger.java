package com.team1458.turtleshell.logging;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class TurtleLogger {
	private static FileWriter fw;
	private static TurtleLogLevel currentLevel;

	public static void initialise(TurtleLogLevel l) {
		if (Files.notExists(Paths.get("/home/lvuser/log"))) {
			try {
				Files.createDirectory(Paths.get("/home/lvuser/log"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			fw = new FileWriter(new File("/home/lvuser/log/" + LocalDateTime.now().toString() + ".log"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		currentLevel=l;
	}

	public static void log(String s, TurtleLogLevel l) {
		if(l.level>=currentLevel.level) {
			//do log
			try {
				fw.write(LocalDateTime.now().toString()+" ["+l.toString()+"] " + s + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void critical(String s) {
		log(s, TurtleLogLevel.CRITICAL);
	}

	public static void severe(String s) {
		log(s, TurtleLogLevel.SEVERE);
	}

	public static void warning(String s) {
		log(s, TurtleLogLevel.WARNING);
	}

	public static void info(String s) {
		log(s, TurtleLogLevel.INFO);
	}

	public static void verbose(String s) {
		log(s, TurtleLogLevel.VERBOSE);
	}

}
