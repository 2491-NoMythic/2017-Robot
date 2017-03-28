package com._2491nomythic.watt.commands.dustpan;

import com._2491nomythic.watt.commands.CommandBase;


/**
 * Automatically picks a gear up  off of the ground
 */
public class PickUpGears extends CommandBase {

	/**
	 * Automatically picks a gear up  off of the ground
	 */
    public PickUpGears() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(dustpan);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	dustpan.flipDown();
    	dustpan.runMotor(1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return dustpan.getLimitSwitch();
    }

    // Called once after isFinished returns true
    protected void end() {
    	dustpan.stop();
    	dustpan.flipUp();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
