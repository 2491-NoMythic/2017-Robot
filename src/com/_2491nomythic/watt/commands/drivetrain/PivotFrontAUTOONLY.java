package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Constants;

/**
 *
 */
public class PivotFrontAUTOONLY extends CommandBase {
	private double frontSpeed, backSpeed, distance, initialPosition;

    public PivotFrontAUTOONLY(double frontSpeed, double backSpeed, double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	this.frontSpeed = frontSpeed;
    	this.backSpeed = backSpeed;
    	this.distance = distance;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialPosition = drivetrain.getCenterEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println(drivetrain.getCenterEncoderDistance());
    	
    	drivetrain.driveCenter(Constants.pivotDriveRatio * frontSpeed, backSpeed);
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
