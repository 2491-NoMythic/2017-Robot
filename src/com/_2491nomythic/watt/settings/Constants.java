package com._2491nomythic.watt.settings;

public class Constants {
	
	//Controllers
	public static final int ControllerOnePort = 0;
	public static final int ControllerTwoPort = 1;
	
	//Drive
	public static final int driveTalonLeft1Channel = 11;
	public static final int driveTalonLeft2Channel = 19;
	public static final int driveTalonLeft3Channel = 14;
	public static final int driveTalonCenterLeftChannel = 17;
	public static final int driveTalonCenterRightChannel = 10;
	public static final int driveTalonRight1Channel = 12;
	public static final int driveTalonRight2Channel = 15;
	public static final int driveTalonRight3Channel = 13;
	public static final int driveEncoderLeftChannel1 = 3;
	public static final int driveEncoderLeftChannel2 = 4;
	public static final int driveEncoderCenterChannel1 = 5;
	public static final int driveEncoderCenterChannel2 = 6;
	public static final int driveEncoderRightChannel1 = 7;
	public static final int driveEncoderRightChannel2 = 8;
	public static final int driveSolenoidChannel = 0;
	
	public static final double pivotDriveRatio = 0.4;
	
	//Climber
	public static final int climberTalonLeftChannel = 18;
	public static final int climberTalonRightChannel = 20;
	
	//GearSlot
	
	public static final int gearDoorSolenoidChannel = 1;
	public static final int gearPusherSolenoidChannel = 2;
	
	//Shooter
	
	public static final int shooterTalonChannel = 21;
		
	//Computation
	public static final double driveEncoderToFeet = 1.0 / 670; //6.0 * Math.PI / 256 / 12; //TODO change this value to something more accurate.
	
	//Autonomous (all measurements are in feet)
	public static final int driveStraightAutoDistance = 10;
	
	//Lights
	public static final int redLightChannel = 0;
	public static final int blueLightChannel = 1;
	public static final int greenLightChannel =2;
	
	//Dustpan
	public static final int dustpanTalonChannel=21;
	public static final int dustpanLimitSwitchChannel=1;
	public static final int dustpanSolenoid1Channel=3;
	public static final int dustpanSolenoid2Channel=4;
	
	//Vision
	public static final int xPerfectValue = 162;
	public static final int errorMargin = 10;
}

