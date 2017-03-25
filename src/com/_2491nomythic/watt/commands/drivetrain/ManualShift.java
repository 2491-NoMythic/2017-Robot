package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.Timer;

/**
 * Shifts the drivetrain's transmission manually
 */
public class ManualShift extends CommandBase {
	private Timer timer;
	private int state;

	/**
	 * Shifts the drivetrain's transmission manually
	 */
    public ManualShift() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	timer.reset();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state){
    	case 0:
    		if(timer.get() > 0.5) {
    			Variables.driveRestriction = 0.5;
    	    	drivetrain.shiftToHighGear();
    			state = 1;
    		}
    		break;
    	case 1:
    		if(timer.get() > 1) {
    			Variables.driveRestriction = 1;
    			state = 2;
    		}
    		break;
    	case 2:
    		break;
    	default:
    		System.out.println("Uh oh, something went wrong in ManualShift.java, state is " + state);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.shiftToLowGear();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Variables.driveRestriction = 1;
    	end();
    }
}
