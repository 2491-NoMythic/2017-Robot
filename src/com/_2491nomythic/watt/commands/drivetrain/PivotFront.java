package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Constants;
import com._2491nomythic.watt.settings.ControllerMap;

/**
 * Rotates the robot around the center of the front edge
 */
public class PivotFront extends CommandBase {
	private double speed;

	/**
	 * Rotates the robot around the center of the front edge
	 */
    public PivotFront() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	speed = 0.5 * oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveTurnAxis, 0.1);
    	
    	drivetrain.driveCenter(-speed * Constants.pivotDriveRatio, -speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
