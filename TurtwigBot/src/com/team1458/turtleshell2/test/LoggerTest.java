package com.team1458.turtleshell2.test;

import com.team1458.turtleshell2.util.Logger;

import java.util.Random;

/**
 * Test for Logger
 *
 * @author asinghani
 */
public class LoggerTest {
	public static void main(String[] args) {
		try {
			Logger logger = new Logger(Logger.LogFormat.PLAINTEXT);
			Logger.LogServer server = new Logger.ColoredLogServer(8888, "/");

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
			e.printStackTrace();
		}
	}
}
