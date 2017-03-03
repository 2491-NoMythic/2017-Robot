package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyro;

/**
 *
 */
public class PassiveRight extends CommandBase {
	private DriveStraightToPosition firstDrive, secondDrive;
	private RotateDrivetrainWithGyro rotate1;
	private int state;

    public PassiveRight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	firstDrive = new DriveStraightToPosition(1,6.3);
    	secondDrive = new DriveStraightToPosition(1,5.1);
    	rotate1 = new RotateDrivetrainWithGyro(0.25,-50);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		firstDrive.start();
    		state++;
    		break;
    		
    	case 1:
    		if(!firstDrive.isRunning()) {
    			rotate1.start();
    			state++;
    		}
    		break;
    	
    	case 2:
    		if(!rotate1.isRunning()) {
    			secondDrive.start();
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
        return !secondDrive.isRunning() && state == 3;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	firstDrive.cancel();
    	secondDrive.cancel();
    	rotate1.cancel();
    }
}
