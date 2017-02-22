package com._2491nomythic.watt.commands.gearslot;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;

/**
 *
 */
public class OpenAndEjectGearSlot extends CommandBase {
	private ToggleGearSlot openDoors, closeDoors;
	private ToggleEjector extend, retract;
	private DriveStraightToPosition backAway;
	private int state;

    public OpenAndEjectGearSlot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	openDoors = new ToggleGearSlot();
    	closeDoors = new ToggleGearSlot();
    	extend = new ToggleEjector();
    	retract = new ToggleEjector();
    	backAway = new DriveStraightToPosition(-1, 2);
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
    		state++;
    	break;
    	
    	case 1:
    		if(!openDoors.isRunning()) {
    			extend.start();
    			state++;
    		}
    		break;
    		
    	case 2:
    		if(!extend.isRunning()) {
    			retract.start();
    			state++;
    		}
    		break;
    		
    	case 3:
    		if(!retract.isRunning()) {
    			backAway.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!backAway.isRunning()) {
    			closeDoors.start();
    			state++;
    		}
    		break;
    		
    	case 5:
    		
    		break;
    		
    	default:
    		System.out.println("Something went wrong in OpenAndEjectGearSlot.java. State: " + state);
    		break;
    	
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return !closeDoors.isRunning();
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	openDoors.cancel();
    	closeDoors.cancel();
    	extend.cancel();
    	retract.cancel();
    	backAway.cancel();
    }
}
