package com._2491nomythic.watt.commands.gearslot;

import com._2491nomythic.watt.commands.CommandBase;

/**
 *
 */
public class OpenAndEjectGearSlotWithoutMoving extends CommandBase {
	private int state;
	private ToggleDoors openDoors;
	private TogglePusher extend, retract;

    public OpenAndEjectGearSlotWithoutMoving() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	openDoors = new ToggleDoors();
    	extend = new TogglePusher();
    	retract = new TogglePusher();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		openDoors.start();
    		System.out.println("Case 0");
    		state = 1;
    		break;
    	case 1:
    		if(!openDoors.isRunning()) {
    			extend.start();
    			System.out.println("Case 1");
    			state = 2;
    		}
    		break;
    	case 2:
    		if(!extend.isRunning()) {
    			retract.start();
    			System.out.println("Case 2");
    			state = 3;
    		}
    		break;
    	case 3:
    		break;
    	default:
    		System.out.println("Something went wrong in OpenAndEjectGearSlot.java. State: " + state);
    		break;	
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !retract.isRunning() && state == 3;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	openDoors.cancel();
    	extend.cancel();
    	retract.cancel();
    }
}
