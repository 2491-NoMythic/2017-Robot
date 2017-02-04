package com._2491nomythic.watt.commands.climber;

import com._2491nomythic.watt.commands.CommandBase;

/**
 *
 */
public class Unravel extends CommandBase {
	private double power;

    public Unravel(double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(climber);
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	climber.runClimberMotors(-power);
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
