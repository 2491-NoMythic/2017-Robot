package com._2491nomythic.watt.commands.vision;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Constants;
import com._2491nomythic.watt.settings.Variables;

/**
 * Rotates the robot to line up to the gear peg using vision
 */
public class AngleOnPeg extends CommandBase {
	private double actualX, targetX, speed;
	private int margin;
	private boolean isDone;
	//for the central wheel drivetrain methods, positive = right.

	/**
	 * Rotates the robot to line up to the gear peg using vision
	 * @param desiredSpeed The power fed to the motors, ranging from 0 to 1
	 */
    public AngleOnPeg(double desiredSpeed) {
    	isDone = false;
    	actualX = Variables.x1;
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
    		if (targetX + margin < actualX) {
    			drivetrain.driveLeft(-speed);
    			drivetrain.driveRight(speed);
    		}
    		else if (targetX - margin > actualX) {
    			drivetrain.driveLeft(speed);
    			drivetrain.driveRight(-speed);
    		}
    		else {
    			drivetrain.stop();
    			isDone = true;
    		}
    	}
    	else {
    		drivetrain.stop();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
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
