package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.commands.drivetrain.Drive;
import com._2491nomythic.watt.settings.Constants;
import com._2491nomythic.watt.settings.Variables;
import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

//import edu.wpi.first.wpilibj.CounterBase;
//import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The system of motors, solenoids, encoders, and a gyro that allows us to drive the robot
 */
public class Drivetrain extends PIDSubsystem {
	private CANTalon left1, left2, left3, centerLeft, centerRight, right1, right2, right3;
	private Solenoid shifter;
	private double currentPIDOutput;
	private AHRS gyro;
	
	private static Drivetrain instance;
	
	
	public static Drivetrain getInstance() {
		if (instance == null) {
			instance = new Drivetrain();
		}
		return instance;
	}
	
	/**
	 * The system of motors, solenoids, encoders, and a gyro that allows us to drive the robot
	 */
	private Drivetrain() {
		super("Drive", Variables.drivetrainPID_P, Variables.drivetrainPID_I, Variables.drivetrainPID_D);
		setAbsoluteTolerance(2);
		setInputRange(0, 360);
		getPIDController().setContinuous(true);
		
		left1 = new CANTalon(Constants.driveTalonLeft1Channel);
		left2 = new CANTalon(Constants.driveTalonLeft2Channel);
		left3 = new CANTalon(Constants.driveTalonLeft3Channel);
		centerLeft = new CANTalon(Constants.driveTalonCenterLeftChannel);
		centerRight = new CANTalon(Constants.driveTalonCenterRightChannel);
		right1 = new CANTalon(Constants.driveTalonRight1Channel);
		right2 = new CANTalon(Constants.driveTalonRight2Channel);
		right3 = new CANTalon(Constants.driveTalonRight3Channel);
		
		left2.changeControlMode(TalonControlMode.Follower);
		left3.changeControlMode(TalonControlMode.Follower);
		right2.changeControlMode(TalonControlMode.Follower);
		right3.changeControlMode(TalonControlMode.Follower);
		
		left2.set(left1.getDeviceID());
		left3.set(left1.getDeviceID());
		right2.set(right1.getDeviceID());
		right3.set(right1.getDeviceID());
		
		left1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		right1.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		centerLeft.setFeedbackDevice(FeedbackDevice.QuadEncoder);
		
		left1.configEncoderCodesPerRev(256);
		right1.configEncoderCodesPerRev(256);
		centerLeft.configEncoderCodesPerRev(256);
		
		shifter = new Solenoid(Constants.driveSolenoidChannel);
		
		gyro = new AHRS(SerialPort.Port.kUSB);
	}
	
	/**
	 * Drives the robot forward or backward only
	 * @param speed The power fed to the vertical drive motors, ranging from -1 to 1, where negative values run the motors backwards
	 */
	public void drive(double speed){
		drive(speed, speed, 0, 0);
	}
	
	/**
	 * Drives the robot vertically and horizontally
	 * @param verticalSpeed The power fed to the vertical drive motors, ranging from -1 to 1, where negative values move the robot backwards
	 * @param centerSpeed The power fed to the horizontal drive motors, ranging from -1 to 1, where negative values move the robot to the left
	 */
	public void drive(double verticalSpeed, double centerSpeed){
		drive(verticalSpeed, verticalSpeed, centerSpeed, centerSpeed);
	}
	
	/**
	 * Drives the robot with each set of motors recieving an individual specific speed
	 * @param leftSpeed The power fed to the left drive motors, ranging from -1 to 1, where negative values run the motors backwards
	 * @param rightSpeed The power fed to the right drive motors, ranging from -1 to 1, where negative values run the motors backwards
	 * @param centerLeftSpeed The power fed to the front (or left) center drive motor, ranging from -1 to 1, where negative values move the robot to the left
	 * @param centerRightSpeed The power fed to the back (or right) center drive motor, ranging from -1 to 1, where negative values move the robot to the left
	 */
	public void drive(double leftSpeed, double rightSpeed, double centerLeftSpeed, double centerRightSpeed){
		driveLeft(leftSpeed * Variables.driveRestriction);
		driveRight(rightSpeed * Variables.driveRestriction);
		driveCenter(centerLeftSpeed * Variables.driveRestriction, centerRightSpeed * Variables.driveRestriction);
	}
	
	/**
	 * Drives the left side of the robot
	 * @param speed The power fed to the motors, ranging from -1 to 1, where negative values run the motors backwards
	 */
	public void driveLeft(double speed){
		left1.set(-speed);
	}
	
	/**
	 * Drives the right side of the robot
	 * @param speed The power fed to the motors, ranging from -1 to 1, where negative values run the motors backwards
	 */
	public void driveRight(double speed){
		right1.set(speed);
	}
	
	/**
	 * Drives the center motors on the robot
	 * @param leftSpeed The power fed to the front (or left) center drive motor, ranging from -1 to 1, where negative values move the robot to the left
	 * @param rightSpeed The power fed to the back (or right) center drive motor, ranging from -1 to 1, where negative values move the robot to the left
	 */
	public void driveCenter(double leftSpeed, double rightSpeed){
		centerLeft.set(leftSpeed);
		centerRight.set(-rightSpeed);
	}
	
