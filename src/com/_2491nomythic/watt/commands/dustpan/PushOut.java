package com._2491nomythic.watt.commands.dustpan;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Ejects a gear from the dustpan
 */
public class PushOut extends CommandBase{
	private Timer timer;
	/**
	 * Ejects a gear from the dustpan and drives away from the peg.
	 */
    public PushOut() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(dustpan);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer = new Timer();
    	timer.reset();
    	timer.start();
    	dustpan.flipDown();
    	dustpan.runMotor(Variables.gearEjectPower);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(timer.get() > (Variables.gearEjectTiming/2)) {
    		drivetrain.driveLeft(.2);
    		drivetrain.driveRight(.2);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > Variables.gearEjectTiming;
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
