package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

/**
 * Rotates the drivetrain to a specified angle using PID
 */
public class RotateDrivetrainWithGyroPID extends CommandBase {
	private double  target, relative;
	private boolean type;

	/**
	 * Rotates the drivetrain to a specified angle using PID
	 * @param angle The angle that the robot rotates to, where negative values rotate the robot counterclockwise
	 * @param absolute Set true for absolute, false for relative 
	 */
    public RotateDrivetrainWithGyroPID(double angle, boolean absolute) {
        // Use requires() here to declare subsystem dependencies
      	requires(drivetrain);
      	target = angle;
    	type = absolute;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	relative = ((drivetrain.getGyroAngle() + target) % 360 + 360) % 360;
    	System.out.println(relative);
    	if (type) { //absolute
    		drivetrain.setSetpoint(target);
    	}
    	else { //relative
    		drivetrain.setSetpoint(relative);
    	}
    	drivetrain.enable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
