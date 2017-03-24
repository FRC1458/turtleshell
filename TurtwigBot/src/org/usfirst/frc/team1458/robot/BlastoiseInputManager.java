package org.usfirst.frc.team1458.robot;

import com.team1458.turtleshell2.input.*;
import com.team1458.turtleshell2.input.FlightStick.FlightButton;
import com.team1458.turtleshell2.input.XboxController.XboxAxis;
import com.team1458.turtleshell2.input.XboxController.XboxButton;
import com.team1458.turtleshell2.input.fake.FakeButtonInput;
import com.team1458.turtleshell2.input.fake.FakeRumbleable;
import com.team1458.turtleshell2.util.TurtleMaths;

/**
 * Manages all input/control for Robot
 *
 * @author asinghani
 */
public class BlastoiseInputManager implements Rumbleable {
	final AnalogInput leftJoystick;
	final AnalogInput rightJoystick;

	final ButtonInput straightButton;
	final ButtonInput turnButton;

	final ButtonInput slowButton;

	final ButtonInput alignShooterButton;

	final ButtonInput climberSwitch;
	final DigitalInput intakeSwitch;
	final ButtonInput shootButton;

	final DigitalInput shooterSpeed;

	final TurtleJoystickPOVSwitch pov;
	
	final Rumbleable rumbleController;

	final ButtonInput autoManualToggle;

	final ButtonInput panicButton;

	final ButtonInput gearButton;

	final ButtonInput agitateButton;

	public BlastoiseInputManager(FlightStick leftStick, FlightStick rightStick, final BlastoiseController blastoiseController) {
		this.leftJoystick = leftStick.getAxis(FlightStick.FlightAxis.PITCH);
		this.rightJoystick = rightStick.getAxis(FlightStick.FlightAxis.PITCH);

		this.turnButton = leftStick.getButton(FlightStick.FlightButton.TRIGGER);
		this.straightButton = rightStick.getButton(FlightStick.FlightButton.TRIGGER);

		this.slowButton = new MultiButtonInput(MultiButtonInput.Operator.OR,
				rightStick.getButton(FlightStick.FlightButton.TWO),
				leftStick.getButton(FlightStick.FlightButton.TWO));

		this.alignShooterButton = rightStick.getButton(FlightStick.FlightButton.FIVE);

		this.pov = rightStick.getPOVSwitch();
		this.rumbleController = new FakeRumbleable();

		this.climberSwitch = blastoiseController.getClimber();

		// This block of code changes analog value to digital integer between 0 and 2
		this.intakeSwitch = () -> {
			if(TurtleMaths.absDiff(blastoiseController.getIntake().get(), -1) < 0.5) {
				return 2;
			} else if(TurtleMaths.absDiff(blastoiseController.getIntake().get(), 1) < 0.5) {
				return 1;
			} else {
				return 0;
			}
		};

		this.shootButton = blastoiseController.getShooterToggle();
		this.agitateButton = blastoiseController.getFeeder();

		// This code changes shooter speed analog input to integer between 0 and 11
		this.shooterSpeed = () -> (int) Math.round(TurtleMaths.shift(blastoiseController.getShooterSpeed().get(), -1, 1, 0, 11));
		this.autoManualToggle = blastoiseController.getShooterAutoManual();
		this.panicButton = blastoiseController.getPanic();

		gearButton = new MultiButtonInput(MultiButtonInput.Operator.OR,
				rightStick.getButton(FlightStick.FlightButton.THREE),
				leftStick.getButton(FlightStick.FlightButton.THREE));

	}

