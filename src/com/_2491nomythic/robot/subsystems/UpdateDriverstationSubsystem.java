package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.commands.UpdateDriverstation;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class UpdateDriverstationSubsystem extends Subsystem {
	private static UpdateDriverstationSubsystem instance;
	
	public static UpdateDriverstationSubsystem getInstance() {
		if (instance == null) {
			instance = new UpdateDriverstationSubsystem();
		}
		return instance;
	}
	
	private UpdateDriverstationSubsystem() {
		
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new UpdateDriverstation());
    }
}

