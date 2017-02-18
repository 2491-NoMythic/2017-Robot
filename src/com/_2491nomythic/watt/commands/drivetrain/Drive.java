package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.ControllerMap;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends CommandBase {
	double currentLeftSpeed, currentRightSpeed, lastLeftSpeed, lastRightSpeed, directionMultiplierLeft, directionMultiplierRight;
	double /*leftPower, rightPower,*/ horizontalPower, turnSpeed;
	boolean isShifted;
	
    public Drive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	drivetrain.changeVerticalToSpeed();
    	isShifted = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	turnSpeed = 0.5 * oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveTurnAxis, 0.1);
    	
    	lastLeftSpeed = currentLeftSpeed;
		lastRightSpeed = currentRightSpeed;
		currentLeftSpeed = /*Variables.lowGearMaxSpeed * */(-oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis, 0.05) + turnSpeed);
		currentRightSpeed = /*Variables.lowGearMaxSpeed * */(-oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis, 0.05) - turnSpeed);
		if (Variables.useLinearAcceleration) {
			double leftAcceleration = (currentLeftSpeed - lastLeftSpeed);
			double signOfLeftAcceleration = leftAcceleration / Math.abs(leftAcceleration);
			if (Math.abs(leftAcceleration) > Variables.accelerationSpeed) { // otherwise the power is below accel and is fine
				if (Math.abs(currentLeftSpeed) - Math.abs(lastLeftSpeed) > 0) {
					System.out.println(currentLeftSpeed + " was too high, setting to " + (lastLeftSpeed + (Variables.accelerationSpeed * signOfLeftAcceleration)));
					currentLeftSpeed = lastLeftSpeed + (Variables.accelerationSpeed * signOfLeftAcceleration);
					
				}
				// if the difference between the numbers is positive it is going up
				
			}
			double rightAcceleration = (currentRightSpeed - lastRightSpeed);
			double signOfRightAcceleration = rightAcceleration / Math.abs(rightAcceleration);
			if (Math.abs(rightAcceleration) > Variables.accelerationSpeed) { // otherwise the power is below 0.05 accel and is fine
				if (Math.abs(currentRightSpeed) - Math.abs(lastRightSpeed) > 0) {
					System.out.println(currentRightSpeed + " was too high, setting to " + (lastRightSpeed + (Variables.accelerationSpeed * signOfRightAcceleration)));
					currentRightSpeed = lastRightSpeed + (Variables.accelerationSpeed * signOfRightAcceleration);
				}
				// if the difference between the numbers is positive it is going up
			}
		}
		
		SmartDashboard.putNumber("Right Encoder Distance", drivetrain.getRightEncoderDistance());
		SmartDashboard.putNumber("Left Encoder Distance", drivetrain.getLeftEncoderDistance());
    	
    	horizontalPower = oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveHorizontalAxis, 0.05);
    	
    	currentLeftSpeed = Math.min(/*Variables.lowGearMaxSpeed*/1, Math.abs(currentLeftSpeed)) * (currentLeftSpeed > 0? 1: -1);
    	currentRightSpeed = Math.min(/*Variables.lowGearMaxSpeed*/1, Math.abs(currentRightSpeed)) * (currentRightSpeed > 0? 1: -1);
    	
    	drivetrain.drive(currentLeftSpeed, currentRightSpeed, horizontalPower, horizontalPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
//    	drivetrain.changeVerticalToPercentVbus();
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
