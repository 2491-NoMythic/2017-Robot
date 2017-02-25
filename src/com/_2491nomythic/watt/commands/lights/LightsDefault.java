package com._2491nomythic.watt.commands.lights;

import com._2491nomythic.watt.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class LightsDefault extends CommandBase {
	private Timer timer;
    public LightsDefault() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(lights);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.reset();
    	timer.start();
    	;
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	lights.setColors(127);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() >= 1000;
    }

    // Called once after isFinished returns true
    protected void end() {
    	lights.shutOffRed();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
