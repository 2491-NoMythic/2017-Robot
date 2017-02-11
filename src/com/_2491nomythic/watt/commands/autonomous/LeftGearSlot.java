package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyro;
import com._2491nomythic.watt.commands.gearslot.OpenAndEjectGearSlot;

/**
 *
 */
public class LeftGearSlot extends CommandBase {
	private DriveStraightToPosition firstDrive, secondDrive, thirdDrive, fourthDrive;
	private OpenAndEjectGearSlot ejectGear;
	private RotateDrivetrainWithGyro rotateDrivetrain1, rotateDrivetrain2;
	private int state;

    public LeftGearSlot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	firstDrive = new DriveStraightToPosition(1, 8.75);
    	secondDrive = new DriveStraightToPosition(1, 2.92);
    	thirdDrive = new DriveStraightToPosition(-1, -2.92);
    	fourthDrive = new DriveStraightToPosition(1, 7.5);
    	ejectGear = new OpenAndEjectGearSlot();
    	rotateDrivetrain1 = new RotateDrivetrainWithGyro(0.75, 60);
    	rotateDrivetrain2 = new RotateDrivetrainWithGyro(0.75, -60);
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
    			rotateDrivetrain1.start();
    			state++;
    		}
    		break;
    	
    	case 2:
    		if(!rotateDrivetrain1.isRunning()) {
    			secondDrive.start();
    			state++;
    		}
    		break;
    		
    	case 3:
    		if(!secondDrive.isRunning()) {
    			//ejectGear.start();
    			state++;
    		}
    		break;
    		
    	case 4:
    		//if(!ejectGear.isRunning()) {
    			thirdDrive.start();
    			state++;
    		//}
    		break;
    		
    	case 5:
    		if(!thirdDrive.isRunning()) {
    			rotateDrivetrain2.start();
    			state++;
    		}
    		break;
    		
    	case 6:
    		if(!rotateDrivetrain2.isRunning()) {
    			fourthDrive.start();
    			state++;
    		}
    		break;
    		
    	case 7:
    		
    		break;
    		
    	default:
    		System.out.println("Something went wrong in autonomous switchcase. State: " + state);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !fourthDrive.isRunning();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	firstDrive.cancel();
    	secondDrive.cancel();
    	thirdDrive.cancel();
    	fourthDrive.cancel();
    	ejectGear.cancel();
    	rotateDrivetrain1.cancel();
    	rotateDrivetrain2.cancel();
    }
}
