package com._2491nomythic.watt.commands;

import com._2491nomythic.watt.commands.dustpan.FlipDustpan;
import com._2491nomythic.watt.commands.gearslot.ToggleDoors;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class AyyyyyMacarena extends CommandBase {
	private ToggleDoors open, close;
	private FlipDustpan lower, raise;
	private Timer timer;
	private double macarenaTime, numberOfCycles;
	private int state;

    public AyyyyyMacarena() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	open = new ToggleDoors();
    	close = new ToggleDoors();
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 1;
    	numberOfCycles = 0;
    	
    	//fairly accurate time for macarenaTime
    	macarenaTime = 0.60;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 1:
    		timer.start();
    		timer.reset();
    		
    		numberOfCycles++;
    		
    		drivetrain.drive(0.75, 0.75, 0, 0);
    		
    		state++;
    		break;
    		
    	case 2:
    		if(timer.get() > macarenaTime) {
    			drivetrain.drive(-0.75, -0.75, 0, 0);
    			state++;
    		}
    		break;
    		
    	case 3:
    		if(timer.get() > 2 * macarenaTime) {
    			drivetrain.stop();
    			drivetrain.drive(0, 0, -0.75, -0.75);
    			state++;
    		}
    		break;
    		
    	case 4:
    		if(timer.get() > 3 * macarenaTime) {
    			drivetrain.drive(0, 0, 0.75, 0.75);
    			state++;
    		}
    		break;
    		
    	case 5:
    		if(timer.get() > 4 * macarenaTime) {
    			drivetrain.stop();
    			drivetrain.drive(-0.75, 0.75, 0, 0);
    			state++;
    		}
    		break;
    		
    	case 6:
    		if(timer.get() > 5 * macarenaTime) {
    			drivetrain.drive(0.75, 0.75, 0, 0);
    			state++;
    		}
    		break;
    		
    	case 7:
    		if(timer.get() > 6 * macarenaTime) {
    			drivetrain.stop();
    			open.start();
    			state++;
    		}
    		break;
    		
    	case 8:
    		if(timer.get() > 7 * macarenaTime) {
    			close.start();
    			state++;
    		}
    		break;
    		
    	case 9:
    		if(timer.get() > 8 * macarenaTime) {
    			lower.start();
    			state++;
    		}
    		break;
    		
    	case 10:
    		if(timer.get() > 9 * macarenaTime) {
    			raise.start();
    			state++;
    		}
    		break;
    		
    	case 11:
    		if(timer.get() > 10 * macarenaTime) {
    			lights.deactivateLights();
    			state++;
    		}
    		break;
    		
    	case 12:
    		if(timer.get() > 11 * macarenaTime) {
    			lights.activateLights();
    			state++;
    		}
    		break;
    		
    	case 13:
    		if(timer.get() > 12 * macarenaTime) {
    			drivetrain.drive(0.8, -0.8, 0, 0);
    			state++;
    		}
    		break;
    		
    	case 14:
    		//13 and 14 for the turn, 15 for the clap
    		if(timer.get() > 15 * macarenaTime) {
    			drivetrain.stop();
    			state++;
    		}
    		break;
    		
    	default:
    		System.out.println("Ayyyyyy Macarena! State: " + state);
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return numberOfCycles == 5 && state ==  1;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	open.cancel();
    	close.cancel();
    	
    	end();
    }
}
