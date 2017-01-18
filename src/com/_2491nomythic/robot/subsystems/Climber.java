package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.settings.Constants;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
	private CANTalon left, right;
	
	private static Climber instance;
	
	public static Climber getInstance() {
		if (instance == null) {
			instance = new Climber();
		}
		return instance;
	}
	
	private Climber() {
		left = new CANTalon(Constants.climberTalonLeftChannel);
		right = new CANTalon(Constants.climberTalonRightChannel);
	}
	
	public void runClimberMotors(double power) {
		left.set(power);
		right.set(-power);
	}
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

