package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPositionNoSlowdown;
import com._2491nomythic.watt.commands.drivetrain.PivotFrontAUTOONLY;
import com._2491nomythic.watt.commands.drivetrain.ResetGyro;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.watt.commands.gearslot.OpenAndEjectGearSlot;
import com._2491nomythic.watt.commands.gearslot.TogglePusher;

import edu.wpi.first.wpilibj.Timer;

/**
 * Attempts to deposit a gear onto the left gear peg by approaching it diagonally, turns, and drives down field
 */
public class RedLeft extends CommandBase {
	private DriveStraightToPositionNoSlowdown initialDrive1, initialDrive2;
	private DriveStraightToPosition drivePastPeg, landPeg, impalePeg, driveIntoNeutralZone;
	private RotateDrivetrainWithGyroPID aimForPeg, aimForDispenser;
	private OpenAndEjectGearSlot eject;
	private PivotFrontAUTOONLY squareUp;
	private TogglePusher extend, retract;
	private int state;
	private ResetGyro resetGyro;
	private Timer timer;
	
	// Autonomous positioning numbers
		// Left: As far to the left as possible (- inches from edge)
		// Center: Lined up with peg
		// Right: As far to the right as possible (- inches from center)

	/**
	 * Attempts to deposit a gear onto the left gear peg by approaching it diagonally, turns, and drives down field
	 */
    public RedLeft() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	initialDrive1 = new DriveStraightToPositionNoSlowdown(0.3, 0.5);
    	initialDrive2 = new DriveStraightToPositionNoSlowdown(0.6, 0.5);
    	drivePastPeg = new DriveStraightToPosition(0.8, 6.6);
    	landPeg = new DriveStraightToPosition(0.8, 4.95);
    	impalePeg = new DriveStraightToPosition(0.85, 0.35);
    	driveIntoNeutralZone = new DriveStraightToPosition(0.9, 10);
    	aimForPeg = new RotateDrivetrainWithGyroPID(80, true);
    	aimForDispenser = new RotateDrivetrainWithGyroPID(-60, false);
    	squareUp = new PivotFrontAUTOONLY(0.35, 0.35, -0.35, 0.35, 0.4);
    	eject = new OpenAndEjectGearSlot();
    	extend = new TogglePusher();
    	retract = new TogglePusher();
    	resetGyro = new ResetGyro();
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 1;
    	resetGyro.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		initialDrive1.start();
    		state++;
    		break;
    	case 1:
    		if(!initialDrive1.isRunning()) {
    			initialDrive2.start();
    			state++;
    		}
    		break;
    	case 2:
    		if(!initialDrive2.isRunning()) {
    			drivePastPeg.start();
    			state++;
    		}
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
    		if(timer.get() > 1.1 || !aimForPeg.isRunning()) {
    			aimForPeg.cancel();
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
    			aimForDispenser.start();
    			state++;
    		}
    		break;
    	case 11:
    		if(!aimForDispenser.isRunning() || timer.get() > 1.1) {
    			aimForDispenser.cancel();
    			System.out.println("Case 11 in RedLeft");
    			driveIntoNeutralZone.start();
    			state++;
    		}
    		break;
    	case 12:
    		break;
    	default:
    		System.out.println("Something went wrong in auto switchcase. State: " + state);
    	}
    	
    	System.out.println("State: " + state);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state == 12 && !driveIntoNeutralZone.isRunning();
    }

    // Called once after isFinished returns true
    protected void end() {
    	initialDrive1.cancel();
    	initialDrive2.cancel();
    	drivePastPeg.cancel();
    	landPeg.cancel();
    	impalePeg.cancel();
    	driveIntoNeutralZone.cancel();
    	aimForPeg.cancel();
    	aimForDispenser.cancel();
    	squareUp.cancel();
    	eject.cancel();
    	extend.cancel();
    	retract.cancel();
    }

    // Called when another
    // command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
