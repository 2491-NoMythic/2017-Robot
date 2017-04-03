package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 * Rotates the drivetrain to a specified angle using PID
 */
public class RotateDrivetrainWithGyroPID extends CommandBase {
	private double  target, relitive;

	/**
	 * Rotates the drivetrain to a specified angle using PID
	 * @param angle The angle that the robot rotates to, where negative values rotate the robot counterclockwise
	 */
    public RotateDrivetrainWithGyroPID(double angle) {
        // Use requires() here to declare subsystem dependencies
      	requires(drivetrain);
      	target = angle;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	relitive = (drivetrain.getGyroAngle() + target) % 360;
    	drivetrain.setSetpoint(relitive);
    	drivetrain.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

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
