package com._2491nomythic.watt.commands.vision;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class InitCameraFeed extends CommandBase {
	
	private CameraPacket[] packet = new CameraPacket[7];
    public InitCameraFeed() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(vision);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	for (int i = 1; i < 8; i++) {
			try {
				packet[i - 1] = vision.pixy.readPacket(i);
			} catch (CameraException e) {
				SmartDashboard.putString("Pixy error: " + i, "Exception");
			}
			if (packet[i - 1] == null) {
				SmartDashboard.putString("Pixy error: " + i, "Absent Data");
			} else {
				SmartDashboard.putString("Pixy error: " + i, "None");
				Variables.hasTarget = true;
			}
		}
		try {
			Variables.x = packet[0].camX;
			Variables.y = packet[0].camY;
			Variables.height = packet[0].camHeight;
			Variables.width = packet[0].camWidth;
			SmartDashboard.putNumber("X Value: ", packet[0].camX);
			SmartDashboard.putNumber("Y Value: ", packet[0].camY);
			SmartDashboard.putNumber("Height: ", packet[0].camHeight);
			SmartDashboard.putNumber("Width: ", packet[0].camWidth);
		}
		catch (NullPointerException e) {
			Variables.hasTarget = false;
			SmartDashboard.putNumber("X Value: ", -1);
			SmartDashboard.putNumber("Y Value: ", -1);
			SmartDashboard.putNumber("Height: ", -1);
			SmartDashboard.putNumber("Width: ", -1);
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
    	end();
    }
}
