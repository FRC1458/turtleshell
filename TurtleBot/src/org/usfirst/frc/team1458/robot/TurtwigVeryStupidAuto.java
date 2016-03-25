package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell.core.TurtleAutonomous;
import com.team1458.turtleshell.core.TurtlePhysicalRobot;
import com.team1458.turtleshell.util.MotorValue;
import com.team1458.turtleshell.util.TurtleSafeDriverStation;

import edu.wpi.first.wpilibj.Timer;

public class TurtwigVeryStupidAuto implements TurtleAutonomous {
    private TurtlePhysicalRobot phys;

    @Override
    public void giveRobot(TurtlePhysicalRobot physicalRobot) {
	this.phys = physicalRobot;
    }

    @Override
    public void doAuto() {
	((TurtwigSmartTankChassis) phys.getComponent("Chassis")).forceMotors(new MotorValue[] { MotorValue.fullForward, new MotorValue(0.97) });
	Timer t = new Timer();
	t.start();
	while(t.get()<3.62&&TurtleSafeDriverStation.canAuto()) {
	    //wait
	    phys.updateAll();
	}
	phys.stopAll();
    }

}
