package com._2491nomythic.robot.commands.gearslot;

import com._2491nomythic.robot.commands.CommandBase;

/**
 *
 */
public class ToggleGearSlot extends CommandBase {
	private boolean doorsOpened = false;

    public ToggleGearSlot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(gearslot);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(doorsOpened) {
    		gearslot.closeDoors();
    		doorsOpened = false;
    	}
    	else {
    		gearslot.openDoors();
    		doorsOpened = true;
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
