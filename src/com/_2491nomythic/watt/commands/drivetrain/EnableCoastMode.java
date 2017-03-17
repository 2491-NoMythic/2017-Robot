package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class EnableCoastMode extends CommandBase {
	Timer timer;

    public EnableCoastMode() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	setRunWhenDisabled(true);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	drivetrain.enableVerticalCoastMode();
    	drivetrain.enableHorizontalCoastMode();
    	
    	timer.start();
    	timer.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (timer.get() > 1) {
        	System.out.println("THE ROBOT IS IN COAST MODE. CLICK CANCEL NEXT TO \'Coast Mode\' ON THE SmartDashboard TO TURN COAST MODE OFF.");
        	timer.reset();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.enableVerticalBrakeMode();
    	drivetrain.enableHorizontalBrakeMode();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
