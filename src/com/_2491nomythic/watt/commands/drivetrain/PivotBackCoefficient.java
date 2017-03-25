package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.settings.Constants;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PivotBackCoefficient extends Command {

    public PivotBackCoefficient() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Variables.backPivotCoefficient = Constants.pivotDriveRatio;
    	Variables.pivotCoefficientAmount = -.5;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
