package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 * Rotates the robot to a specific position using a process that crudely imitates PID
 */
public class RotateDrivetrainPseudoPID extends CommandBase {
	private double initialAngle, desiredAngle, direction, distanceRemaining, currentAngle;

	/**
	 * Rotates the robot to a specific position using a process that crudely imitates PID
	 * @param desiredAngle The angle the robot turns to, where negative values rotate the robot counterclockwise
	 */
    public RotateDrivetrainPseudoPID(double desiredAngle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.desiredAngle = desiredAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialAngle = drivetrain.getGyroAngle();
    	
    	if(desiredAngle > 0) {
    		direction = 0.5;
    	}
    	else if(desiredAngle < 0) {
    		direction = -0.5;
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentAngle = drivetrain.getGyroAngle() - initialAngle;
    	
    	distanceRemaining = desiredAngle - currentAngle;
    	
    	drivetrain.drive(direction * Math.abs(distanceRemaining / desiredAngle), -direction * Math.abs(distanceRemaining / desiredAngle), 0, 0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(desiredAngle - currentAngle) <= 0;
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
