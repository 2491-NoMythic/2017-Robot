package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 * Resets the drive encoders
 */
public class ResetEncoders extends CommandBase {

	/**
	 * Resets the drive encoders
	 */
    public ResetEncoders() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.resetCenterEncoder();
    	drivetrain.resetLeftEncoder();
    	drivetrain.resetRightEncoder();
    	System.out.println("RESETING THE ENCODERS");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
