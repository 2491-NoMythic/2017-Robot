package com._2491nomythic.watt.commands.vision;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Constants;
import com._2491nomythic.watt.settings.Variables;

/**
 *
 */
public class VisionSwivel extends CommandBase {
	private double actualX, targetX, speed;
	private int margin;
	//for the central wheel drivetrain methods, positive = right.

    public VisionSwivel(double desiredSpeed) {
    	actualX = Variables.x;
    	targetX = Constants.xPerfectValue;
    	speed = desiredSpeed;
    	margin = Constants.errorMargin;
    	requires(drivetrain);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Variables.hasTarget) {
    		if (targetX < actualX) {
    			drivetrain.driveLeft(-speed);
    			drivetrain.driveRight(speed);
    		}
    		else if (targetX > actualX) {
    			drivetrain.driveLeft(speed);
    			drivetrain.driveRight(-speed);
    		}
    	}
    	else {
    		drivetrain.stop();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (targetX < (actualX + margin)) && (targetX > (actualX - margin));
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
