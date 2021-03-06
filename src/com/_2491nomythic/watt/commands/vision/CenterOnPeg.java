package com._2491nomythic.watt.commands.vision;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Constants;
import com._2491nomythic.watt.settings.Variables;


/**
 * Horizontally moves the robot to line up to the gear peg using vision
 */
public class CenterOnPeg extends CommandBase {
	private boolean isDone;
	private int margin;
	private double speed;
	
	/**
	 * Horizontally moves the robot to line up to the gear peg using vision
	 * @param desiredSpeed The power fed to the motor, ranging from 0 to 1
	 */
    public CenterOnPeg(double desiredSpeed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	margin = Constants.errorMargin;
    	speed = desiredSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isDone = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Variables.hasTarget) {
    		if (Constants.xPerfectValue >= Variables.x1 + margin){
    			drivetrain.driveCenter(-speed, -speed);
    		}
    		else if (Constants.xPerfectValue <= Variables.x1 - margin){
    			drivetrain.driveCenter(speed, speed);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
