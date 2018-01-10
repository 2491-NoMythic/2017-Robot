package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.commands.drivetrain.Drive;
import com._2491nomythic.watt.settings.Constants;
import com._2491nomythic.watt.settings.Variables;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
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
	private TalonSRX left1, left2, left3, centerLeft, centerRight, right1, right2, right3;
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
		
		left1 = new TalonSRX(Constants.driveTalonLeft1Channel);
		left2 = new TalonSRX(Constants.driveTalonLeft2Channel);
		left3 = new TalonSRX(Constants.driveTalonLeft3Channel);
		centerLeft = new TalonSRX(Constants.driveTalonCenterLeftChannel);
		centerRight = new TalonSRX(Constants.driveTalonCenterRightChannel);
		right1 = new TalonSRX(Constants.driveTalonRight1Channel);
		right2 = new TalonSRX(Constants.driveTalonRight2Channel);
		right3 = new TalonSRX(Constants.driveTalonRight3Channel);
		
		left2.follow(left1);
		left3.follow(left1);
		right2.follow(right1);
		right3.follow(right1);
		
		left1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		right1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		centerLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
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
		left1.set(ControlMode.PercentOutput, -speed);
	}
	
	/**
	 * Drives the right side of the robot
	 * @param speed The power fed to the motors, ranging from -1 to 1, where negative values run the motors backwards
	 */
	public void driveRight(double speed){
		right1.set(ControlMode.PercentOutput, speed);
	}
	
	/**
	 * Drives the center motors on the robot
	 * @param leftSpeed The power fed to the front (or left) center drive motor, ranging from -1 to 1, where negative values move the robot to the left
	 * @param rightSpeed The power fed to the back (or right) center drive motor, ranging from -1 to 1, where negative values move the robot to the left
	 */
	public void driveCenter(double leftSpeed, double rightSpeed){
		centerLeft.set(ControlMode.PercentOutput, leftSpeed);
		centerRight.set(ControlMode.PercentOutput, -rightSpeed);
	}
	
	/**
	 * Stops all drive motion
	 */
	public void stop(){
		drive(0, 0);
	}
	
	/**
	 * Changes vertical drive motors to PercentVBus mode
	 * @deprecated
	 */
	public void changeVerticalToPercentVbus() {
		left1.set(ControlMode.PercentOutput, 0);
		right1.set(ControlMode.PercentOutput, 0);
	}
	
	/**
	 * Changes vertical drive motors to Speed mode
	 * @deprecated
	 */
	public void changeVerticalToSpeed() {
		left1.set(ControlMode.Velocity, 0);
		right1.set(ControlMode.Velocity, 0);
	}
	
	/**
	 * Changes vertical drive motors to Position mode
	 * @deprecated
	 */
	public void changeVerticalToPosition() {
		left1.set(ControlMode.Position, 0);
		right1.set(ControlMode.Position, 0);
	}
	
	/**
	 * Changes vertical drive motors to Coast mode
	 */
	public void enableVerticalCoastMode() {
		left1.setNeutralMode(NeutralMode.Coast);
		left2.setNeutralMode(NeutralMode.Coast);
		left3.setNeutralMode(NeutralMode.Coast);
		right1.setNeutralMode(NeutralMode.Coast);
		right2.setNeutralMode(NeutralMode.Coast);
		right3.setNeutralMode(NeutralMode.Coast);
	}
	
	/**
	 * Changes horizontal drive motors to Coast mode
	 */
	public void enableHorizontalCoastMode() {
		centerLeft.setNeutralMode(NeutralMode.Coast);
		centerRight.setNeutralMode(NeutralMode.Coast);
	}
	
	/**
	 * Changes vertical drive motors to Brake mode
	 */
	public void enableVerticalBrakeMode() {
		left1.setNeutralMode(NeutralMode.Brake);
		left2.setNeutralMode(NeutralMode.Brake);
		left3.setNeutralMode(NeutralMode.Brake);
		right1.setNeutralMode(NeutralMode.Brake);
		right2.setNeutralMode(NeutralMode.Brake);
		right3.setNeutralMode(NeutralMode.Brake);
	}
	
	/**
	 * Changes horizontal drive motors to Brake mode
	 */
	public void enableHorizontalBrakeMode() {
		centerLeft.setNeutralMode(NeutralMode.Brake);
		centerRight.setNeutralMode(NeutralMode.Brake);
	}
	
	/**
	 * Resets the left drive encoder value to 0
	 */
	public void resetLeftEncoder() {
		left1.setSelectedSensorPosition(0, 0, 0);
	}
	
	/**
	 * Resets the right drive encoder value to 0
	 */
	public void resetRightEncoder() {
		right1.getSelectedSensorPosition(0);
	}
	
	/**
	 * Resets the center drive encoder value to 0
	 */
	public void resetCenterEncoder() {
		centerLeft.setSelectedSensorPosition(0, 0, 0);
	}
	
	/**
	 * @return The value of the left drive encoder
	 */
	public double getLeftEncoderDistance() {
		return left1.getSelectedSensorPosition(0) * Constants.driveEncoderToFeet;
	}
	
	/**
	 * @return The value of the right drive encoder
	 */
	public double getRightEncoderDistance() {
		return right1.getSelectedSensorPosition(0) * Constants.driveEncoderToFeet;
	}
	
	/**
	 * @return The value of the center drive encoder
	 */
	public double getCenterEncoderDistance() {
		return -centerLeft.getSelectedSensorPosition(0) * Constants.driveEncoderToFeet;
	}
	
	/**
	 * @return The speed of the left motor in feet per second
	 */
	public double getLeftEncoderRate() {
		return left1.getSelectedSensorVelocity(0);
	}
	
	/**
	 * @return The speed of the right motor in feet per second
	 */
	public double getRightEncoderRate() {
		return right1.getSelectedSensorVelocity(0);
	}
	
	/**
	 * @return The speed of the center motor in feet per second
	 */
	public double getCenterEncoderRate() {;
		return centerLeft.getSelectedSensorVelocity(0);
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

