package com._2491nomythic.watt.commands;


import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UpdateDriverstation extends CommandBase {
	private Timer timer;
	private double nextRun;

    public UpdateDriverstation() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(updateDriverstationSubsystem);
		setRunWhenDisabled(true);
		timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
		timer.reset();
		nextRun = timer.get();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (timer.get() > nextRun) {
			nextRun = nextRun + 0.1;
			
			Variables.useLinearAcceleration = SmartDashboard.getBoolean("Use Linear Acceleration", true);
			Variables.lowGearMaxSpeed = SmartDashboard.getNumber("Low Gear Max Speed (ft/s)", Variables.lowGearMaxSpeed);
			SmartDashboard.putBoolean("Use Linear Acceleration", Variables.useLinearAcceleration);
			SmartDashboard.putNumber("Low Gear Max Speed (ft/s)", Variables.lowGearMaxSpeed);
		}
			
    	
			
			
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	timer.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}