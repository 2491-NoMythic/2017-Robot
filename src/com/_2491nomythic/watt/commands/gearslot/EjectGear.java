package com._2491nomythic.watt.commands.gearslot;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class EjectGear extends CommandBase {
	Timer timer;
	
    public EjectGear() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(gearslot);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	timer.reset();
    	
    	gearslot.ejectGear();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(timer.get() > Variables.timeToEjectGear) {
    		gearslot.retractEjector();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > Variables.timeToEjectGear;
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
