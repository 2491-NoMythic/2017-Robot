package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.ResetEncoders;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyro;

/**
 *
 */
public class PassiveLeft extends CommandBase {
	private DriveStraightToPosition firstDrive, secondDrive;
	private RotateDrivetrainWithGyro rotate1;
	private ResetEncoders reset;
	private int state;

    public PassiveLeft() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	firstDrive = new DriveStraightToPosition(1,8.75);
    	secondDrive = new DriveStraightToPosition(1,2.92);
    	rotate1 = new RotateDrivetrainWithGyro(0.5,60);
    	state = 0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	reset.start();
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
        return !secondDrive.isRunning();
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
