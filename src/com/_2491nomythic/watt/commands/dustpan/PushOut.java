package com._2491nomythic.watt.commands.dustpan;

import com._2491nomythic.watt.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class PushOut extends CommandBase{
Timer timer;

    public PushOut() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(dustpan);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	dustpan.flipUp();
    	timer = new Timer();
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	dustpan.runMotor(1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (timer.get() >= 5){
        	return true;
        }
        else{
        	return false;
        }
    }

    // Called once after isFinished returns true
    protected void end() {
    	dustpan.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
