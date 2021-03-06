package com._2491nomythic.watt.commands.gearslot;

import com._2491nomythic.watt.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 * Toggles whether the doors are open
 */
public class ToggleDoors extends CommandBase {
	private Timer timer;
	
	/**
	 * Toggles whether the doors are open
	 */
    public ToggleDoors() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(gearslot);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	timer.reset();
    	    	
    	if(gearslot.getDoors())	gearslot.closeDoors();
    	else gearslot.openDoors();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > 0.05;
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
