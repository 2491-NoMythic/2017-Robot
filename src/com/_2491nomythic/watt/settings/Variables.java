package com._2491nomythic.watt.settings;

public class Variables {
	
	//Linear Acceleration
	public static boolean useLinearAcceleration = true;
	public static double accelerationSpeed = 0.1;
	public static double wheelCircumference = 0.5 * Math.PI;
	
	public static double lowGearMaxSpeedFeetPerSecond = 6.51;
	public static double lowGearRatio = 17.36;
	public static double lowGearMaxSpeed = (lowGearMaxSpeedFeetPerSecond / wheelCircumference) * 256 / 10;
	
	public static double highGearMaxSpeedFeetPerSecond = 23.94;
	public static double highGearRatio = 4.72;
	public static double highGearMaxSpeed = (highGearMaxSpeedFeetPerSecond / wheelCircumference) * highGearRatio * 60;
	
	public static double driveRestriction = 1;
	
	//Shifting Constants
	public static double shiftUpSpeed = 0.7;
	public static double shiftDownSpeed = 0.5;
	public static double shiftUpNewPower = 0.7;
	public static double shiftDownNewPower = 1.3;
	
	//PID Constants
	public static double drivetrainPID_P = 0.001;
	public static double drivetrainPID_I = 0.0;
	public static double drivetrainPID_D = 0.0;

	//GearSlot stuff
	public static double timeToOpenGearSlot = 0.5;
	public static double timeToEjectGear = 0.25;
	public static boolean ejected = false;

}
