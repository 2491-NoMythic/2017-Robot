package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.ResetEncoders;
import com._2491nomythic.watt.commands.gearslot.OpenAndEjectGearSlot;

/**
 *
 */
public class ActiveCenter extends CommandBase {
	private PassiveCenter passiveCenter;
	private OpenAndEjectGearSlot gearDeposit;
	private int state;

    public ActiveCenter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	passiveCenter = new PassiveCenter();
    	gearDeposit = new OpenAndEjectGearSlot();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		passiveCenter.start();
    		state = 1;
    		break;
    	case 1:
    		if(!passiveCenter.isRunning()) {
    			gearDeposit.start();
    			state = 2;
    		}
    		break;
    	case 2:
    		break;
    		
    		default:
    			System.out.println("Active Center GearSlot Auto. Case: " + state);	
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !gearDeposit.isRunning() && state == 2;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	passiveCenter.cancel();
    	gearDeposit.cancel();
    }
}
