package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.Variables;

/**
 *
 */
public class SafeMode extends CommandBase {
	// The SafeMode button is bound to the B button on the blue controller by the way.
	// However, it is under the "Driver" heading in ControllerMap.
	// But, if it is supposed to be for the driver, that is the NoTurnLock button.
	// I also think that SafeMode should really be in the Drivetrain initDefaultCommand.
	
	// Example: 	if(Variables.inSafeMode) {
	//					setDefaultCommand(new SafeMode());
	//				}
	//				else {
	//					setDefaultCommand(new Drive());
	//				}
	
	// Maybe Variables.inSafeMode is determined from the SmartDashboard.
	// But I am pretty sure the button is the problem.

    public SafeMode() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Variables.useAutomaticTransmission = false;
    	Variables.driveRestriction = 0.5;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Variables.useAutomaticTransmission = true;
    	Variables.driveRestriction = 1;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
