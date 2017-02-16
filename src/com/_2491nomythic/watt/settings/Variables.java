package com._2491nomythic.watt.settings;

public class Variables {
	
	//Linear Acceleration
	public static boolean useLinearAcceleration = false;
	public static double accelerationSpeed = 0.07;
	public static double wheelCircumference = 0.5 * Math.PI;
	
	public static double lowGearMaxSpeed = 6.51;
	public static double lowGearRatio = 17.36;
	public static double lowGearMaxRPM = (lowGearMaxSpeed / wheelCircumference) * lowGearRatio * 60;
	
	public static double highGearMaxSpeed = 23.94;
	public static double highGearRatio = 4.72;
	public static double highGearMaxRPM = (highGearMaxSpeed / wheelCircumference) * highGearRatio * 60;
	
	public static double driveRestriction = 1;
	
	//Shifting Constants
	public static double shiftUpSpeed = 1000;
	public static double shiftDownSpeed = 1000;
	public static double shiftUpNewPower = 0.5;
	public static double shiftDownNewPower = 0.9;
	
	//PID Constants
	public static double drivetrainPID_P = 1.0;
	public static double drivetrainPID_I = 0.0;
	public static double drivetrainPID_D = 0.0;

	//GearSlot stuff
	public static double timeToOpenGearSlot = 0.5;
	public static double timeToEjectGear = 0.25;

}
