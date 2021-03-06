package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.ControllerMap;



/**
 * Drives the robot only vertically or horizontally based on driver control
 */
public class DriveLock extends CommandBase {
	private double yAxisValue;
	private double xAxisValue;

	/**
	 * Drives the robot only vertically or horizontally based on driver control
	 */
    public DriveLock() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	yAxisValue = -oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis, 0.05);
    	xAxisValue = oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveHorizontalAxis, 0.05);
    	if (Math.abs(yAxisValue) > Math.abs(xAxisValue)) {
    		drivetrain.drive(yAxisValue, yAxisValue, 0, 0);
    	}
    	else {
    		drivetrain.drive(0, 0, xAxisValue, xAxisValue);
    	}
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
