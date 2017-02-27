package com._2491nomythic.watt.commands.gearslot;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;

/**
 *
 */
public class OpenAndEjectGearSlot extends CommandBase {
	private ToggleDoors openDoors, closeDoors;
	private TogglePusher extend, retract;
	private DriveStraightToPosition backAway;
	private int state;

    public OpenAndEjectGearSlot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	openDoors = new ToggleDoors();
    	closeDoors = new ToggleDoors();
    	extend = new TogglePusher();
    	retract = new TogglePusher();
    	backAway = new DriveStraightToPosition(-1, 2);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.resetLeftEncoder();
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
    		if(!retract.isRunning()) {
    			backAway.start();
    			System.out.println("Case 3");
    			state = 4;
    		}
    		break;
    	case 4:
    		if(!backAway.isRunning()) {
    			closeDoors.start();
    			System.out.println("Case 4");
    			state = 5;
    		}
    		break;
    	case 5:
    		System.out.println("Case 5");
    		break;
    	default:
    		System.out.println("Something went wrong in OpenAndEjectGearSlot.java. State: " + state);
    		break;	
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return !closeDoors.isRunning() && state == 5;
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
