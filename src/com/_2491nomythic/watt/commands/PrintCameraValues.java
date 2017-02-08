package com._2491nomythic.watt.commands;

import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;

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
    	try {
			camera.readPacket();
		} catch (CameraException e) {
			e.printStackTrace();
		}
    	System.out.println("X:" + CameraPacket.cameraX + " Y:" + CameraPacket.cameraY + " Width:" + CameraPacket.cameraWidth + " Height" + CameraPacket.cameraHeight);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
