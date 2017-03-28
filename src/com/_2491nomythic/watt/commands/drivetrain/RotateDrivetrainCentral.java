package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 * Rotates the drivetrain using the center motors at a specified power for a specified time
 */
public class RotateDrivetrainCentral extends CommandBase {
	private Timer timer;
	private double motorPower;
	private double rotateDuration;

	/**
	 * Rotates the drivetrain using the center motors at a specified power for a specified time
	 * @param power The power fed to the motors, ranging from -1 to 1, where negative values rotate the robot counterclockwise
	 * @param time The amount of time for the robot to rotate, in seconds
	 */
    public RotateDrivetrainCentral(double power,double time) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	timer = new Timer();
    	
    	//Positive speed rotates clockwise and negative speed rotates counter-clockwise.
    	motorPower = power;
    	rotateDuration = time;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	timer.reset();
    	drivetrain.driveCenter(motorPower, -1.0*motorPower);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return timer.get() > rotateDuration;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
