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
			Variables.shiftUpPercentage = SmartDashboard.getNumber("Shift Up Speed", Variables.shiftUpPercentage);
			Variables.shiftEngagePneumaticsTime = SmartDashboard.getNumber("Time To Engage Pneumatics For Shifter (in seconds)", Variables.shiftEngagePneumaticsTime);
			Variables.shiftTotalTime = SmartDashboard.getNumber("Total Shift Time (in seconds)", Variables.shiftTotalTime);
			SmartDashboard.putNumber("Gyro in Degrees", drivetrain.getGyroAngle());
			Variables.lowGearMaxSpeedFeetPerSecond = SmartDashboard.getNumber("Low Gear Max Speed (ft/s)", Variables.lowGearMaxSpeedFeetPerSecond);
			SmartDashboard.putBoolean("Use Linear Acceleration", Variables.useLinearAcceleration);
			//Variables.drivetrainPID_P = SmartDashboard.getNumber("PID P Value", 20);
			SmartDashboard.putNumber("Low Gear Max Speed (ft/s)", Variables.lowGearMaxSpeedFeetPerSecond);
			
			Variables.shiftUpSpeed = Variables.lowGearMaxSpeed * Variables.shiftUpPercentage;
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
