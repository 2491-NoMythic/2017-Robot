package com._2491nomythic.robot.commands.drivetrain;

import com._2491nomythic.robot.commands.CommandBase;
import com._2491nomythic.robot.settings.ControllerMap;
import com._2491nomythic.robot.settings.Variables;

/**
 *
 */
public class Drive extends CommandBase {
	double leftPower, rightPower, horizontalPower, turnPower;
	boolean isShifted;
	
    public Drive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isShifted = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (isShifted && drivetrain.getLeftEncoderRate() < Variables.shiftDownSpeed && drivetrain.getRightEncoderRate() < Variables.shiftUpSpeed) {
    		drivetrain.shiftToLowGear();
    		isShifted = false;
    	}
    	else if (!isShifted && drivetrain.getLeftEncoderRate() > Variables.shiftUpSpeed && drivetrain.getRightEncoderRate() > Variables.shiftUpSpeed) {
    		drivetrain.shiftToHighGear();
    		isShifted = true;
    	}
    	
    	leftPower = -oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveVerticalAxis);
    	rightPower = leftPower;
    	horizontalPower = oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveHorizontalAxis);
    	turnPower = oi.getAxisDeadzonedSquared(ControllerMap.driveController, ControllerMap.driveHorizontalAxis);
    	
    	leftPower += turnPower;
    	rightPower -= turnPower;
    	
    	leftPower = Math.min(1, Math.abs(leftPower)) * (leftPower > 0? 1: -1);
    	rightPower = Math.min(1, Math.abs(rightPower)) * (rightPower > 0? 1: -1);
    	
    	drivetrain.drive(leftPower, rightPower, horizontalPower, horizontalPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
