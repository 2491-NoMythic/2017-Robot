package com._2491nomythic.watt.commands;

/**
 *
 */
public class PrintCameraValues extends CommandBase {

    public PrintCameraValues() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	System.out.println("Starting...\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    	camera.testCamera();
    	System.out.println("Ending...\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
