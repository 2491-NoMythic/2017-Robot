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
public class HighlyExperimentalRight extends CommandBase {
	private DriveStraightToPosition drivePastPeg, landPeg, impalePeg;
	private RotateDrivetrainWithGyro aimForPeg, straightenPeg;
	private DriveSideways squareUp;
	private OpenAndEjectGearSlot eject;
	private Timer timer;
	private int state;
	
	// Autonomous positioning numbers
		// Left: As far to the left as possible (- inches from edge)
		// Center: Lined up with peg
		// Right: As far to the right as possible (- inches from center)

    public HighlyExperimentalRight() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drivePastPeg = new DriveStraightToPosition(0.75,7.75);
    	landPeg = new DriveStraightToPosition(0.6,4.8);
    	impalePeg = new DriveStraightToPosition(0.5,0.35);
    	aimForPeg = new RotateDrivetrainWithGyro(-0.4,55);
    	straightenPeg = new RotateDrivetrainWithGyro(0.4,10);
    	squareUp = new DriveSideways(0.5,2);
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
    		drivePastPeg.start();
    		state++;
    		break;
    	case 1:
    		if(!drivePastPeg.isRunning()) {
    			aimForPeg.start();
    			state++;
    		}
    		break;
    	case 2:
    		if(!aimForPeg.isRunning()) {
    			landPeg.start();
    			state++;
    		}
    		break;
    	case 3:
    		if(!landPeg.isRunning()) {
    			straightenPeg.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!straightenPeg.isRunning()) {
    			timer.start();
    			timer.reset();
    			squareUp.start();
    			state++;
    		}
    		break;
    	case 5:
    		if(timer.get() > 0.4) {
    			squareUp.cancel();
    			impalePeg.start();
    			state++;
    		}
    		break;
    	case 6:
    		if(!impalePeg.isRunning()) {
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
    	drivePastPeg.cancel();
    	landPeg.cancel();
    	impalePeg.cancel();
    	aimForPeg.cancel();
    	straightenPeg.cancel();
    	squareUp.cancel();
    	eject.cancel();
    }
}
