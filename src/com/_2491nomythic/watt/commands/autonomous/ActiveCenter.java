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
    	state = 0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	passiveCenter.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(passiveCenter.isRunning()) {
    		state = 1;
    		gearDeposit.start();
    	}	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !gearDeposit.isRunning() && state == 1;
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
