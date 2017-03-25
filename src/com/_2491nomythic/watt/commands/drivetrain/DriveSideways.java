package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 * Drives the robot sideways a specified distance at a specified power
 */
public class DriveSideways extends CommandBase {
	double power, distance;

	/**
	 * Drives the robot sideways a specified distance at a specified power
	 * @param power The power fed to the center motors, ranging from -1 to 1, where negative values run move the robot to the left
	 * @param distance The distance (in feet) for the robot to move
	 */
    public DriveSideways(double power, double distance) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.power = power;
    	this.distance = distance;
    	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.resetCenterEncoder();
    	drivetrain.drive(0, power);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(drivetrain.getCenterEncoderDistance()) > distance;
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
