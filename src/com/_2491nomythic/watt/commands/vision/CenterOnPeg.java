package com._2491nomythic.watt.commands.vision;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Constants;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.Timer;


/**
 *
 */
public class CenterOnPeg extends CommandBase {
	private boolean isDone;
	private Timer timer;
	private int margin;
    public CenterOnPeg() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	timer = new Timer();
    	margin = Constants.errorMargin;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isDone = false;
    	timer.reset();
    	timer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (Constants.xPerfectValue >= Variables.x + margin){
    		drivetrain.driveCenter(-0.35, -0.35);
    	}
    	else if (Constants.xPerfectValue <= Variables.x - margin){
    		drivetrain.driveCenter(0.35, 0.35);
    	}
    	else {
    		drivetrain.stop();
    		isDone = true;
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
