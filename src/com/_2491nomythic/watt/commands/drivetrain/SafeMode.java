package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Variables;

/**
 *
 */
public class SafeMode extends CommandBase {
	boolean enabled;
	
    public SafeMode() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	enabled = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (enabled) {
    		Variables.useAutomaticTransmission = true;
        	Variables.driveRestriction = 1;
        	enabled = false;
    	}
    	else {
    		Variables.useAutomaticTransmission = false;
    		Variables.driveRestriction = 0.5;
    		enabled = true;
    	}
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
