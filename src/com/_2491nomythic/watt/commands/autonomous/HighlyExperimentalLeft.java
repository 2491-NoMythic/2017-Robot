package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveSideways;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyro;
import com._2491nomythic.watt.commands.gearslot.OpenAndEjectGearSlot;

import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class HighlyExperimentalLeft extends CommandBase {
	private DriveStraightToPosition firstDrive, secondDrive, thirdDrive;
	private RotateDrivetrainWithGyro rotate1, rotate2;
	private DriveSideways shimmy;
	private OpenAndEjectGearSlot eject;
	private Timer timer;
	private int state;
	
	// Autonomous positioning numbers
		// Left: As far to the left as possible (- inches from edge)
		// Center: Lined up with peg
		// Right: As far to the right as possible (- inches from center)

    public HighlyExperimentalLeft() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	firstDrive = new DriveStraightToPosition(0.75,7.75);
    	secondDrive = new DriveStraightToPosition(0.6,4.8);
    	thirdDrive = new DriveStraightToPosition(0.5,0.35);
    	rotate1 = new RotateDrivetrainWithGyro(0.4,55);
    	rotate2 = new RotateDrivetrainWithGyro(-0.4,10);
    	shimmy = new DriveSideways(0.5,2);
    	eject = new OpenAndEjectGearSlot();
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
    		if(!secondDrive.isRunning()) {
    			rotate2.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!rotate2.isRunning()) {
    			timer.start();
    			timer.reset();
    			shimmy.start();
    			state++;
    		}
    		break;
    	case 5:
    		if(timer.get() > 0.4) {
    			shimmy.cancel();
    			thirdDrive.start();
    			state++;
    		}
    		break;
    	case 6:
    		if(!thirdDrive.isRunning()) {
    			eject.start();
    			state++;
    		}
    		break;
    	case 7:
    		break;
    	default:
    		System.out.println("Something went wrong in auto switchcase. State: " + state);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state == 7 && !eject.isRunning();
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
    	rotate1.cancel();
    	rotate2.cancel();
    	shimmy.cancel();
    	eject.cancel();
    }
}
