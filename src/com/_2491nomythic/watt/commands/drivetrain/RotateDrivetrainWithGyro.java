package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Variables;

/**
 *
 */
public class RotateDrivetrainWithGyro extends CommandBase {
	private double speed;
	private double angle;
	private double direction;
	private double initialAngle;

    public RotateDrivetrainWithGyro(double speed, double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	this.speed = speed * Variables.lowGearMaxSpeed;
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
    	drivetrain.drive(direction * speed, -direction * speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(drivetrain.getGyroAngle() - initialAngle) >= Math.abs(angle);
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
