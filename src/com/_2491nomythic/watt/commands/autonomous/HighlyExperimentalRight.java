package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.PivotFrontAUTOONLY;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyro;
import com._2491nomythic.watt.commands.gearslot.OpenAndEjectGearSlot;
import com._2491nomythic.watt.commands.gearslot.TogglePusher;

/**
 *
 */
public class HighlyExperimentalRight extends CommandBase {
	private DriveStraightToPosition drivePastPeg, landPeg, impalePeg;
	private RotateDrivetrainWithGyro aimForPeg;
	private PivotFrontAUTOONLY squareUp;
	private OpenAndEjectGearSlot eject;
	private TogglePusher extend, retract;
	private int state;
	
	// Autonomous positioning numbers
		// Left: As far to the left as possible (- inches from edge)
		// Center: Lined up with peg
		// Right: As far to the right as possible (- inches from center)

    public HighlyExperimentalRight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drivePastPeg = new DriveStraightToPosition(0.75,7.9);
    	landPeg = new DriveStraightToPosition(0.6,4.6);
    	impalePeg = new DriveStraightToPosition(0.85,0.35);
    	aimForPeg = new RotateDrivetrainWithGyro(-0.4,60);
    	squareUp = new PivotFrontAUTOONLY(0.35,0.35,-0.35,0.5);
    	eject = new OpenAndEjectGearSlot();
    	extend = new TogglePusher();
    	retract = new TogglePusher();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		drivePastPeg.start();
    		state++;
    		break;
    	case 1:
    		if(!drivePastPeg.isRunning()) {
    			aimForPeg.start();
    			state++;
    		}
    		break;
    	case 2:
    		if(!aimForPeg.isRunning()) {
    			landPeg.start();
    			state++;
    		}
    		break;
    	case 3:
    		if(!landPeg.isRunning()) {
    			squareUp.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!squareUp.isRunning()) {
    			impalePeg.start();
    			state++;
    		}
    		break;
    	case 5:
    		if(!impalePeg.isRunning()) {
    			eject.start();
    			state++;
    		}
    		break;
    	case 6:
    		if(!eject.isRunning()) {
    			extend.start();
    			state++;
    		}
    		break;
    	case 7:
    		if(!extend.isRunning()) {
    			retract.start();
    			state++;
    		}
    		break;
    	case 8:
    		break;
    	default:
    		System.out.println("Something went wrong in auto switchcase. State: " + state);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state == 8 && !retract.isRunning();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	drivePastPeg.cancel();
    	landPeg.cancel();
    	impalePeg.cancel();
    	aimForPeg.cancel();
    	squareUp.cancel();
    	eject.cancel();
    	extend.cancel();
    	retract.cancel();
    }
}
