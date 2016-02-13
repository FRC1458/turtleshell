package com.team1458.turtleshell;

/**
 * A replacement for SampleRobot/SimpleRobot, this class provides a base class
 * for a robot built off of the turtleshell framework
 * 
 * @author mehnadnerd
 *
 */
public abstract class TurtleRobot extends TurtleAdvancedRobot {
	/**
	 * A representation of the physical robot. You should add components to
	 * this, and give it to your tele and auto.
	 */
	protected TurtlePhysicalRobot physicalRobot = new TurtlePhysicalRobot();
	/**
	 * Teleop. This object represents your teleop code, and you call it in
	 * teleop.
	 */
	protected TurtleTeleop tele;
	/**
	 * Autonomous. This object handles autonomous, you call it during autonomous
	 * and let it do its thing.
	 */
	protected TurtleAutonomous auto;
	
	protected TurtleThingGiver thingGiver;
	
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
