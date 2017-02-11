package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;

/**
 *
 */
public class FollowObject extends CommandBase {
	private int centerX, targetHeight, targetWidth, state;

    public FollowObject(int x, int width, int height) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	centerX = x;
    	targetHeight = height;
    	targetWidth = width;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try {
			camera.readPacket();
		} 
    	catch (CameraException e) {
			e.printStackTrace();
		}
    	switch(state) {
    	case 0:
    		if (centerX < (CameraPacket.cameraX - 1) && CameraPacket.cameraX != 0) {
    			drivetrain.driveLeft(.5);
    			drivetrain.driveRight(-.5);
    		}
    		else {
    			state++;
    		}
    	case 1:
    		if (centerX > (CameraPacket.cameraX + 1) && CameraPacket.cameraX != 0) {
    			drivetrain.driveLeft(-.5);
    			drivetrain.driveRight(.5);
    		}
    		else state++;
    	case 2:
    		if (targetWidth < (CameraPacket.cameraWidth - 1) && targetHeight < (CameraPacket.cameraHeight - 1) && CameraPacket.cameraHeight != 0 && CameraPacket.cameraWidth !=0) {
    			drivetrain.drive(.5);
    		}
    		else state++;
    	case 3:
    		if (targetWidth > (CameraPacket.cameraWidth + 1) && targetHeight > (CameraPacket.cameraHeight + 1) && CameraPacket.cameraHeight != 0 && CameraPacket.cameraWidth != 0) {
    			drivetrain.drive(-.5);
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return centerX == CameraPacket.cameraX && targetWidth == CameraPacket.cameraWidth && targetHeight == CameraPacket.cameraHeight;
    }

    // Called once after isFinished returns true
    protected void end() {
    	drivetrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
