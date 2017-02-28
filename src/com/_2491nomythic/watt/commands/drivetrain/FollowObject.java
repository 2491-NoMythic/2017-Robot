package com._2491nomythic.watt.commands.drivetrain;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.CameraException;

/**
 *
 */
public class FollowObject extends CommandBase {
	private int targetHeight, targetWidth, actualX, actualHeight, actualWidth, state;
	private double centerX, speed;

    public FollowObject() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	centerX = 159.5;
    	targetHeight = 21;
    	targetWidth = 17;
    	actualX = camera.values.x;
    	actualHeight = camera.values.height;
    	actualWidth = camera.values.width;
    	speed = .2;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	state = 0;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	try {
			camera.readPacket(1);
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
    			drivetrain.driveLeft(speed);
    			drivetrain.driveRight(speed);
    		}
    		else state++;
    	case 2:
    		if (targetWidth < (actualWidth - 1) && targetHeight < (actualHeight - 1)) {
    			drivetrain.drive(speed);
    		}
    		else state++;
    	case 3:
    		if (targetWidth > (actualWidth + 1) && targetHeight > (actualHeight + 1)) {
    			drivetrain.drive(speed);
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return centerX == actualX && targetWidth == actualWidth && targetHeight == actualHeight;
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
