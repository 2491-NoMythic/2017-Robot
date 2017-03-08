package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;

/**
 *
 */
public class PassiveCenter extends CommandBase {
	private DriveStraightToPosition firstDrive;
	
	public PassiveCenter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	firstDrive = new DriveStraightToPosition(0.5, 6.5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	firstDrive.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !firstDrive.isRunning();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	firstDrive.cancel();
    }
}
