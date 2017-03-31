package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.PivotFrontAUTOONLY;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyro;
import com._2491nomythic.watt.commands.gearslot.OpenAndEjectGearSlot;
import com._2491nomythic.watt.commands.gearslot.TogglePusher;

/**
 * Attempts to deposit a gear onto the center gear peg by approaching it diagonally
 */
public class AngledCenter extends CommandBase {
	private DriveStraightToPosition driveNearPeg, landPeg, impalePeg;
	private RotateDrivetrainWithGyro aimForPeg;
	private PivotFrontAUTOONLY squareUp;
	private OpenAndEjectGearSlot eject;
	private TogglePusher extend, retract;
	int state;
	
	// Autonomous positioning numbers
	// Left: As far to the left as possible (- inches from edge)
	// Center: Lined up with peg
	// Right: As far to the right as possible (- inches from center)
	
	//LINUX NUMBER 2 IS REQUIRED
	
	/**
	 * Attempts to deposit a gear onto the center gear peg by approaching it diagonally
	 */
	public AngledCenter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	driveNearPeg = new DriveStraightToPosition(0.75, 4.1);
    	landPeg = new DriveStraightToPosition(0.75, 2.3);
    	impalePeg = new DriveStraightToPosition(0.75,0.45);
    	aimForPeg = new RotateDrivetrainWithGyro(0.25,25);
    	squareUp = new PivotFrontAUTOONLY(0.35, 0.35, -0.35, 0.35, 0.65);
    	eject = new OpenAndEjectGearSlot();
    	extend = new TogglePusher();
    	retract = new TogglePusher();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 1;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("Center Encoder Value: " + drivetrain.getCenterEncoderDistance());
    	
    	System.out.println("State: " + state);
    	
    	switch(state) {
    	case 1:
    			driveNearPeg.start();
    			state++;
    		break;
    	case 2:
    		if(!driveNearPeg.isRunning()) {
    			aimForPeg.start();
    			state++;
    		}
    		break;
    	case 3:
    		if(!aimForPeg.isRunning()) {
    			landPeg.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!landPeg.isRunning()) {
    			squareUp.start();
    			state++;
    		}
    		break;
    	case 5:
    		if(!squareUp.isRunning()) {
    			impalePeg.start();
    			state++;
    		}
    		break;
    	case 6:
    		if(!impalePeg.isRunning()) {
    			eject.start();
    			state++;
    		}
    		break;
    	case 7:
    		if(!eject.isRunning()) {
    			extend.start();
    			state++;
    		}
    		break;
    	case 8:
    		if(!extend.isRunning()) {
    			retract.start();
    			state++;
    		}
    		break;
    	case 9:
    		break;
    	default:
    		System.out.println("Error in autonomous. State: " + state);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !retract.isRunning() && state == 9;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	driveNearPeg.cancel();
    	landPeg.cancel();
    	impalePeg.cancel();
    	aimForPeg.cancel();
    	squareUp.cancel();
    	eject.cancel();
    	extend.cancel();
    	retract.cancel();
    }
}