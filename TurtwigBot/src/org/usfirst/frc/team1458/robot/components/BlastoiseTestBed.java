package org.usfirst.frc.team1458.robot.components;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.team1458.turtleshell2.implementations.drive.TurtleMotorSet;
import com.team1458.turtleshell2.implementations.input.TurtleFlightStick;
import com.team1458.turtleshell2.implementations.input.TurtleXboxController;
import com.team1458.turtleshell2.implementations.movement.TurtleFakeMotor;
import com.team1458.turtleshell2.implementations.movement.TurtleTalonSRXCAN;
import com.team1458.turtleshell2.interfaces.TurtleComponent;
import com.team1458.turtleshell2.interfaces.input.TurtleAnalogInput;
import com.team1458.turtleshell2.interfaces.movement.TurtleMotor;
import com.team1458.turtleshell2.util.TurtleLogger;
import com.team1458.turtleshell2.util.TurtleMaths;
import com.team1458.turtleshell2.util.types.MotorValue;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team1458.robot.constants.RobotConstants;

/**
 * Not to be used at competition
 *
 * @author asinghani
 */
public class BlastoiseTestBed implements TurtleComponent {
    TurtleLogger logger;
    boolean disabled = false;

    /**
     * Test bed for shooter
     */
    TurtleAnalogInput shooterSpeed;
    
    TurtleMotor shooter = new TurtleFakeMotor(RobotConstants.Shooter.MOTOR_PORT);
    
    TurtleMotorSet talons = new TurtleMotorSet(new TurtleTalonSRXCAN(12));
    		//new TurtleTalonSRXCAN(11), new TurtleTalonSRXCAN(15));
    CANTalon a = new CANTalon(11);
    CANTalon b = new CANTalon(15);

    {
    	a.changeControlMode(TalonControlMode.Follower);
    	a.set(12);
    	b.changeControlMode(TalonControlMode.Follower);
    	b.set(12);
    }
    
    public BlastoiseTestBed(TurtleLogger logger, TurtleFlightStick rightStick, TurtleFlightStick leftStick) {
        this.logger = logger;

        shooterSpeed = rightStick.getAxis(TurtleFlightStick.FlightAxis.THROTTLE);

        disabled = DriverStation.getInstance().isFMSAttached();
        if(disabled) logger.warn("BlastoiseTestBed was instantiated while FMS enabled");
    }

    public BlastoiseTestBed(TurtleLogger logger, TurtleXboxController xboxController) {
        this.logger = logger;

        shooterSpeed = xboxController.getAxis(TurtleXboxController.XboxAxis.RT);

        disabled = DriverStation.getInstance().isFMSAttached();
        if(disabled) logger.warn("BlastoiseTestBed was instantiated while FMS enabled");
    }

    @Override
    public void teleUpdate() {
        if(disabled) return;
        //shooter.set(new MotorValue(TurtleMaths.deadband(shooterSpeed.get(), RobotConstants.JOYSTICK_DEADBAND)));

        talons.set(new MotorValue(TurtleMaths.deadband(shooterSpeed.get(), RobotConstants.JOYSTICK_DEADBAND)));
        SmartDashboard.putNumber("Shooter Speed", shooterSpeed.get());
    }
}
