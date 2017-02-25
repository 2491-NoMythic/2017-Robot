package com._2491nomythic.watt.commands.gearslot;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Variables;

/**
 *
 */
public class ToggleEjector extends CommandBase {

    public ToggleEjector() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(gearslot);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(!Variables.ejected) {
    		gearslot.ejectGear();
    		Variables.ejected = true;
    	}
    	else {
    		gearslot.retractEjector();
    		Variables.ejected = false;
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
