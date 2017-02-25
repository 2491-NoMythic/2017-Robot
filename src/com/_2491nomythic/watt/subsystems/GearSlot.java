package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.settings.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */

public class GearSlot extends Subsystem {
	private Solenoid door, pusher;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	private static GearSlot instance;
	
	public static GearSlot getInstance() {
		if (instance == null) {
			instance = new GearSlot();
		}
		return instance;
	}
	
	private GearSlot() {
		door = new Solenoid(Constants.gearDoorSolenoidChannel);
		pusher = new Solenoid(Constants.gearPusherSolenoidChannel);
	}
	
	public void openDoors() {
		door.set(true);
	}
	
	public void closeDoors() {
		door.set(false);
	}
	
	public void ejectGear() {
		pusher.set(true);
	}
	
	public void retractEjector() {
		pusher.set(false);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

