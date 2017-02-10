package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.commands.drivetrain.Drive;
import com._2491nomythic.watt.settings.Constants;
import com._2491nomythic.watt.settings.Variables;
import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CounterBase;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 *
 */
public class Drivetrain extends PIDSubsystem {
	private CANTalon left1, left2, left3, centerLeft, centerRight, right1, right2, right3;
	private Encoder encoderLeft, encoderRight, encoderCenter;
	private Solenoid shifter;
	private AHRS gyro;
	
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
		super(Variables.drivetrainPID_P, Variables.drivetrainPID_I, Variables.drivetrainPID_D);
		left1 = new CANTalon(Constants.driveTalonLeft1Channel);
		left2 = new CANTalon(Constants.driveTalonLeft2Channel);
		left3 = new CANTalon(Constants.driveTalonLeft3Channel);
		centerLeft = new CANTalon(Constants.driveTalonCenterLeftChannel);
		centerRight = new CANTalon(Constants.driveTalonCenterRightChannel);
		right1 = new CANTalon(Constants.driveTalonRight1Channel);
		right2 = new CANTalon(Constants.driveTalonRight2Channel);
		right3 = new CANTalon(Constants.driveTalonRight3Channel);
		
		encoderLeft = new Encoder(Constants.driveEncoderLeftChannel1, Constants.driveEncoderLeftChannel2, false, CounterBase.EncodingType.k1X);
		encoderRight = new Encoder(Constants.driveEncoderRightChannel1, Constants.driveEncoderRightChannel2, false, CounterBase.EncodingType.k1X);
		encoderCenter = new Encoder(Constants.driveEncoderCenterChannel1, Constants.driveEncoderCenterChannel2, false, CounterBase.EncodingType.k1X);
		encoderLeft.setDistancePerPulse(Constants.driveEncoderToFeet);
		encoderRight.setDistancePerPulse(Constants.driveEncoderToFeet);
		encoderCenter.setDistancePerPulse(Constants.driveEncoderToFeet);
		encoderLeft.reset();
		encoderRight.reset();
		encoderCenter.reset();
		
		//shifter = new Solenoid(Constants.driveSolenoidChannel);
		
		gyro = new AHRS(SerialPort.Port.kUSB);
	}
	
	public void drive(double speed){
		drive(speed, speed, 0, 0);
	}
	
	public void drive(double verticalSpeed, double centerSpeed){
		drive(verticalSpeed, verticalSpeed, centerSpeed, centerSpeed);
	}
	
	public void drive(double leftSpeed, double rightSpeed, double centerLeftSpeed, double centerRightSpeed){
		driveLeft(leftSpeed);
		driveRight(rightSpeed);
		driveCenter(centerLeftSpeed, centerRightSpeed);
	}
	
	public void driveLeft(double speed){
		left1.set(-speed);
		left2.set(-speed);
		left3.set(-speed);
	}
	
	public void driveRight(double speed){
		right1.set(speed);
		right2.set(speed);
		right3.set(speed);
	}
	
	public void driveCenter(double leftSpeed, double rightSpeed){
		centerLeft.set(leftSpeed);
		centerRight.set(-rightSpeed);
	}
	
	public void stop(){
		drive(0, 0);
	}
	
	public void changeVerticalToPercentVbus() {
		left1.changeControlMode(TalonControlMode.PercentVbus);
		left2.changeControlMode(TalonControlMode.PercentVbus);
		left3.changeControlMode(TalonControlMode.PercentVbus);
		right1.changeControlMode(TalonControlMode.PercentVbus);
		right2.changeControlMode(TalonControlMode.PercentVbus);
		right3.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	public void changeVerticalToSpeed() {
		left1.changeControlMode(TalonControlMode.Speed);
		left2.changeControlMode(TalonControlMode.Speed);
		left3.changeControlMode(TalonControlMode.Speed);
		right1.changeControlMode(TalonControlMode.Speed);
		right2.changeControlMode(TalonControlMode.Speed);
		right3.changeControlMode(TalonControlMode.Speed);
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
	public double getCenterEncoderRate() {;
		return encoderCenter.getRate();
	}
	
	/**
	 * Shifts the drivetrain to high gear, giving it more speed but less torque
	 */
	public void shiftToHighGear() {
		shifter.set(true);
	}
	
	/**
	 * Shifts the drivetrain to low gear, giving it more torque but less speed
	 */
	public void shiftToLowGear() {
		shifter.set(false);
	}
	
	/**
	 * @return Whether the drive solenoid is extended
	 */
	public boolean getSolenoidValue() {
		return shifter.get();
	}
	
	public void resetGyro() {
		gyro.reset();
	}
	
	public double getGyroAngle(){
		return gyro.getAngle();
	}
	
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Drive());
    }

	@Override
	protected double returnPIDInput() {
		return getGyroAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		left1.pidWrite(-output);
		left2.pidWrite(-output);
		left3.pidWrite(-output);
		
		right1.pidWrite(output);
		right2.pidWrite(output);
		right3.pidWrite(output);
		
		centerLeft.pidWrite(-output);
		centerRight.pidWrite(output);
		
	}
}
