package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.commands.UpdateDriverstation;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system whose sole purpose is to run UpdateDriverstation.java as its default command
 */
public class UpdateDriverstationSubsystem extends Subsystem {
	private static UpdateDriverstationSubsystem instance;
	
	public static UpdateDriverstationSubsystem getInstance() {
		if (instance == null) {
			instance = new UpdateDriverstationSubsystem();
		}
		return instance;
	}
	
	/**
	 * The system whose sole purpose is to run UpdateDriverstation.java as its default command
	 */
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

