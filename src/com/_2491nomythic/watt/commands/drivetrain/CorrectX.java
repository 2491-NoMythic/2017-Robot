package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Variables;

/**
 *
 */
public class CorrectX extends CommandBase {
	private double actualX, targetX, speed;
	//for the central wheel drivetrain methods, positive = right.

    public CorrectX(double desiredSpeed, double desiredX) {
    	actualX = Variables.x;
    	targetX = desiredX;
    	speed = desiredSpeed;
    	requires(drivetrain);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (targetX < actualX) {
    		drivetrain.driveCenter(-speed, -speed);
    	}
    	else if (targetX > actualX) {
    		drivetrain.driveCenter(speed, speed);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (targetX < (actualX + 1)) && (targetX > (actualX - 1));
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
