package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyro;

/**
 * Drives to the left gear peg
 * @deprecated
 */
public class PassiveLeft extends CommandBase {
	private DriveStraightToPosition drivePastBaseLine, impalePeg;
	private RotateDrivetrainWithGyro aimForPeg;
	private int state;

	/**
	 * Drives to the left gear peg
	 * @deprecated
	 */
    public PassiveLeft() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drivePastBaseLine = new DriveStraightToPosition(1,6.3);
    	impalePeg = new DriveStraightToPosition(1,5.7);
    	aimForPeg = new RotateDrivetrainWithGyro(0.25,50);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		drivePastBaseLine.start();
    		state++;
    		break;
    	
    	case 1:
    		if(!drivePastBaseLine.isRunning()) {
    			aimForPeg.start();
    			state++;
    		}
    		break;
    		
    	case 2:
    		if(!aimForPeg.isRunning()) {
    			impalePeg.start();
    			state++;
    		}
    		break;
    		
    	case 3:
    		
    		break;
    		
    	default:
    		System.out.println("Error in auto switchcase. Case: " + state);
    		break;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !impalePeg.isRunning() && state == 3;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	drivePastBaseLine.cancel();
    	impalePeg.cancel();
    	aimForPeg.cancel();
    }
}
