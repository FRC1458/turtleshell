package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.util.TurtleLogger;

import java.util.Random;

/**
 * @author asinghani
 */
public class Test {
	public static void main(String[] args){
		try {
			TurtleLogger logger = new TurtleLogger(TurtleLogger.PLAINTEXT);
			TurtleLogger.LogServer server = new TurtleLogger.ColoredLogServer(8888, "/");

			logger.attachServer(server);

			Random r = new Random();

			logger.debug("Debug Message "+r.nextInt());
			logger.info("Info Message "+r.nextInt());
			logger.verbose("Verbose Message "+r.nextInt());
			logger.warn("Warning Message "+r.nextInt());
			logger.error("Error Message "+r.nextInt());

			long time = System.currentTimeMillis();

			while(System.currentTimeMillis() - time < 4000);

			logger.debug("Debug Message "+r.nextInt());
			logger.info("Info Message "+r.nextInt());
			logger.verbose("Verbose Message "+r.nextInt());
			logger.warn("Warning Message "+r.nextInt());
			logger.error("Error Message "+r.nextInt());

		} catch(Exception e){

		}
	}
}
