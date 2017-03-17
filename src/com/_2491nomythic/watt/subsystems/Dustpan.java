package com._2491nomythic.watt.subsystems;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Dustpan extends Subsystem {
	private DigitalInput limitSwitch;
	private CANTalon intakeMotor;
	private Solenoid turner1,turner2;
	
	private static Dustpan instance;
	
	public static Dustpan getInstance() {
		if (instance == null) {
			instance = new Dustpan();
		}
		return instance;
	}
	private Dustpan(){
		
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

