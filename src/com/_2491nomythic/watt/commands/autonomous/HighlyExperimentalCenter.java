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
public class HighlyExperimentalCenter extends CommandBase {
	private DriveStraightToPosition drivePastPeg,landPeg, impalePeg;
	private RotateDrivetrainWithGyro aimForPeg, straightenPeg;
	private OpenAndEjectGearSlot eject;
	private DriveSideways offCenterAtStart, squareUp;
	int state;
	private Timer timer;
	
	// Autonomous positioning numbers
	// Left: As far to the left as possible (- inches from edge)
	// Center: Lined up with peg
	// Right: As far to the right as possible (- inches from center)
	
	public HighlyExperimentalCenter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	drivePastPeg = new DriveStraightToPosition(0.5, 3.9);
    	landPeg = new DriveStraightToPosition(0.5, 2.1);
    	impalePeg = new DriveStraightToPosition(0.5,0.35);
    	aimForPeg = new RotateDrivetrainWithGyro(0.25,20);
    	straightenPeg = new RotateDrivetrainWithGyro(0.25,-20);
    	eject = new OpenAndEjectGearSlot();
    	offCenterAtStart = new DriveSideways(-0.25,30);
    	squareUp = new DriveSideways(0.25,1);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("Center Encoder Value: " + drivetrain.getCenterEncoderDistance());
    	
    	switch(state) {
    	case 0:
    		timer.start();
    		timer.reset();
    		offCenterAtStart.start();
    		state++;
    		break;
    	case 1:
    		if(timer.get() > 0.3) {
    			offCenterAtStart.cancel();
    			drivePastPeg.start();
    			state++;
    		}
    		break;
    	case 2:
    		if(!drivePastPeg.isRunning()) {
    			aimForPeg.start();
    			state++;
    		}
    		break;
    	case 3:
    		if(!aimForPeg.isRunning()) {
    			landPeg.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!landPeg.isRunning()) {
    			straightenPeg.start();
    			state++;
    		}
    		break;
    	case 5:
    		if(!straightenPeg.isRunning()) {
    			timer.reset();
    			squareUp.start();
    			state++;
    		}
    		break;
    	case 6:
    		if(timer.get() > 0.4) {
    			squareUp.cancel();
    			impalePeg.start();
    			state++;
    		}
    		break;
    	case 7:
    		if(!impalePeg.isRunning()) {
    			eject.start();
    			state++;
    		}
    		break;
    	case 8:
    		break;
    	default:
    		System.out.println("Error in autonomous. State: " + state);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !eject.isRunning() && state == 8;
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
    	offCenterAtStart.cancel();
    	squareUp.cancel();
    	eject.cancel();
    }
}
