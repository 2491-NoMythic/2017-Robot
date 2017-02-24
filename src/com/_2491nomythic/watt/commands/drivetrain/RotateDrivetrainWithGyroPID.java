package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RotateDrivetrainWithGyroPID extends CommandBase {
	private double  angle, initialAngle, direction;

    public RotateDrivetrainWithGyroPID(double angle) {
        // Use requires() here to declare subsystem dependencies
      	requires(drivetrain);
    	initialAngle = drivetrain.getGyroAngle();
    	this.angle = angle + initialAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(angle > 0) {
    		direction = 1;
    	}
    	else if(angle < 0) {
    		direction = -1;
    	}
    	drivetrain.enable();
    	drivetrain.setSetpoint(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drivetrain.drivePID(direction * drivetrain.getPIDOutput(), -direction * drivetrain.getPIDOutput());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return drivetrain.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    	drivetrain.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
