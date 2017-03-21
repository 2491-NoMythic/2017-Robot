package com._2491nomythic.watt.commands;

import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UpdateCameraValues extends CommandBase {
	private CameraPacket[] dacket;

    public UpdateCameraValues() {
    	dacket = new CameraPacket[7];
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	for (int i = 0; i < dacket.length; i++) 
			dacket[i] = null;
		SmartDashboard.putString("hello pixy ", "working");
		for (int i = 1; i < 8; i++) {
			try {
				dacket[i - 1] = vision.pixy.readPacket(i);
			} catch (CameraException e) {
				SmartDashboard.putString("Pixy error: " + i, "exception");
			}
			if (dacket[i - 1] == null) {
				SmartDashboard.putString("Pixy error: " + i, "True");
				continue;
			}
			Variables.x = dacket[i-1].camX;
			Variables.y = dacket[i-1].camY;
			Variables.height = dacket[i-1].camHeight;
			Variables.width = dacket[i-1].camWidth;
			SmartDashboard.putString("Pixy error" + i, "False");
		}
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
