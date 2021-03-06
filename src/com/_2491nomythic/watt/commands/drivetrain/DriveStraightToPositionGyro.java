package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 * Drives straight using the gyro for a specified distance at a specified variableSpeed
 */
public class DriveStraightToPositionGyro extends CommandBase {
	double distance, speed, adjustment;
	
	/**
	 * Drives straight using the gyro for a specified distance at a specified variableSpeed
	 * @param variableSpeed The power fed to the vertical drive motors, ranging from -1 to 1, where negative values run the motors backwards
	 * @param distance The distance (in feet) for the robot to drive
	 */
    public DriveStraightToPositionGyro(double speed, double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.distance = distance;
    	this.speed = speed;
    	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.resetLeftEncoder();
    	drivetrain.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	adjustment = drivetrain.getRawGyroAngle() / 10.0;
    	if (speed > 0) {
			drivetrain.drive(Math.min(speed, speed - Math.min(0.5 * speed, adjustment)), Math.min(speed, speed + Math.max(-0.5 * speed, adjustment)), 0, 0);
		}
		else {
			drivetrain.drive(Math.max(speed, speed - Math.max(0.5 * speed, adjustment)), Math.max(speed, speed + Math.min(-0.5 * speed, adjustment)), 0, 0);
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(drivetrain.getLeftEncoderDistance()) > distance;
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
