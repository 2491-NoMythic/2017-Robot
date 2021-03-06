package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 * Drives at a given velocity in Speed mode
 * @deprecated
 */
public class SpeedTest extends CommandBase {
	double speed;

	/**
	 * Drives at a given velocity in Speed mode
	 * @param speed The velocity (in RPM) at which to drive the motors
	 * @deprecated
	 */
    public SpeedTest(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.changeVerticalToSpeed();
    	drivetrain.drive(speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
