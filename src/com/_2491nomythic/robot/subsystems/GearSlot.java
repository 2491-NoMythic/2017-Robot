package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.settings.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearSlot extends Subsystem {
	private Solenoid left, right;

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
		left = new Solenoid(Constants.leftGearSolenoidChannel);
		right = new Solenoid(Constants.rightGearSolenoidChannel);
	}
	
	public void openDoors() {
		left.set(true);
		right.set(true);
	}
	
	public void closeDoors() {
		left.set(false);
		right.set(false);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

