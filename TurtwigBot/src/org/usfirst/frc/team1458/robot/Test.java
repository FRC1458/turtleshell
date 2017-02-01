package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.util.Logger;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author asinghani
 */
public class Test {
	public static void main(String[] args){
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

			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					logger.debug("Spring X = " + BlastoiseVision.getSpringX());
				}
			}, 1000, 1000);

		} catch(Exception e){

		}
	}
}
