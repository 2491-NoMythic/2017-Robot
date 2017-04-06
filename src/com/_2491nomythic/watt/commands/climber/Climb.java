package com._2491nomythic.watt.commands.climber;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.ControllerMap;

/**
 * Runs the climber motors at a speed specified by a driver
 */
public class Climb extends CommandBase {

	/**
	 * Runs the climber motors at a speed specified by a driver
	 */
    public Climb() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	climber.runClimberMotors(oi.getAxis(ControllerMap.secondaryDriveController, ControllerMap.climbThrottleAxis));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	climber.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
