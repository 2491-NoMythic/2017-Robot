package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.gearslot.OpenAndEjectGearSlot;
import com._2491nomythic.watt.commands.vision.VisionSwivel;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Places the gear on the center peg using vision
 */
public class VisionCenter extends Command {
	private DriveStraightToPosition approachPeg, impalePeg;
	private VisionSwivel lineUp;
	private OpenAndEjectGearSlot eject;
	private int state;

	/**
	 * Places the gear on the center peg using vision
	 */
    public VisionCenter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	//Values inaccurate
    	approachPeg = new DriveStraightToPosition(0.85,4);
    	impalePeg = new DriveStraightToPosition(0.85,2);
    	lineUp = new VisionSwivel(0.2);
    	eject = new OpenAndEjectGearSlot();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		approachPeg.start();
    		state++;
    		break;
    	case 1:
    		if(!approachPeg.isRunning()) {
    			lineUp.start();
    			state++;
    		}
    		break;
    	case 2:
    		if(!lineUp.isRunning()) {
    			impalePeg.start();
    			state++;
    		}
    		break;
    	case 3:
    		if(!impalePeg.isRunning()) {
    			eject.start();
    			state++;
    		}
    		break;
    	case 4:
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !eject.isRunning() && state == 4;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	approachPeg.cancel();
    	lineUp.cancel();
    	impalePeg.cancel();
    	eject.cancel();
    }
}
