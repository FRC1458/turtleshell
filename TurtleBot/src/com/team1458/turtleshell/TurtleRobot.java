package com.team1458.turtleshell;

public abstract class TurtleRobot extends TurtleAdvancedRobot {
	protected TurtlePhysicalRobot physicalRobot = new TurtlePhysicalRobot();
	protected TurtleTeleop tele;
	protected TurtleAutonomous auto;

	@Override
	public final void robotInit() {
		TurtleSafeDriverStation.setDS(m_ds);
		initRobot();
	}

	/**
	 * Initialise the robot. Override this method rather than robotInit()
	 * because robotInit is marked as final to allow code that is critical to be
	 * guaranteed to run.
	 */
	public abstract void initRobot();

	@Override
	public abstract void autonomous();

	@Override
	public abstract void operatorControl();
}
