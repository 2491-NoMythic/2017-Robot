package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.ControllerMap;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Drive extends CommandBase {
	double currentLeftSpeed, currentRightSpeed, lastLeftSpeed, lastRightSpeed, directionMultiplierLeft, directionMultiplierRight, futureLeftSpeed, futureRightSpeed;
	double /*leftPower, rightPower,*/ horizontalPower, turnSpeed;
	int state;
	boolean isShifted, isShifting;
	Timer timer;
	
    public Drive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	drivetrain.changeVerticalToSpeed();
    	isShifted = false;
    	timer.start();
    	timer.reset();
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	turnSpeed = oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveTurnAxis, 0.1);
    	
    	lastLeftSpeed = currentLeftSpeed;
		lastRightSpeed = currentRightSpeed;
    	
		if (Variables.useAutomaticTransmission) {
			if (((Math.abs(drivetrain.getLeftEncoderRate()) > Variables.shiftUpSpeed && Math.abs(drivetrain.getRightEncoderRate()) > Variables.shiftUpSpeed) || oi.getButton(ControllerMap.mainDriveController, ControllerMap.manualShiftButton)) && !isShifted && !isShifting && timer.get() > 1) {
				isShifting = true;
				futureLeftSpeed = lastLeftSpeed * Variables.shiftUpNewPower;
				futureRightSpeed = lastRightSpeed * Variables.shiftUpNewPower;
				currentLeftSpeed = 0;
				currentRightSpeed = 0;
			}
			else if (Math.abs(lastLeftSpeed) < Variables.shiftDownSpeed && Math.abs(lastRightSpeed) < Variables.shiftDownSpeed && isShifted && !isShifting && timer.get() > 1 && !oi.getButton(ControllerMap.mainDriveController, ControllerMap.manualShiftButton)) {
				isShifting = true;
				futureLeftSpeed = lastLeftSpeed * Variables.shiftDownNewPower;
				futureRightSpeed = lastRightSpeed * Variables.shiftDownNewPower;
				currentLeftSpeed = 0;
				currentRightSpeed = 0;
			}
			
			if (isShifted) {
				currentLeftSpeed = (-oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis, 0.05) + turnSpeed / 6);
				currentRightSpeed = (-oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis, 0.05) - turnSpeed / 6);
			}
			else if (!isShifting){
				currentLeftSpeed = (-oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis, 0.05) + 0.5 * turnSpeed);
				currentRightSpeed = (-oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis, 0.05) - 0.5 * turnSpeed);
			}
			
			if (isShifting) {
				switch (state) {
				case 0:
					timer.reset();
					drivetrain.enableVerticalCoastMode();
					state = 1;
					break;
				case 1:
					if (timer.get() > Variables.shiftEngagePneumaticsTime) {
						if (isShifted) drivetrain.shiftToLowGear();
						else drivetrain.shiftToHighGear();
						state = 2;
					}
					break;
				case 2:
					if (timer.get() > Variables.shiftTotalTime) {
						drivetrain.enableVerticalBrakeMode();
						timer.reset();
						state = 0;
						lastLeftSpeed = futureLeftSpeed;
						lastRightSpeed = futureRightSpeed;
						isShifting = false;
						if (isShifted) isShifted = false;
						else isShifted = true;
					}
					break;
				default:
					System.out.println("Uh oh, something went wrong in Drive.java. State = " + state);
				}
			}
		}
		else {
			currentLeftSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis, 0.05) + turnSpeed;
			currentRightSpeed = -oi.getAxisDeadzonedSquared(ControllerMap.mainDriveController, ControllerMap.driveVerticalAxis, 0.05) - turnSpeed;
		}
		
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
    	    	
    	drivetrain.drive(currentLeftSpeed+Variables.pivotCoefficientAmount, currentRightSpeed+(Variables.pivotCoefficientAmount*-1), horizontalPower*Variables.frontPivotCoefficient, horizontalPower*Variables.backPivotCoefficient);
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
