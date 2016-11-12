import java.util.Random;

/**
 * High-Level controller
 *
 * Manages Autonomous/Teleop modes and Control Systems
 *
 * @author asinghani
 */
public class BlastoiseController {
	public static void main(String[] args){
		TurtleDualPID pid = new TurtleStraightDrivePID(new TurtlePIDConstants(.0015, 0, .0001, .0001), 10, 0.00005, 0.01);
		double lSpeed = 0;
		double rSpeed = 0;
		double lDistance = 0;
		double rDistance = 0;
		Random r = new Random();
		MotorValue[] motors;
		double maxDiff = Double.MIN_VALUE;
		while (!pid.atTarget()) {
			lDistance += lSpeed;
			rDistance += rSpeed;
			System.out.println(lDistance + "|" + rDistance);
			motors = pid.newValue(new double[] { lDistance, rDistance, lSpeed, rSpeed });
			lSpeed = .3 * motors[0].getValue() + .7 * lSpeed;
			rSpeed = .3 * motors[1].getValue() + .7 * rSpeed;
			lSpeed *= (r.nextDouble() / 5 + .9);
			rSpeed *= (r.nextDouble() / 5 + .9);
			maxDiff= TurtleMaths.biggerOf(maxDiff, TurtleMaths.absDiff(lDistance, rDistance));
		}
		System.out.println("MaxDiff: "+maxDiff);
	}
}
