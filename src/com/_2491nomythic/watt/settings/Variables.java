package com._2491nomythic.watt.settings;

/**
 * Various information needed for robot functionality that can be modified by the code itself
 */
public class Variables {
	public static double frontPivotCoefficient = 1;
	public static double backPivotCoefficient = 1;
	public static double pivotCoefficientAmount = 0;
	
	//Linear Acceleration
	public static boolean useLinearAcceleration = true;
	public static boolean useAutomaticTransmission = true;
	public static double accelerationSpeed = 0.05;
	public static double centerAccelerationSpeed = 0.02;
	public static double wheelCircumference = 0.5 * Math.PI;
	
	public static double lowGearMaxSpeedFeetPerSecond = 6.51;
	public static double lowGearRatio = 17.36;
	public static double lowGearMaxSpeed = 460;
	
	public static double highGearMaxSpeedFeetPerSecond = 23.94;
	public static double highGearRatio = 4.72;
	public static double highGearMaxSpeed = (highGearMaxSpeedFeetPerSecond / wheelCircumference) * highGearRatio * 60;
	
	public static double driveRestriction = 1;
	
	//Shifting Constants
	public static double shiftUpPercentage = 0.9;
	public static double shiftUpSpeed = lowGearMaxSpeed * shiftUpPercentage;
	public static double shiftDownSpeed = 0.3;
	public static double shiftUpNewPower = 1;
	public static double shiftDownNewPower = 1;
	public static double shiftEngagePneumaticsTime = 0.02;
	public static double shiftTotalTime = 0.05;

	//PID Constants
	public static double drivetrainPID_P = 0.0028;
	public static double drivetrainPID_I = 0.0;
	public static double drivetrainPID_D = 0.0;
	public static double encoderP = 0.0;
	public static double encoderI = 0.0;
	public static double encoderD = 0.0;

	//GearSlot stuff
	public static double timeToOpenGearSlot = 0.5;
	public static double timeToEjectGear = 0.25;
	public static boolean ejected = false;
	
	//Dustpan
	public static double gearEjectPower = 0.25;
	public static double gearEjectTiming = 0.2;
	public static double automatedIntakePower = -0.25;
	
	//Global Camera Variables
	public static double x1;
	public static double y1;
	public static double height1;
	public static double width1;
	public static double x2;
	public static double y2;
	public static double height2;
	public static double width2;
	public static double avgHeight = (height1 + height2)/2;
	public static double avgWidth = (width1 + width2)/2;
	public static double avgX = (x1 + x2)/2;
	public static double avgY = (y1 + y2)/2;
	public static boolean hasTarget;

}
