package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 * Drives straight using encoders for a specified distance at a specified speed
 */
public class DriveStraightToPosition extends CommandBase {
	private double speed, leftSpeed, rightSpeed;
	private double distance, leftDistance, rightDistance, leftStart, rightStart;

	/**
	 * Drives straight using encoders for a specified distance at a specified speed
	 * @param speed The power fed to the vertical drive motors, ranging from 0 to 1, where negative values run the motors backwards
	 * @param distance The distance (in feet) for the robot to drive
	 */
    public DriveStraightToPosition(double speed, double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	this.speed = speed;
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	leftStart = drivetrain.getLeftEncoderDistance();
    	rightStart = drivetrain.getRightEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftDistance = drivetrain.getLeftEncoderDistance() - leftStart;
    	rightDistance = -(drivetrain.getRightEncoderDistance() - rightStart);
    	leftSpeed = speed;
    	rightSpeed = speed;
    	
    	if (leftDistance - rightDistance > 1 / 12) {
    		leftSpeed -= 0.05;
    	}
    	else if (rightDistance - leftDistance > 1 / 12) {
    		rightSpeed -= 0.05;
    	}
    	
    	if(distance - Math.abs(leftDistance) < 0.5 || distance - Math.abs(rightDistance) < 0.5) {
    		drivetrain.drive(0.25 * leftSpeed, 0.25 * rightSpeed, 0, 0);
    	}
    	else if(distance - Math.abs(leftDistance) < 1 || distance - Math.abs(rightDistance) < 1) {
    		drivetrain.drive(0.5 * leftSpeed, 0.5 * rightSpeed, 0, 0);
    	}
    	else {
    		drivetrain.drive(leftSpeed, rightSpeed, 0, 0);
    	}
    	
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return Math.abs(leftDistance) > distance && Math.abs(rightDistance) > distance;
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
