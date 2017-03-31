package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Constants;
import com._2491nomythic.watt.settings.ControllerMap;


/**
 *
 */
public class PivotDriveFront extends CommandBase {
	private double turnSpeed, centerSpeed, speed;

    public PivotDriveFront() {
    	centerSpeed = turnSpeed * .5;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	speed = oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis, .1);
    	if (Math.abs(oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveTurnAxis, .1)) > 0) {
    		turnSpeed = oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveTurnAxis, .1);
    	}
    	else {
    		turnSpeed = oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveHorizontalAxis, .1);
    	}
    	drivetrain.drive(speed);
    	drivetrain.driveLeft(turnSpeed);
    	drivetrain.driveRight(-turnSpeed);
    	drivetrain.driveCenter(Constants.pivotDriveRatio * centerSpeed, centerSpeed);
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
