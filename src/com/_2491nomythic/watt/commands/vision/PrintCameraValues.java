package com._2491nomythic.watt.commands.vision;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PrintCameraValues extends CommandBase {
	private CameraPacket[] dacket;

    public PrintCameraValues() {
    	dacket = new CameraPacket[7];
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	setRunWhenDisabled(true);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	for (int i = 0; i < dacket.length; i++) 
			dacket[i] = null;
		SmartDashboard.putString("hello pixy ", "working");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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
		}
		try {
			SmartDashboard.putNumber("X Value: 1", dacket[0].camX);
			SmartDashboard.putNumber("Y Value: 1", dacket[0].camY);
			SmartDashboard.putNumber("Height: 1", dacket[0].camHeight);
			SmartDashboard.putNumber("Width: 1", dacket[0].camWidth);
		}
		catch (NullPointerException e) {
			SmartDashboard.putNumber("X Value: 1", -1);
			SmartDashboard.putNumber("Y Value: 1", -1);
			SmartDashboard.putNumber("Height: 1", -1);
			SmartDashboard.putNumber("Width: 1", -1);
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
