package com._2491nomythic.watt.commands;

import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class CameraDefault extends CommandBase {
	private CameraPacket[] defaultPacket;

    public CameraDefault() {
    	defaultPacket = new CameraPacket[7];
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	for (int i = 1; i < 8; i++) {
    		try {
    			defaultPacket[i - 1] = camera.readPacket(i);
    		}
    		catch (CameraException e) {
    			SmartDashboard.putString("Pixy Error" + i, "Exception");
    		}
    		if (defaultPacket[i - 1] == null) {
    			SmartDashboard.putString("Pixy Error" + i, "Not False");
    			continue;
    		}
    		camera.values.x = defaultPacket[i - 1].x;
    		camera.values.height = defaultPacket[i - 1].height;
    		camera.values.width = defaultPacket[i - 1].width;
    		camera.values.y = defaultPacket[i - 1].y;
    	}
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
    }
}
