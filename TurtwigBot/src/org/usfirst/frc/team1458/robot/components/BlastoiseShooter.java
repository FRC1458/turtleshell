package org.usfirst.frc.team1458.robot.components;

import com.team1458.turtleshell2.implementations.movement.TurtleVictorSP;
import com.team1458.turtleshell2.implementations.pid.PID;
import com.team1458.turtleshell2.implementations.sensor.TurtleHallSensor;
import com.team1458.turtleshell2.interfaces.RobotComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleButtonInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

/**
 * Basic code for a single shooter. Uses RPM internally. Maintains constant motor speed.
 * @author asinghani
 */
public class BlastoiseShooter implements RobotComponent {
	private TurtleHallSensor hallSensor;
	private TurtleMotor shooter;
	private PID pid;

	private TurtleButtonInput shootButton;
	private TurtleButtonInput speedSwitch;

	private double speedRPM;

	public BlastoiseShooter(TurtleHallSensor hallSensor, int motorPort, TurtleButtonInput shootButton, TurtleButtonInput speedSwitch) {
		this.hallSensor = hallSensor;
		this.shooter = new TurtleVictorSP(motorPort);
		this.shootButton = shootButton;
		this.speedSwitch = speedSwitch;

		speedRPM = getRPM();
		pid = new PID(RobotConstants.Shooter.PID_CONSTANTS, speedRPM, 0);
	}

	@Override
	public void teleUpdate() {
		if(shootButton.getButton()){
			if(shootButton.getDown()) {
				speedRPM = getRPM();
				pid = new PID(RobotConstants.Shooter.PID_CONSTANTS, speedRPM, 0);
			}

			if(getRPM() != speedRPM) {
				speedRPM = getRPM();
				pid = new PID(RobotConstants.Shooter.PID_CONSTANTS, speedRPM, 0);
			}

			double motorPower = pid.newValue(hallSensor.getRPM());
			MotorValue motorValue = new MotorValue(motorPower);

			shooter.set(motorValue);

			SmartDashboard.putNumber("RPM"+getSmartDashboardTag(), hallSensor.getRPM());
			SmartDashboard.putNumber("Power"+getSmartDashboardTag(), motorPower);
			SmartDashboard.putNumber("ScaledPower"+getSmartDashboardTag(), motorValue.getValue());
		} else {
			shooter.set(MotorValue.zero);
		}
	}

	public double getRPM() {
		return speedSwitch.getButton() ? RobotConstants.Shooter.LOW_RPM : RobotConstants.Shooter.HIGH_RPM;
	}

	public String getSmartDashboardTag() {
		return " ("+toString()+")";
	}
}
