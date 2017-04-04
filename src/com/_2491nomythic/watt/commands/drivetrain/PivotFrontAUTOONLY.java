package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Constants;

/**
 * Rotates the robot around its front edge for a specified distance at a specified speed
 */
public class PivotFrontAUTOONLY extends CommandBase {
	private double frontSpeed, backSpeed, distance, leftSpeed, rightSpeed, initialPosition;

	/**
	 * Rotates the robot around its front edge for a specified distance at a specified speed
	 * @param frontSpeed The power fed to the front motor, before multiplication by Constants.pivotDriveRatio, ranging from -1 to 1, where negative values run the motor backwards
	 * @param backSpeed The power fed to the back motor, ranging from -1 to 1, where negative values run the motor backwards
	 * @param leftRightSpeed The power fed to the left and right motors, ranging from -1 to 1, where all values rotate the robot counterclockwise
	 * @param distance The distance that the robot moves horizontally before stopping
	 */
	public PivotFrontAUTOONLY(double frontSpeed, double backSpeed, double leftSpeed, double rightSpeed, double distance) { //TODO fix javadoc
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	this.frontSpeed = frontSpeed;
    	this.backSpeed = backSpeed;
    	this.distance = distance;
    	this.leftSpeed = leftSpeed;
    	this.rightSpeed = rightSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialPosition = drivetrain.getCenterEncoderDistance();
    }

    // Called repeatedly when this Command is scheduled to run 
    protected void execute() {
    	System.out.println("Pivot Front Auto Only is running");
    	System.out.println(drivetrain.getCenterEncoderDistance());
    	
    	drivetrain.drive(leftSpeed, rightSpeed, frontSpeed * Constants.pivotDriveRatio, backSpeed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Math.abs(drivetrain.getCenterEncoderDistance() - initialPosition) >= Math.abs(distance);
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
