package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Variables;

/**
 * Puts the robot in a "safe mode" where robot movement is restricted to half power, and the transmission will not shift
 */
public class SafeMode extends CommandBase {
	boolean enabled = false;
	
	/**
	 * Puts the robot in a "safe mode" where robot movement is restricted to half power, and the transmission will not shift
	 */
    public SafeMode() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
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
