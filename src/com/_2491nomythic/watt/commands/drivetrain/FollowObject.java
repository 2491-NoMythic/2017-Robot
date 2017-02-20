package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.CameraException;

/**
 *
 */
public class FollowObject extends CommandBase {
	private double centerX;
	private int targetHeight, targetWidth, actualX, actualHeight, actualWidth, state;

    public FollowObject() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	centerX = 119.5;
    	targetHeight = 100;
    	targetWidth = 50;
    	actualX = camera.packet.pixX;
    	actualHeight = camera.packet.pixHeight;
    	actualWidth = camera.packet.pixWidth;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try {
			camera.readPacket(2);
		} 
    	catch (CameraException e) {
			e.printStackTrace();
		}
    	switch(state) {
    	case 0:
    		if (centerX < (actualX - 1)) {
    			drivetrain.driveLeft(.5);
    			drivetrain.driveRight(-.5);
    		}
    		else {
    			state++;
    		}
    	case 1:
    		if (centerX > (actualX + 1)) {
    			drivetrain.driveLeft(-.5);
    			drivetrain.driveRight(.5);
    		}
    		else state++;
    	case 2:
    		if (targetWidth < (actualWidth - 1) && targetHeight < (actualHeight - 1)) {
    			drivetrain.drive(.5);
    		}
    		else state++;
    	case 3:
    		if (targetWidth > (actualWidth + 1) && targetHeight > (actualHeight + 1)) {
    			drivetrain.drive(-.5);
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return centerX > (actualX - 1) && centerX < (actualX + 1) && targetWidth > (actualWidth - 1) && targetWidth < (actualWidth + 1) && targetHeight > (actualHeight - 1) && targetHeight < (actualHeight + 1);
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
