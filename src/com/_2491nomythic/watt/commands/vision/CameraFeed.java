package com._2491nomythic.watt.commands.vision;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Updates values for vision in Variables.java and on the SmartDashboard
 */
public class CameraFeed extends CommandBase {
	
	private CameraPacket[] packet = new CameraPacket[7];
	
	/**
	 * Updates values for vision in Variables.java and on the SmartDashboard
	 */
    public CameraFeed() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(vision);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
			try {
				packet[0] = vision.pixy.readPacket(1);
			} catch (CameraException e) {
				SmartDashboard.putString("Pixy error: ", "Exception");
			}
			if (packet[0] == null) {
				SmartDashboard.putString("Pixy error: ", "Absent Data");
			} else {
				SmartDashboard.putString("Pixy error: ", "None");
				Variables.hasTarget = true;
			}
		try {
			Variables.x1 = packet[0].camX;
			Variables.y1 = packet[0].camY;
			Variables.height1 = packet[0].camHeight;
			Variables.width1 = packet[0].camWidth;
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
		SmartDashboard.putBoolean("Target: ", Variables.hasTarget);
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
