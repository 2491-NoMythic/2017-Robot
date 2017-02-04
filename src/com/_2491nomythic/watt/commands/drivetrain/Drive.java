package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.ControllerMap;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends CommandBase {
	double currentLeftPower, currentRightPower, lastLeftPower, lastRightPower, directionMultiplierLeft, directionMultiplierRight;
	double /*leftPower, rightPower,*/ horizontalPower, turnPower;
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
    	turnPower = oi.getAxisDeadzonedSquared(ControllerMap.turnDriveController, ControllerMap.driveTurnAxis);
    	
    	lastLeftPower = currentLeftPower;
		lastRightPower = currentRightPower;
		currentLeftPower = -oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis) - turnPower;
		currentRightPower = -oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis) + turnPower;
		if (Variables.useLinearAcceleration) {
			double leftAcceleration = (currentLeftPower - lastLeftPower);
			double signOfLeftAcceleration = leftAcceleration / Math.abs(leftAcceleration);
			if (Math.abs(leftAcceleration) > Variables.accelerationSpeed) { // otherwise the power is below accel and is fine
				if (Math.abs(currentLeftPower) - Math.abs(lastLeftPower) > 0) {
					System.out.println(currentLeftPower + " was too high, setting to " + (lastLeftPower + (Variables.accelerationSpeed * signOfLeftAcceleration)));
					currentLeftPower = lastLeftPower + (Variables.accelerationSpeed * signOfLeftAcceleration);
					
				}
				// if the difference between the numbers is positive it is going up
				
			}
			double rightAcceleration = (currentRightPower - lastRightPower);
			double signOfRightAcceleration = rightAcceleration / Math.abs(rightAcceleration);
			if (Math.abs(rightAcceleration) > Variables.accelerationSpeed) { // otherwise the power is below 0.05 accel and is fine
				if (Math.abs(currentRightPower) - Math.abs(lastRightPower) > 0) {
					System.out.println(currentRightPower + " was too high, setting to " + (lastRightPower + (Variables.accelerationSpeed * signOfRightAcceleration)));
					currentRightPower = lastRightPower + (Variables.accelerationSpeed * signOfRightAcceleration);
				}
				// if the difference between the numbers is positive it is going up
			}
		}
//		drivetrain.drive(currentLeftPower, currentRightPower);
		
		SmartDashboard.putNumber("Right Encoder Distance", drivetrain.getRightEncoderDistance());
		SmartDashboard.putNumber("Left Encoder Distance", drivetrain.getLeftEncoderDistance());
    	
//    	leftPower = -1.0 * oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis);
//    	rightPower = -1.0 * leftPower;
    	horizontalPower = oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveHorizontalAxis);
//    	turnPower = oi.getAxisDeadzonedSquared(ControllerMap.turnDriveController, ControllerMap.driveTurnAxis);
    	
//    	currentLeftPower -= turnPower;
//    	currentRightPower -= turnPower;
    	
    	currentLeftPower = Math.min(1, Math.abs(currentLeftPower)) * (currentLeftPower > 0? 1: -1);
    	currentRightPower = Math.min(1, Math.abs(currentRightPower)) * (currentRightPower > 0? 1: -1);
    	
    	drivetrain.drive(currentLeftPower, currentRightPower, horizontalPower, horizontalPower);
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
