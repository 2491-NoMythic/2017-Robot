package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.PivotFrontAUTOONLY;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.watt.commands.gearslot.OpenAndEjectGearSlot;
import com._2491nomythic.watt.commands.gearslot.TogglePusher;

import edu.wpi.first.wpilibj.Timer;

/**
 * Attempts to deposit a gear onto the left gear peg by approaching it diagonally
 */
public class BlueLeft extends CommandBase {
	private DriveStraightToPosition drivePastPeg, landPeg, impalePeg, driveIntoNeutral, driveTowardsDispenser;
	private RotateDrivetrainWithGyroPID aimForPeg, aimForNeutral, aimForDispenser;
	private OpenAndEjectGearSlot eject;
	private PivotFrontAUTOONLY squareUp;
	private TogglePusher extend, retract;
	private int state;
	private Timer timer;
	
	// Autonomous positioning numbers
		// Left: As far to the left as possible (- inches from edge)
		// Center: Lined up with peg
		// Right: As far to the right as possible (- inches from center)

	/**
	 * Attempts to deposit a gear onto the left gear peg by approaching it diagonally
	 */
    public BlueLeft() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drivePastPeg = new DriveStraightToPosition(0.6, 8.1);
    	landPeg = new DriveStraightToPosition(0.6, 4.7);
    	impalePeg = new DriveStraightToPosition(0.85, 0.35);
    	driveIntoNeutral = new DriveStraightToPosition(1, 8);
    	driveTowardsDispenser = new DriveStraightToPosition(1, 15);
    	aimForPeg = new RotateDrivetrainWithGyroPID(80);
    	squareUp = new PivotFrontAUTOONLY(0.35, 0.35, -0.35, 0.35, 0.4);
    	eject = new OpenAndEjectGearSlot();
    	extend = new TogglePusher();
    	retract = new TogglePusher();
    	timer = new Timer();
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
    			timer.start();
    			timer.reset();
    			aimForPeg.start();
    			state++;
    		}
    		break;
    	case 2:
    		if(timer.get() > 1.1 || !aimForPeg.isRunning()) {
    			aimForPeg.cancel();
    			timer.reset();
    			landPeg.start();
    			state++;
    		}
    		break;
    	case 3:
    		if(!landPeg.isRunning() || timer.get() > 2.4) {
    			squareUp.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!squareUp.isRunning()) {
    			timer.reset();
    			impalePeg.start();
    			state++;
    		}
    		break;
    	case 5:
    		if(!impalePeg.isRunning() || timer.get() > 1.4) {
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
    		if(!retract.isRunning()) {
    			timer.reset();
    			aimForNeutral.start();
    			state++;
    		}
    		break;
    	case 9:
    		if(!aimForNeutral.isRunning() || timer.get() > 1.1) {
    			aimForNeutral.cancel();
    			driveIntoNeutral.start();
    			state++;
    		}
    		break;
    	case 10:
    		if(!driveIntoNeutral.isRunning()) {
    			timer.reset();
    			aimForDispenser.start();
    			state++;
    		}
    		break;
    	case 11:
    		if(!aimForDispenser.isRunning() || timer.get() > 0.6) {
    			aimForDispenser.cancel();
    			driveTowardsDispenser.start();
    			state++;
    		}
    		break;
    	case 12:
    		break;
    	default:
    		System.out.println("Something went wrong in auto switchcase. State: " + state);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state == 12 && !driveTowardsDispenser.isRunning();
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
    	driveIntoNeutral.cancel();
    	driveTowardsDispenser.cancel();
    	aimForPeg.cancel();
    	aimForDispenser.cancel();
    	squareUp.cancel();
    	eject.cancel();
    	extend.cancel();
    	retract.cancel();
    }
}