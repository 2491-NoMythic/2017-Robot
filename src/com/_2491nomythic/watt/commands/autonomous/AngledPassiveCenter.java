package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.PivotFrontAUTOONLY;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyroPID;

import edu.wpi.first.wpilibj.Timer;
/**
 * Approaches the center peg on the left, turns around 45 degrees, then lands the peg and straightens up.
 */
public class AngledPassiveCenter extends CommandBase {
	private DriveStraightToPosition driveNearPeg, landPeg, impalePeg;
	private RotateDrivetrainWithGyroPID aimForPeg;
	private PivotFrontAUTOONLY squareUp;
	private Timer timer;
	private int state;
	
	/**
	 * Approaches the center peg at around a 45 degree angle and sits there so the gear can be removed
	 */
	
    public AngledPassiveCenter() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	driveNearPeg = new DriveStraightToPosition(0.75, 4.1);
    	landPeg = new DriveStraightToPosition(0.75, 2.3);
    	impalePeg = new DriveStraightToPosition(0.75, 0.3);
    	aimForPeg = new RotateDrivetrainWithGyroPID(30, false);
    	squareUp = new PivotFrontAUTOONLY(0.35, 0.35, -0.35, 0.35, 0.6);
    	timer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch(state) {
    	case 0:	
    		driveNearPeg.start();
    		state++;
    		break;
    	case 1:
    		if(!driveNearPeg.isRunning()) {
    			timer.reset();
    			aimForPeg.start();
    			state++;
    		}
    		break;
    	case 2:
    		if(!aimForPeg.isRunning() || timer.get() > 1.5) {
    			aimForPeg.cancel();
    			landPeg.start();
    			state++;
    		}
    		break;
    	case 3:
    		if(!landPeg.isRunning()) {
    			timer.reset();
    			squareUp.start();
    			state++;
    		}
    		break;
    	case 4:
    		if(!squareUp.isRunning() || timer.get() > 2.5) {
    			timer.reset();
    			impalePeg.start();
    			state++;
    		}
    		break;
    	case 5:
    		
    		break;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (!impalePeg.isRunning() || timer.get() > 1.5) && state == 5;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	driveNearPeg.cancel();
    	landPeg.cancel();
    	impalePeg.cancel();
    	aimForPeg.cancel();
    	squareUp.cancel();
    }
}
