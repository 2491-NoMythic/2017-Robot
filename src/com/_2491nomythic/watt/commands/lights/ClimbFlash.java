package com._2491nomythic.watt.commands.lights;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.subsystems.Lights;

import edu.wpi.first.wpilibj.Timer;

/**
 * Flashes the lights during the endgame
 * @deprecated
 */
public class ClimbFlash extends CommandBase {
	private Timer overallTimer, flashTimer;
	private boolean thirtyLeft, fifteenLeft;
	private int state;

	/**
	 * Flashes the lights during the endgame
	 * @deprecated
	 */
    public ClimbFlash() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(lights);
    	
    	overallTimer = new Timer();
    	flashTimer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	overallTimer.start();
    	overallTimer.reset();
    	
    	state = 0;
    	thirtyLeft = false;
    	fifteenLeft = false;
    	
    	Lights.activateLights();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(overallTimer.get() > 120) {
    		flashTimer.start();
    		flashTimer.reset();
    		fifteenLeft = true;
    		thirtyLeft = false;
    	} else if(overallTimer.get() > 105) {
    		flashTimer.reset();
    		thirtyLeft = true;
    		fifteenLeft = false;
    	}
    	
    	
    	
    	while(thirtyLeft) {
    		switch(state) {
    		case 0:
    			if(flashTimer.get() >= 1) {
    				Lights.deactivateLights();
    				state = 1;
    			}
    			break;
    		case 1:
    			if(flashTimer.get() >= 2) {
    				Lights.activateLights();
    				flashTimer.reset();
    				state = 0;
    			}
    			break;
    		default:
    			System.out.println("Something went wrong in ClimbFlash. State: " + state);
    			break;
    		}
    	}	
    	
    	while(fifteenLeft) {
    		switch(state) {
    		case 0:
    			if(flashTimer.get() >= 0.5) {
    				Lights.deactivateLights();
    				state = 1;
    			}
    			break;
    		case 1:
    			if(flashTimer.get() >= 1) {
    				Lights.activateLights();
    				flashTimer.reset();
    				state = 0;
    			}
    			break;
    		default:
    			System.out.println("Something went wrong in ClimbFlash. State: " + state);
    			break;
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return overallTimer.get() >= 135;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Lights.activateLights();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
