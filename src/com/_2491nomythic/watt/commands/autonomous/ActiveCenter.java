package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.gearslot.OpenAndEjectGearSlot;

/**
 * Drives to the center gear peg and deposits a gear
 * @deprecated
 */
public class ActiveCenter extends CommandBase {
	private DriveStraightToPosition impalePeg;
	private OpenAndEjectGearSlot gearDeposit;
	private int state;

	/**
	 * Drives to the center gear peg and deposits a gear
	 * @deprecated
	 */
    public ActiveCenter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	impalePeg = new DriveStraightToPosition(0.5, 6.2);
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
    		impalePeg.start();
    		state = 1;
    		break;
    	case 1:
    		if(!impalePeg.isRunning()) {
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
    	impalePeg.cancel();
    	gearDeposit.cancel();
    }
}
