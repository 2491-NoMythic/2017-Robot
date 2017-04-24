package com._2491nomythic.watt.commands.autonomous;

import com._2491nomythic.watt.commands.drivetrain.DriveSideways;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Drives to the center gear peg and slowly moves side to side to assist in gear extraction
 * @deprecated
 */
public class PassiveCenterStrafe extends Command {
	private DriveStraightToPosition driveToHook;
	private DriveSideways strafe1, strafe2;
	private int state;

	/**
	 * Drives to the center gear peg and slowly moves side to side to assist in gear extraction
	 * @deprecated
	 */
    public PassiveCenterStrafe() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	driveToHook = new DriveStraightToPosition(0.5, 6.5);
    	strafe1 = new DriveSideways(0.1, 0.2);
    	strafe2 = new DriveSideways(-0.1, 0.2);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    	driveToHook.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	switch (state) {
    	case 0:
    		if (!driveToHook.isRunning()) {
    			strafe1.start();
    			state = 1;
    		}
    		break;
    	case 1:
    		if (!strafe1.isRunning()) {
    			strafe2.start();
    			state = 2;
    		}
    		break;
    	case 2:
    		break;
    	default:
    		System.out.println("Uh oh, something went wrong in PassiveCenterStrafe.java. State = " + state);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state == 2 && !strafe2.isRunning();
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	driveToHook.cancel();
    	strafe1.cancel();
    	strafe2.cancel();
    }
}
