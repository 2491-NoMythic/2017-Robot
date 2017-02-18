package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 *
 */
public class DriveStraightToPosition extends CommandBase {
	private double speed;
	private double distance;

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
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(distance - drivetrain.getLeftEncoderDistance() < 1) {
    		drivetrain.drive(0.25 * speed);
    	}
    	else if(distance - drivetrain.getLeftEncoderDistance() < 2) {
    		drivetrain.drive(0.5 * speed);
    	}
    	else {
    		drivetrain.drive(speed);
    	}
    	
    	System.out.println(drivetrain.getLeftEncoderDistance());
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		return drivetrain.getLeftEncoderDistance() >= distance;
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
