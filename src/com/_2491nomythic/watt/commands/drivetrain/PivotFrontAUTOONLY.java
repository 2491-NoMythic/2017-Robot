package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 *
 */
public class PivotFrontAUTOONLY extends CommandBase {
	private double leftSpeed, rightSpeed, distance, initialPosition;

    public PivotFrontAUTOONLY(double frontSpeed, double rightSpeed, double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	this.leftSpeed = leftSpeed;
    	this.rightSpeed = rightSpeed;
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialPosition = drivetrain.getCenterEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drivetrain.driveCenter(leftSpeed, rightSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return drivetrain.getCenterEncoderDistance() - initialPosition >= distance;
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
