package com._2491nomythic.robot.commands.gearslot;

import com._2491nomythic.robot.commands.CommandBase;

/**
 *
 */
public class OpenAndEjectGearSlot extends CommandBase {
	EjectGear ejectGear;
	ToggleGearSlot toggleGearSlot1, toggleGearSlot2;

    public OpenAndEjectGearSlot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	ejectGear = new EjectGear();
    	toggleGearSlot1 = new ToggleGearSlot();
    	toggleGearSlot2 = new ToggleGearSlot();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	toggleGearSlot1.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(toggleGearSlot1.isFinished()) {
    		ejectGear.start();
    		if(ejectGear.isFinished()) {
    			toggleGearSlot2.start();
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return toggleGearSlot2.isFinished();
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
