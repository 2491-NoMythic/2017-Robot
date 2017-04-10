package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyro;
import com._2491nomythic.watt.commands.gearslot.OpenAndEjectGearSlot;

/**
 * Drives to the left gear peg and deposits a gear
 * @deprecated
 */
public class ActiveLeft extends CommandBase {
	private DriveStraightToPosition drivePastBaseline, impalePeg, backOffOfPeg, driveToMiddleLine;
	private OpenAndEjectGearSlot ejectGear;
	private RotateDrivetrainWithGyro aimForPeg, aimForMiddleLine;
	private int state;

	/**
	 * Drives to the left gear peg and deposits a gear
	 * @deprecated
	 */
    public ActiveLeft() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drivePastBaseline = new DriveStraightToPosition(1, 6.3);
    	impalePeg = new DriveStraightToPosition(0.7, 4.7);
    	backOffOfPeg = new DriveStraightToPosition(-1, -5.8);
    	driveToMiddleLine = new DriveStraightToPosition(1,3);
    	ejectGear = new OpenAndEjectGearSlot();
    	aimForPeg = new RotateDrivetrainWithGyro(0.25, 50);
    	aimForMiddleLine = new RotateDrivetrainWithGyro(0.25, -50);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		drivePastBaseline.start();
    		System.out.println("Case 0");
    		state++;
    		break;
    	case 1:
    		if(!drivePastBaseline.isRunning()) {
    			aimForPeg.start();
    			System.out.println("Case 1");
    			state++;
    		}
    		break;
    	
    	case 2:
    		if(!aimForPeg.isRunning()) {
    			impalePeg.start();
    			state++;
    			System.out.println("Case 2");
    		}
    		break;
    		
    	case 3:
    		if(!impalePeg.isRunning()) {
    			ejectGear.start();
    			System.out.println("Case 3");
    			state++;
    		}
    		break;
    		
    	case 4:
    		if(!ejectGear.isRunning()) {
    			backOffOfPeg.start();
    			System.out.println("Case 4");
    			state++;
    		}
    		break;
    		
    	case 5:
    		if(!backOffOfPeg.isRunning()) {
    			aimForMiddleLine.start();
    			System.out.println("Case 5");
    			state++;
    		}
    		break;
    		
    	case 6:
    		if(!aimForMiddleLine.isRunning()) {
    			driveToMiddleLine.start();
    			System.out.println("Case 6");
    			state++;
    		}
    		break;
    		
    	case 7:
    		System.out.println("Case 7");
    		break;
    		
    	default:
    		System.out.println("Something went wrong in autonomous switchcase. State: " + state);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       // return !fourthDrive.isRunning() && state == 7;
    	return !impalePeg.isRunning() && state == 7;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	drivePastBaseline.cancel();
    	impalePeg.cancel();
    	backOffOfPeg.cancel();
    	driveToMiddleLine.cancel();
    	ejectGear.cancel();
    	aimForPeg.cancel();
    	aimForMiddleLine.cancel();
    }
}
