package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.commands.drivetrain.Drive;
import com._2491nomythic.robot.settings.Constants;
import com._2491nomythic.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Drivetrain extends Subsystem {
	private CANTalon left, centerLeft, centerRight, right;
	private Encoder encoderLeft, encoderRight, encoderCenter;
	
	private static Drivetrain instance;
	
	public static Drivetrain getInstance() {
		if (instance == null) {
			instance = new Drivetrain();
		}
		return instance;
	}
	
	/**
	 * The system used to move the robot
	 */
	private Drivetrain() {
		left = new CANTalon(Constants.driveTalonLeftChannel);
		centerLeft = new CANTalon(Constants.driveTalonCenterLeftChannel);
		centerRight = new CANTalon(Constants.driveTalonCenterRightChannel);
		right = new CANTalon(Constants.driveTalonRightChannel);
		
		encoderLeft = new Encoder(Constants.driveEncoderLeftChannel1, Constants.driveEncoderLeftChannel2, false, CounterBase.EncodingType.k1X);
		encoderRight = new Encoder(Constants.driveEncoderRightChannel1, Constants.driveEncoderRightChannel2, false, CounterBase.EncodingType.k1X);
		encoderCenter = new Encoder(Constants.driveEncoderCenterChannel1, Constants.driveEncoderCenterChannel2, false, CounterBase.EncodingType.k1X);
		encoderLeft.setDistancePerPulse(Constants.driveEncoderToFeet);
		encoderRight.setDistancePerPulse(Constants.driveEncoderToFeet);
		encoderCenter.setDistancePerPulse(Constants.driveEncoderToFeet);
		encoderLeft.reset();
		encoderRight.reset();
		encoderCenter.reset();
	}
	
	public void drive(double speed){
		driveLeft(speed);
		driveRight(speed);
	}
	
	public void drive(double leftRightSpeed, double centerSpeed){
		driveLeft(leftRightSpeed);
		driveRight(leftRightSpeed);
		driveCenter(centerSpeed);
	}
	
	public void drive(double leftSpeed, double rightSpeed, double centerSpeed){
		driveLeft(leftSpeed);
		driveRight(rightSpeed);
		driveCenter(centerSpeed);
	}
	
	public void driveLeft(double speed){
		left.set(speed);
	}
	
	public void driveRight(double speed){
		right.set(speed);
	}
	
	public void driveCenter(double speed){
		centerLeft.set(speed);
		centerRight.set(speed);
	}
	
	public void stop(){
		drive(0);
	}
	
	public void resetLeftEncoder() {
		encoderLeft.reset();
	}
	
	/**
	 * Resets the right drive encoder value to 0
	 */
	public void resetRightEncoder() {
		encoderRight.reset();
	}
	
	/**
	 * Resets the center drive encoder value to 0
	 */
	public void resetCenterEncoder() {
		encoderCenter.reset();
	}
	
	/**
	 * @return The left drive encoder
	 */
	public Encoder getLeftEncoder() {
		return encoderLeft;
	}
	
	/**
	 * @return the right drive encoder
	 */
	public Encoder getRightEncoder() {
		return encoderRight;
	}
	
	/**
	 * @return the right drive encoder
	 */
	public Encoder getCenterEncoder() {
		return encoderCenter;
	}
	
	/**
	 * @return The value of the left drive encoder
	 */
	public double getLeftEncoderDistance() {
		return encoderLeft.getDistance();
	}
	
	/**
	 * @return The value of the right drive encoder
	 */
	public double getRightEncoderDistance() {
		return encoderRight.getDistance();
	}
	
	/**
	 * @return The value of the center drive encoder
	 */
	public double getCenterEncoderDistance() {
		return encoderCenter.getDistance();
	}
	
	/**
	 * @return The speed of the left motor in feet per second
	 */
	public double getLeftEncoderRate() {
		return encoderLeft.getRate();
	}
	
	/**
	 * @return The speed of the right motor in feet per second
	 */
	public double getRightEncoderRate() {
		return encoderRight.getRate();
	}
	
	/**
	 * @return The speed of the center motor in feet per second
	 */
	public double getCenterEncoderRate() {
		return encoderCenter.getRate();
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Drive());
    }
}

