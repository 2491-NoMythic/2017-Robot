package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 *
 */
public class DriveStraightToPosition extends CommandBase {
	private double speed, leftSpeed, rightSpeed;
	private double distance, leftDistance, rightDistance;

    public DriveStraightToPosition(double speed, double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	this.speed = speed;
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.resetLeftEncoder();
    	drivetrain.resetRightEncoder();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftDistance = drivetrain.getLeftEncoderDistance();
    	rightDistance = -drivetrain.getRightEncoderDistance();
    	leftSpeed = speed;
    	rightSpeed = speed;
    	
    	if (leftDistance - rightDistance > 1 / 12) {
    		leftSpeed -= 0.05;
    	}
    	else if (rightDistance - leftDistance > 1 / 12) {
    		rightSpeed -= 0.05;
    	}
    	
    	if(distance - drivetrain.getLeftEncoderDistance() < 0.5) {
    		drivetrain.drive(0.25 * leftSpeed, 0.25 * rightSpeed, 0, 0);
    	}
    	else if(distance - drivetrain.getLeftEncoderDistance() < 1) {
    		drivetrain.drive(0.5 * leftSpeed, 0.5 * rightSpeed, 0, 0);
    	}
    	else {
    		drivetrain.drive(leftSpeed, rightSpeed, 0, 0);
    	}
    	
    	System.out.println(drivetrain.getLeftEncoderDistance());
    	System.out.println(drivetrain.getRightEncoderDistance());
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return Math.abs(leftDistance) > distance && Math.abs(rightDistance) > distance;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    	drivetrain.resetLeftEncoder();
    	drivetrain.resetRightEncoder();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
