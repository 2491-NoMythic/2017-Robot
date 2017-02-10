package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Variables;

/**
*
*/
public class HoldPosition extends CommandBase {
	double speed;
	
	public HoldPosition() {
		// Use requires() here to declare subsystem dependencies
		requires(drivetrain);
		
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		drivetrain.resetLeftEncoder();
		drivetrain.resetRightEncoder();
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		speed = (Math.abs(drivetrain.getLeftEncoderDistance() * 3) > 1) ? 1.0 : Math.abs(drivetrain.getLeftEncoderDistance() * 3.0);
		if(drivetrain.getLeftEncoderDistance() > 0.05) {
			drivetrain.drive(-speed * Variables.lowGearMaxSpeed);
		}
		else if(drivetrain.getLeftEncoderDistance() < -0.05) {
			drivetrain.drive(speed * Variables.lowGearMaxSpeed);
		}
		else {
			drivetrain.stop();
		}
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