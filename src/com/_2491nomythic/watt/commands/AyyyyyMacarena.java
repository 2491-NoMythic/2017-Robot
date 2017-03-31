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
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 1:
    		break;
    	case 2:
    		break;
    	case 3:
    		break;
    	case 4:
    		break;
    	case 5:
    		break;
    	case 6:
    		break;
    	case 7:
    		break;
    	case 8:
    		break;
    	case 9:
    		break;
    	case 10:
    		break;
    	case 11:
    		break;
    	case 12:
    		break;
    	case 13:
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
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
