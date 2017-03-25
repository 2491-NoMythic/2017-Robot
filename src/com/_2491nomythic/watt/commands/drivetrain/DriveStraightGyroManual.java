package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.ControllerMap;

/**
 * Drives straight using the gyro at a velocity dictated by the driver
 * @deprecated
 */
public class DriveStraightGyroManual extends CommandBase {
	double distance, speed, adjustment;
	
	/**
	 * Drives straight using the gyro at a velocity dictated by the driver
	 * @deprecated
	 */
    public DriveStraightGyroManual() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	adjustment = drivetrain.getGyroAngle() / 10;
    	speed = -oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis, 0.05);
    	if (speed > 0) {
			drivetrain.drive(Math.min(speed, speed + Math.max(-0.5 * speed, adjustment)), Math.min(speed, speed - Math.min(0.5 * speed, adjustment)), 0, 0);
		}
		else {
			drivetrain.drive(Math.max(speed, speed + Math.min(-0.5 * speed, adjustment)), Math.max(speed, speed - Math.max(0.5 * speed, adjustment)), 0, 0);
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
