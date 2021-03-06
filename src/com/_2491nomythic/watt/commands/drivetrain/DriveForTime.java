package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 * Drives the left and right motors at specified velocities for a specified amount of time
 */
public class DriveForTime extends CommandBase{
	private Timer timer;
	double timeOut;
	double leftPower;
	double rightPower;
	
	/**
	 * Drives the left and right motors at specified velocities for a specified amount of time
	 * @param timeOut Amount of time to run the motors
	 * @param leftPower Power at which to run the left motors, ranging from -1 to 1, where negative values run the motors backwards
	 * @param rightPower Power at which to run the right motors, ranging from -1 to 1, where negative values run the motors backwards
	 */
    public DriveForTime(double timeOut, double leftPower, double rightPower) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	this.timeOut = timeOut;
    	this.leftPower = leftPower;
    	this.rightPower = rightPower;
    	timer = new Timer();
    }
    
    /**
     * Drives the left and right motors at specified velocities for a specified amount of time
     * @param timeOut Amount of time to run the motors
     * @param power Power at which to run the vertical drive motors, ranging from -1 to 1, where negative values run the motors backwards
     */
    public DriveForTime(double timeOut, double power) {
		requires(drivetrain);
		this.timeOut = timeOut;
		this.leftPower = power;
		this.rightPower = power;
		timer = new Timer();
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	timer.reset();
    	drivetrain.drive(leftPower, rightPower, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > timeOut;
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