	public BlastoiseInputManager(FlightStick leftStick, FlightStick rightStick, final XboxController xbox) {
		this.leftJoystick = leftStick.getAxis(FlightStick.FlightAxis.PITCH);
		this.rightJoystick = rightStick.getAxis(FlightStick.FlightAxis.PITCH);

		this.turnButton = leftStick.getButton(FlightStick.FlightButton.TRIGGER);
		this.straightButton = rightStick.getButton(FlightStick.FlightButton.TRIGGER);

		this.slowButton = new MultiButtonInput(MultiButtonInput.Operator.OR,
				rightStick.getButton(FlightStick.FlightButton.TWO),
				leftStick.getButton(FlightStick.FlightButton.TWO));

		this.alignShooterButton = rightStick.getButton(FlightStick.FlightButton.FIVE);

		this.pov = rightStick.getPOVSwitch();
		this.rumbleController = new FakeRumbleable();

		this.climberSwitch = xbox.getButton(XboxButton.A);

		final ButtonInput xboxX = xbox.getButton(XboxButton.X);
		final ButtonInput xboxB = xbox.getButton(XboxButton.B);
		
		// This block of code changes analog value to digital integer between 0 and 2
		this.intakeSwitch = () -> {
			if(xboxX.getButton()) {
				return 2;
			} else if(xboxB.getButton()) {
				return 1;
			} else {
				return 0;
			}
		};

		this.shootButton = new SampleButtonInput() {

			@Override
			public boolean getButton() {
				return xbox.getAxis(XboxAxis.RT).get() > 0.25;
			}
			
		};
		this.agitateButton = new SampleButtonInput() {

			@Override
			public boolean getButton() {
				return xbox.getAxis(XboxAxis.RT).get() > 0.75;
			}
			
		};

		// This code changes shooter speed analog input to integer between 0 and 11
		this.shooterSpeed = new XboxShooterThing(xbox.getButton(XboxButton.SELECT), xbox.getButton(XboxButton.START));
		this.autoManualToggle = new SampleButtonInput() {

			@Override
			public boolean getButton() {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
		this.panicButton = new SampleButtonInput() {

			@Override
			public boolean getButton() {
				// TODO Auto-generated method stub
				return false;
			}
			
		};

		gearButton = new MultiButtonInput(MultiButtonInput.Operator.OR,
				rightStick.getButton(FlightStick.FlightButton.THREE),
				leftStick.getButton(FlightStick.FlightButton.THREE));

	}

	public BlastoiseInputManager(XboxController controller, BlastoiseController blastoiseController) {
		this.leftJoystick = controller.getAxis(XboxController.XboxAxis.LY);
		this.rightJoystick = controller.getAxis(XboxController.XboxAxis.RY);
		
		this.turnButton = controller.getButton(XboxController.XboxButton.LBUMP);
		this.straightButton = controller.getButton(XboxController.XboxButton.RBUMP);

		this.slowButton = controller.getButton(XboxController.XboxButton.A);
		this.alignShooterButton = new FakeButtonInput(); //controller.getButton(XboxController.XboxButton.Y);
		this.shootButton = controller.getButton(XboxController.XboxButton.Y);

		this.shooterSpeed = new DigitalInput() {
			@Override
			public int get() {
				return 0;
			}
		};

		this.pov = controller.getDPad();

		this.rumbleController = controller;
		
		this.climberSwitch = new FakeButtonInput();
		this.intakeSwitch = new FakeButtonInput();
		
		this.panicButton = new FakeButtonInput();
		autoManualToggle = new FakeButtonInput();

		gearButton = controller.getButton(XboxController.XboxButton.X);
		agitateButton = new FakeButtonInput();
	}
	
	public BlastoiseInputManager(XboxController controller, XboxController xbox2) {
		this.leftJoystick = controller.getAxis(XboxController.XboxAxis.LY);
		this.rightJoystick = controller.getAxis(XboxController.XboxAxis.RY);
		
		this.turnButton = controller.getButton(XboxController.XboxButton.LBUMP);
		this.straightButton = controller.getButton(XboxController.XboxButton.RBUMP);

		this.slowButton = controller.getButton(XboxController.XboxButton.A);
		this.alignShooterButton = controller.getButton(XboxController.XboxButton.Y);

		this.pov = controller.getDPad();
		
		this.rumbleController = xbox2;

		this.climberSwitch = xbox2.getButton(XboxController.XboxButton.X);
		this.intakeSwitch = xbox2.getButton(XboxController.XboxButton.B);
		this.shootButton = xbox2.getButton(XboxController.XboxButton.Y);

		this.shooterSpeed = new XboxShooterThing(xbox2.getButton(XboxController.XboxButton.LBUMP),
						xbox2.getButton(XboxController.XboxButton.RBUMP));

		this.panicButton = xbox2.getButton(XboxController.XboxButton.A);

		this.autoManualToggle = new XboxButtonToggleThingy(xbox2.getButton(XboxController.XboxButton.SELECT), xbox2.getButton(XboxController.XboxButton.START));

		gearButton = controller.getButton(XboxController.XboxButton.X);
		agitateButton = new FakeButtonInput();
	}

	@Override
	public void rumbleRight(float strength, long millis) {
		rumbleController.rumbleRight(strength, millis);
	}

	@Override
	public void rumbleLeft(float strength, long millis) {
		rumbleController.rumbleLeft(strength, millis);
	}

	@Override
	public void rumble(float strength, long millis) {
		rumbleController.rumble(strength, millis);
	}

	public double getLeft() {
		return leftJoystick.get();
	}

	public double getRight() {
		return rightJoystick.get();
	}
}
