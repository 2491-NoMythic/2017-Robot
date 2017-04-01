package com._2491nomythic.watt.commands.dustpan;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Variables;

/**
 * Flips down the dustpan and runs the motor
 */
public class AutomatedPickup extends CommandBase {
	private static AutomatedPickup instance;
	
	public static AutomatedPickup getInstance() {
		if (instance == null) {
			instance = new AutomatedPickup();
		}
		return instance;
	}
	
	/**
	 * Flips down the dustpan and runs the motor
	 */
    public AutomatedPickup() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(dustpan);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	dustpan.flipDown();
    	dustpan.runMotor(Variables.automatedIntakePower);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	dustpan.flipUp();
    	dustpan.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
