package com._2491nomythic.watt.commands.dustpan;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Ejects a gear from the dustpan
 */
public class PushOut extends CommandBase{
	private Timer timer;
	private DriveStraightToPosition driveBack;
	private boolean hasEjected;
	/**
	 * Ejects a gear from the dustpan
	 */
    public PushOut() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(dustpan);
    	driveBack = new DriveStraightToPosition(-1, 2);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	hasEjected = false;
    	timer = new Timer();
    	timer.reset();
    	timer.start();
    	dustpan.runMotor(Variables.gearEjectPower);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(timer.get() > Variables.gearEjectTiming) {
    		dustpan.flipDown();
    		driveBack.start();
    		hasEjected = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !driveBack.isRunning() && hasEjected;
    }

    // Called once after isFinished returns true
    protected void end() {
    	dustpan.flipUp();
    	dustpan.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