	/**
	 * Drives the center motors on the robot using PID
	 * @param leftSpeed The desired speed for the left motor
	 * @param rightSpeed The desired speed for the right motor
	 */
	public void driveCenterPID(double leftSpeed, double rightSpeed){
		centerLeft.pidWrite(leftSpeed);
		centerRight.pidWrite(-rightSpeed);
	}
	
	/**
	 * Drives the left and right drive motors using PID
	 * @param leftSpeed The desired speed for the left motors
	 * @param rightSpeed The desired speed for the right motors
	 * @deprecated
	 */
	public void driveLeftRightPID(double leftSpeed, double rightSpeed) {
		left1.pidWrite(leftSpeed);
		left2.pidWrite(leftSpeed);
		left3.pidWrite(leftSpeed);
		right1.pidWrite(rightSpeed);
		right2.pidWrite(rightSpeed);
		right3.pidWrite(rightSpeed);
	}
	
	/**
	 * Stops all drive motion
	 */
	public void stop(){
		drive(0, 0);
	}
	
	/**
	 * Changes vertical drive motors to PercentVBus mode
	 */
	public void changeVerticalToPercentVbus() {
		left1.changeControlMode(TalonControlMode.PercentVbus);
		right1.changeControlMode(TalonControlMode.PercentVbus);
	}
	
	/**
	 * Changes vertical drive motors to Speed mode
	 */
	public void changeVerticalToSpeed() {
		left1.changeControlMode(TalonControlMode.Speed);
		right1.changeControlMode(TalonControlMode.Speed);
	}
	
	/**
	 * Changes vertical drive motors to Position mode
	 */
	public void changeVerticalToPosition() {
		left1.changeControlMode(TalonControlMode.Position);
		right1.changeControlMode(TalonControlMode.Position);
	}
	
	/**
	 * Changes vertical drive motors to Coast mode
	 */
	public void enableVerticalCoastMode() {
		left1.enableBrakeMode(false);
		left2.enableBrakeMode(false);
		left3.enableBrakeMode(false);
		right1.enableBrakeMode(false);
		right2.enableBrakeMode(false);
		right3.enableBrakeMode(false);
	}
	
	/**
	 * Changes horizontal drive motors to Coast mode
	 */
	public void enableHorizontalCoastMode() {
		centerLeft.enableBrakeMode(false);
		centerRight.enableBrakeMode(false);
	}
	
	/**
	 * Changes vertical drive motors to Brake mode
	 */
	public void enableVerticalBrakeMode() {
		left1.enableBrakeMode(true);
		left2.enableBrakeMode(true);
		left3.enableBrakeMode(true);
		right1.enableBrakeMode(true);
		right2.enableBrakeMode(true);
		right3.enableBrakeMode(true);
	}
	
	/**
	 * Changes horizontal drive motors to Brake mode
	 */
	public void enableHorizontalBrakeMode() {
		centerLeft.enableBrakeMode(true);
		centerRight.enableBrakeMode(true);
	}
	
	/**
	 * Resets the left drive encoder value to 0
	 */
	public void resetLeftEncoder() {
		left1.setEncPosition(0);
	}
	
	/**
	 * Resets the right drive encoder value to 0
	 */
	public void resetRightEncoder() {
		right1.setEncPosition(0);
	}
	
	/**
	 * Resets the center drive encoder value to 0
	 */
	public void resetCenterEncoder() {
		centerLeft.setEncPosition(0);
	}
	
	/**
	 * @return The value of the left drive encoder
	 */
	public double getLeftEncoderDistance() {
		return left1.getEncPosition() * Constants.driveEncoderToFeet;
	}
	
	/**
	 * @return The value of the right drive encoder
	 */
	public double getRightEncoderDistance() {
		return right1.getEncPosition() * Constants.driveEncoderToFeet;
	}
	
	/**
	 * @return The value of the center drive encoder
	 */
	public double getCenterEncoderDistance() {
		return -centerLeft.getEncPosition() * Constants.driveEncoderToFeet;
	}
	
	/**
	 * @return The speed of the left motor in feet per second
	 */
	public double getLeftEncoderRate() {
		return left1.getEncVelocity();
	}
	
	/**
	 * @return The speed of the right motor in feet per second
	 */
	public double getRightEncoderRate() {
		return right1.getEncVelocity();
	}
	
	/**
	 * @return The speed of the center motor in feet per second
	 */
	public double getCenterEncoderRate() {;
		return centerLeft.getEncVelocity();
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
	
	/**
	 * Resets the value of the gyro to 0
	 */
	public void resetGyro() {
		gyro.reset();
	}
	
	/**
	 * @return The value of the gyro
	 */
	public double getGyroAngle(){
		return (gyro.getAngle()  % 360 + 360) % 360;
	}	
	
	public double getRawGyroAngle(){
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
		drive(output, -output, 0, 0);
		SmartDashboard.putNumber("PID drive 2", output);
	}
	
	/**
	 * @return The output for the PID loop
	 */
	public double getPIDOutput() {
		return currentPIDOutput;
	}
}

