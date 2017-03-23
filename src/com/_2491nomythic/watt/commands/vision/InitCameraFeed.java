package com._2491nomythic.watt.commands.vision;

import com._2491nomythic.watt.commands.CommandBase;

public class InitCameraFeed extends CommandBase {

    public InitCameraFeed() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(vision);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	vision.cameraFeed();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
