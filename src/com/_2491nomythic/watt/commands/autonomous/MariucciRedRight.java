package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.PivotFrontAUTOONLY;
import com._2491nomythic.watt.commands.drivetrain.ResetGyro;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.watt.commands.gearslot.OpenAndEjectGearSlot;
import com._2491nomythic.watt.commands.gearslot.TogglePusher;

import edu.wpi.first.wpilibj.Timer;

/**
 * Attempts to deposit a gear onto the right gear peg by approaching it diagonally, then drives down field, turns, and drives towards the dispenser
 */
public class MariucciRedRight extends CommandBase {
	private DriveStraightToPosition drivePastPeg, landPeg, impalePeg, driveIntoNeutralZone, driveTowardsDispenser;
	private RotateDrivetrainWithGyroPID aimForPeg, aimForNeutral, aimForDispenser;
	private PivotFrontAUTOONLY squareUp;
	private OpenAndEjectGearSlot eject;
	private TogglePusher extend, retract;
	private ResetGyro resetGyro;
	private Timer timer;
	private int state;
	
	// Autonomous positioning numbers
		// Left: As far to the left as possible (- inches from edge)
		// Center: Lined up with peg
		// Right: As far to the right as possible (- inches from center)

	/**
	 * Attempts to deposit a gear onto the right gear peg by approaching it diagonally, then drives down field, turns, and drives toward the dispenser
	 */
    public MariucciRedRight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drivePastPeg = new DriveStraightToPosition(0.6, 7.8);
    	landPeg = new DriveStraightToPosition(0.6, 4.7);
    	impalePeg = new DriveStraightToPosition(0.85, 0.35);
    	driveIntoNeutralZone = new DriveStraightToPosition(0.9, 8);
    	driveTowardsDispenser = new DriveStraightToPosition(0.9, 15);
    	aimForPeg = new RotateDrivetrainWithGyroPID(-80, false);
    	aimForNeutral = new RotateDrivetrainWithGyroPID(60, false);
    	aimForDispenser = new RotateDrivetrainWithGyroPID(-40, true);
    	squareUp = new PivotFrontAUTOONLY(-0.35, -0.35, 0.35, -0.35, 0.4);
    	eject = new OpenAndEjectGearSlot();
    	extend = new TogglePusher();
    	retract = new TogglePusher();
    	resetGyro = new ResetGyro();
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 2;
    	resetGyro.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 2:
    			drivePastPeg.start();
    			state++;
    		break;
    	case 3:
    		if(!drivePastPeg.isRunning()) {
    			timer.start();
    			timer.reset();
    			aimForPeg.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!aimForPeg.isRunning() || timer.get() > 1.5) {
    			timer.reset();
    			landPeg.start();
    			state++;
    		}
    		break;
    	case 5:
    		if(!landPeg.isRunning() || timer.get() > 2.4) {
    			squareUp.start();
    			state++;
    		}
    		break;
    	case 6:
    		if(!squareUp.isRunning()) {
    			timer.reset();
    			impalePeg.start();
    			state++;
    		}
    		break;
    	case 7:
    		if(!impalePeg.isRunning() || timer.get() > 1.4) {
    			eject.start();
    			state++;
    		}
    		break;
    	case 8:
    		if(!eject.isRunning()) {
    			extend.start();
    			state++;
    		}
    		break;
    	case 9:
    		if(!extend.isRunning()) {
    			retract.start();
    			state++;
    		}
    		break;
    	case 10:
    		if(!retract.isRunning()) {
    			timer.reset();
    			aimForNeutral.start();
    			state++;
    		}
    		break;
    	case 11:
    		if(!aimForNeutral.isRunning() || timer.get() > 1.1) {
    			aimForNeutral.cancel();
    			driveIntoNeutralZone.start();
    			state++;
    		}
    		break;
    	case 12:
    		if(!driveIntoNeutralZone.isRunning()) {
    			timer.reset();
    			aimForDispenser.start();
    			state++;
    		}
    		break;
    	case 13:
    		if(!aimForDispenser.isRunning() || timer.get() > 0.6) {
    			aimForDispenser.cancel();
    			driveTowardsDispenser.start();
    			state++;
    		}
    		break;
    	case 14:
    		break;
    	default:
    		System.out.println("Something went wrong in auto switchcase. State: " + state);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state == 14 && !driveTowardsDispenser.isRunning();
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
    	driveIntoNeutralZone.cancel();
    	driveTowardsDispenser.cancel();
    	aimForPeg.cancel();
    	aimForDispenser.cancel();
    	aimForNeutral.cancel();
    	squareUp.cancel();
    	eject.cancel();
    	extend.cancel();
    	retract.cancel();
    }
}
