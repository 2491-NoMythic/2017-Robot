package com._2491nomythic.watt.settings;

public class Constants {
	
	//Controllers
	public static final int ControllerOnePort = 0;
	public static final int ControllerTwoPort = 1;
	
	//Drive
	public static final int driveTalonLeft1Channel = 14;
	public static final int driveTalonLeft2Channel = 19;
	public static final int driveTalonLeft3Channel = 11;
	public static final int driveTalonCenterLeftChannel = 17;
	public static final int driveTalonCenterRightChannel = 10;
	public static final int driveTalonRight1Channel = 13;
	public static final int driveTalonRight2Channel = 15;
	public static final int driveTalonRight3Channel = 12;
	public static final int driveEncoderLeftChannel1 = 3;
	public static final int driveEncoderLeftChannel2 = 4;
	public static final int driveEncoderCenterChannel1 = 5;
	public static final int driveEncoderCenterChannel2 = 6;
	public static final int driveEncoderRightChannel1 = 7;
	public static final int driveEncoderRightChannel2 = 8;
	public static final int driveSolenoidChannel = 6;
	
	//Climber
	public static final int climberTalonLeftChannel = 18;
	public static final int climberTalonRightChannel = 20;
	public static final int climberLimitSwitchChannel = 2;
	
	//GearSlot
	
	public static final int gearOpenerSolenoidChannel = 7;
	public static final int gearEjectorSolenoidChannel = 6;
	
	//Shooter
	
	public static final int shooterTalonChannel = 21;
		
	//Computation
	public static final double driveEncoderToFeet = 0.00598740738248483559836600606686; //TODO change this value to something more accurate.
	
	//Autonomous (all measurements are in feet)
	public static final int driveStraightAutoDistance = 10;
}
