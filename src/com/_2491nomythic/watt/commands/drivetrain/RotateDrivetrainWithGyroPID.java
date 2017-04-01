package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 * Rotates the drivetrain to a specified angle using PID
 */
public class RotateDrivetrainWithGyroPID extends CommandBase {
	private double  angle, initialAngle, direction;

	/**
	 * Rotates the drivetrain to a specified angle using PID
	 * @param angle The angle that the robot rotates to, where negative values rotate the robot counterclockwise
	 */
    public RotateDrivetrainWithGyroPID(double angle) {
        // Use requires() here to declare subsystem dependencies
      	requires(drivetrain);
    	initialAngle = drivetrain.getGyroAngle();
    	this.angle = angle + initialAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.enable();
    	drivetrain.setSetpoint(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.print(drivetrain.getPIDOutput());
    	drivetrain.driveCenterPID(drivetrain.getPIDOutput(), -drivetrain.getPIDOutput());
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
