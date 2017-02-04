package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 *
 */
public class RotateDrivetrainCentralWithGyro extends CommandBase {
	private double speed, angle, initialAngle, direction;

    public RotateDrivetrainCentralWithGyro(double speed, double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	this.speed = speed;
    	this.angle = angle;
    	initialAngle = drivetrain.getGyroAngle();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(angle > 0) {
    		direction = 1;
    	}
    	else if(angle < 0) {
    		direction = -1;
    	}
    	drivetrain.driveCenter(direction * speed, -direction * speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(drivetrain.getGyroAngle() - initialAngle) > Math.abs(angle);
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
