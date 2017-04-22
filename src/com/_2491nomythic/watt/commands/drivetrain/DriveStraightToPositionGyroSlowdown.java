package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 * Drives straight using the gyro for a specified distance at a specified variableSpeed
 */
public class DriveStraightToPositionGyroSlowdown extends CommandBase {
	double distance, speed, adjustment, variableSpeed, leftDistance, rightDistance, initialLeftDistance, initialRightDistance;
	
	/**
	 * Drives straight using the gyro for a specified distance at a specified variableSpeed
	 * @param variableSpeed The power fed to the vertical drive motors, ranging from -1 to 1, where negative values run the motors backwards
	 * @param distance The distance (in feet) for the robot to drive
	 */
    public DriveStraightToPositionGyroSlowdown(double speed, double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.distance = distance;
    	this.speed = speed;
    	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	variableSpeed = speed;
    	initialLeftDistance = drivetrain.getLeftEncoderDistance();
    	initialRightDistance = drivetrain.getRightEncoderDistance();
    	drivetrain.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	leftDistance = drivetrain.getLeftEncoderDistance();
    	rightDistance = drivetrain.getRightEncoderDistance();
    	
    	if (Math.abs(distance - Math.abs(leftDistance - initialLeftDistance)) < 0.5 || Math.abs(distance - Math.abs(rightDistance - initialRightDistance)) < 0.5) {
    		variableSpeed = 0.25 * speed;
    	}
    	else if (Math.abs(distance - Math.abs(leftDistance - initialLeftDistance)) < 1 || Math.abs(distance - Math.abs(rightDistance - initialRightDistance)) < 1) {
    		variableSpeed = 0.5 * speed;
    	}
    	
    	adjustment = variableSpeed * drivetrain.getRawGyroAngle() / 10.0;
    	
    	if (variableSpeed > 0) {
			drivetrain.drive(Math.min(variableSpeed, variableSpeed - Math.min(0.5 * variableSpeed, adjustment)), Math.min(variableSpeed, variableSpeed + Math.max(-0.5 * variableSpeed, adjustment)), 0, 0);
		}
		else {
			drivetrain.drive(Math.max(variableSpeed, variableSpeed - Math.max(0.5 * variableSpeed, adjustment)), Math.max(variableSpeed, variableSpeed + Math.min(-0.5 * variableSpeed, adjustment)), 0, 0);
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(leftDistance - initialLeftDistance) > distance;
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
