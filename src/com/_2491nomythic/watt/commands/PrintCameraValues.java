package com._2491nomythic.watt.commands;

import com._2491nomythic.watt.settings.CameraException;
import edu.wpi.first.wpilibj.Timer;

/**
 *
 */
public class PrintCameraValues extends CommandBase {
	private Timer timer;

    public PrintCameraValues() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	timer.start();
    	timer.reset();
    	System.out.println("Starting...\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    	try {
			camera.readPacket();
		} catch (CameraException e) {
			e.printStackTrace();
		}
    	System.out.println("X:" + camera.packet.pixX + " Y:" + camera.packet.pixY + " Width:" + camera.packet.pixWidth + " Height" + camera.packet.pixHeight);
    	System.out.println("Ending....\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return timer.get() > 1;
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
