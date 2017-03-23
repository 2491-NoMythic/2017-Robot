package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.drivetrain.CorrectX;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyro;
import com._2491nomythic.watt.commands.gearslot.OpenAndEjectGearSlot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class VisionRight extends Command {
	private DriveStraightToPosition driveToBaseLine, approachPeg, impalePeg;
	private CorrectX lineUp;
	private RotateDrivetrainWithGyro aimForPeg;
	private OpenAndEjectGearSlot eject;
	private int state;

    public VisionRight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	
    	//inaccurate values
    	driveToBaseLine = new DriveStraightToPosition(1,6.3);
    	approachPeg = new DriveStraightToPosition(1,4.5);
    	impalePeg = new DriveStraightToPosition(0.85,2);
    	lineUp = new CorrectX(0.2);
    	aimForPeg = new RotateDrivetrainWithGyro(-0.25,50);
    	eject = new OpenAndEjectGearSlot();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:
    		driveToBaseLine.start();
    		state++;
    		break;
    	case 1:
    		if(!driveToBaseLine.isRunning()) {
    			aimForPeg.start();
    			state++;
    		}
    		break;
    	case 2:
    		if(!aimForPeg.isRunning()) {
    			approachPeg.start();
    			state++;
    		}
    		break;
    	case 3:
    		if(!approachPeg.isRunning()) {
    			lineUp.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!lineUp.isRunning()) {
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
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !eject.isRunning() && state == 6;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	driveToBaseLine.cancel();
    	aimForPeg.cancel();
    	approachPeg.cancel();
    	lineUp.cancel();
    	impalePeg.cancel();
    	eject.cancel();
    }
}
