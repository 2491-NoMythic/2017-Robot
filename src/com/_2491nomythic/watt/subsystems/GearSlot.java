package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.settings.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system that takes gears from the feeder station and pushes them onto a peg
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
	
	/**
	 * The system that takes gears from the feeder station and pushes them onto a peg
	 */
	private GearSlot() {
		door = new Solenoid(Constants.gearDoorSolenoidChannel);
		pusher = new Solenoid(Constants.gearPusherSolenoidChannel);
	}
	
	/**
	 * Opens the doors
	 */
	public void openDoors() {
		door.set(true);
	}
	
	/**
	 * Closes the doors
	 */
	public void closeDoors() {
		door.set(false);
	}
	
	/**
	 * Extends the push bar
	 */
	public void ejectGear() {
		pusher.set(true);
	}
	
	/**
	 * Retracts the push bar
	 */
	public void retractEjector() {
		pusher.set(false);
	}
	
	/**
	 * @return Whether the doors are open
	 */
	public boolean getDoors () {
		return door.get();
	}
	
	/**
	 * @return Whether the push bar is extended
	 */
	public boolean getPusher() {
		return pusher.get();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

