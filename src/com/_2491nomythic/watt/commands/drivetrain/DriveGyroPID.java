package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;


/**
 *
 */
public class DriveGyroPID extends CommandBase {
	private double target;
    public DriveGyroPID(double target) {
        requires(drivetrain);
    	this.target = target;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.enable();
    	drivetrain.resetGyro();
    	drivetrain.setSetpoint(target);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("Motors Running at " + drivetrain.getPIDOutput() + " power.");
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return drivetrain.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.disable();
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
