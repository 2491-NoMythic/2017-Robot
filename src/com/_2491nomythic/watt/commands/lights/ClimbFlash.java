package com._2491nomythic.watt.commands.lights;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.subsystems.Lights;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
@SuppressWarnings("deprecation")
public class ClimbFlash extends CommandBase {
	private Timer timer;
	private boolean climbTime;
	private int state;

    public ClimbFlash() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(lights);
    	
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	timer.reset();
    	
    	climbTime = false;
    	
    	Lights.activateLights();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(timer.get() > 105) {
    		timer.reset();
    		climbTime = true;
    	}
    	
    	while(climbTime) {
    		switch(state) {
    		case 0:
    			if(timer.get() >= 0.5) {
    				Lights.deactivateLights();
    				state = 1;
    			}
    			break;
    		case 1:
    			if(timer.get() >= 1) {
    				Lights.activateLights();
    				timer.reset();
    				state = 0;
    			}
    			break;
    		default:
    			System.out.println("Something went wrong in ClimbFlash. State: " + state);
    		}
    	}	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Lights.deactivateLights();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
