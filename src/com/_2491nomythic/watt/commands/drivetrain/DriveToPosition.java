package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Variables;

/**
 *
 */
public class DriveToPosition extends CommandBase {
	//setting up variables for use in command
	double distance;
	double initialEncoderFeetPos, currentEncoderFeetPos;
	double speed;
	
    public DriveToPosition(double feet,double power) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	distance = feet;
    	speed = Math.abs(power) * (feet > 0 ? 1 : -1);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	initialEncoderFeetPos = drivetrain.getLeftEncoderDistance();
		drivetrain.drive(speed * Variables.lowGearMaxSpeed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentEncoderFeetPos = drivetrain.getLeftEncoderDistance();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (distance > 0) {
			if (currentEncoderFeetPos > initialEncoderFeetPos + distance) {
				drivetrain.stop();
				return true;
			}
		}
		else {
			if (currentEncoderFeetPos < initialEncoderFeetPos + distance) {
				drivetrain.stop();
				return true;
			}
		}
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
