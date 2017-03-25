package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 * Drives the left and right motors at specified velocities for a specified amount of time in Speed mode
 * @deprecated
 */
public class DriveSpeedTime extends CommandBase {
	private double time;
	private double speed;
	private Timer timer;
	
	/**
	 * Drives the left and right motors at specified velocities for a specified amount of time in Speed mode
	 * @param speed The velocity (in RPM) at which to drive the motors
	 * @param time The time for which to drive the motors
	 * @deprecated
	 */
    public DriveSpeedTime(double speed, double time) {
    	requires(drivetrain);
      	this.time = Math.abs(time);
      	this.speed = speed;
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	timer.reset();
    	drivetrain.changeVerticalToSpeed();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drivetrain.drive(speed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > time;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    	drivetrain.changeVerticalToPercentVbus();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
