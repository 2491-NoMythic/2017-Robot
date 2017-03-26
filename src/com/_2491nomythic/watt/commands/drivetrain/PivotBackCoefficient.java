package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Constants;
import com._2491nomythic.watt.settings.ControllerMap;
import com._2491nomythic.watt.settings.Variables;

/**
 *
 */
public class PivotBackCoefficient extends CommandBase {
	private double speed;

    public PivotBackCoefficient() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Variables.backPivotCoefficient = Constants.pivotDriveRatio;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveHorizontalAxis, .1) < 0) {
    		Variables.pivotCoefficientAmount = -.5;
    	}
    	if (oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveHorizontalAxis, .1) > 0) {
    		Variables.pivotCoefficientAmount = .5;
    	}
    	speed = oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveTurnAxis, .1);
    	drivetrain.driveCenter(speed, Constants.pivotDriveRatio * speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Variables.backPivotCoefficient = 1;
    	Variables.pivotCoefficientAmount = 0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
