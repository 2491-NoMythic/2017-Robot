package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.settings.Constants;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
	private CANTalon left, right;
	private DigitalInput limitSwitch;
	
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
		
		limitSwitch = new DigitalInput(Constants.climberLimitSwitchChannel);
	}
	
	public void runClimberMotors(double power) {
		left.set(power);
		right.set(-power);
	}
	
	public boolean getLimitSwitch() {
		return limitSwitch.get();
	}
	
	public void stop() {
		runClimberMotors(0);
	}
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

