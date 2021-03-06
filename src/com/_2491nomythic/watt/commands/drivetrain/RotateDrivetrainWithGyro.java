package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 * Rotates the drivetrain a specified angle at a specified power
 */
public class RotateDrivetrainWithGyro extends CommandBase {
	private double speed;
	private double angle;
	private double direction;
	private double initialAngle;

	/**
	 * Rotates the drivetrain a specified angle at a specified power
	 * @param speed The power fed to the motors, ranging from 0 to 1
	 * @param angle The angle that the robot rotates to, where negative values rotate the robot counterclockwise
	 */
    public RotateDrivetrainWithGyro(double speed, double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	this.speed = speed;
    	this.angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialAngle = drivetrain.getGyroAngle();
    	if(angle > 0) {
    		direction = 1;
    	}
    	else if(angle < 0) {
    		direction = -1;
    	}
    	drivetrain.drive(direction * speed, -direction * speed, 0, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(drivetrain.getGyroAngle() - initialAngle) >= Math.abs(angle);
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
