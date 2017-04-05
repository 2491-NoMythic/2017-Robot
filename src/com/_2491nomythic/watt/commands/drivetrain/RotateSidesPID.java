package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 * Rotates the robot to a specific angle using the left and right motors and PID
 * @deprecated
 */
public class RotateSidesPID extends CommandBase {
	private double initialAngle, desiredAngle, direction;

	/**
	 * Rotates the robot to a specific angle using the left and right motors and PID
	 * @param desiredAngle The angle the robot turns to, where negative values rotate the robot counterclockwise
	 */
    public RotateSidesPID(double desiredAngle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	initialAngle = drivetrain.getGyroAngle();
    	this.desiredAngle = desiredAngle + initialAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(desiredAngle > 0) {
    		direction = 1;
    	}
    	else if(desiredAngle < 0) {
    		direction = -1;
    	}
    	
    	drivetrain.enable();
    	drivetrain.setSetpoint(desiredAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	drivetrain.driveLeftRightPID(direction * drivetrain.getPIDOutput(), -direction * drivetrain.getPIDOutput());
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
