package com._2491nomythic.robot.commands.climber;

import com._2491nomythic.robot.commands.CommandBase;

/**
 *
 */
public class Climb extends CommandBase {
	private double power;

    public Climb(double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(climber);
    	this.power = power;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	climber.runClimberMotors(power);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return climber.getLimitSwitch();
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