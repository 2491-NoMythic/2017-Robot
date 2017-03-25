package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.settings.Constants;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system that shoots balls
 */
public class Shooter extends Subsystem {
	private CANTalon motor;
	private static Shooter instance;
	
	
	public static Shooter getInstance() {
		if (instance == null) {
			instance = new Shooter();
		}
		return instance;
	}
	
	/**
	 * The system that shoots balls
	 */
	private Shooter() {
		motor = new CANTalon(Constants.shooterTalonChannel);
	}
	
	/**
	 * Runs the shooter motor
	 * @param motorPower The power fed to the motor, ranging from -1 to 1, where negative values run the motor backwards
	 */
	public void shoot(double motorPower) {
		motor.set(motorPower);
	}
	
	/**
	 * Stops the shooter motor
	 */
	public void stopShooter() {
		shoot(0);
	}
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

