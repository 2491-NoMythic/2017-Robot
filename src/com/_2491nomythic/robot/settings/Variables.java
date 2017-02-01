package com._2491nomythic.robot.settings;

public class Variables {
	
	public static boolean useLinearAcceleration = true;
	public static double accelerationSpeed = 0.07;
	
	//Shifting Constants
	public static double shiftUpSpeed = 1000;
	public static double shiftDownSpeed = 1000;
	
	//PID Constants
	public static double drivetrainPID_P = 1.0;
	public static double drivetrainPID_I = 0.0;
	public static double drivetrainPID_D = 0.0;

	//GearSlot stuff
	public static double timeToOpenGearSlot = 0.5;
	public static double timeToEjectGear = 0.25;

}
