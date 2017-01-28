package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.settings.Constants;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearSlot extends Subsystem {
	private Solenoid opener, ejector;

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
		opener = new Solenoid(Constants.gearOpenerSolenoidChannel);
		ejector = new Solenoid(Constants.gearEjectorSolenoidChannel);
	}
	
	public void openDoors() {
		opener.set(true);
	}
	
	public void closeDoors() {
		opener.set(false);
	}
	
	public void ejectGear() {
		ejector.set(true);
	}
	
	public void retractEjector() {
		ejector.set(false);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

