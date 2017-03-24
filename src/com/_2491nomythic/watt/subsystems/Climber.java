package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.settings.Constants;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The cylinder on the front of the robot that it uses to climb a rope
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
	
	/**
	 * The cylinder on the front of the robot that it uses to climb a rope
	 */
	private Climber() {
		left = new CANTalon(Constants.climberTalonLeftChannel);
		right = new CANTalon(Constants.climberTalonRightChannel);
	}
	
	/**
	 * Runs the climber motors at a specified power
	 * @param power The amount of power sent to the motors, from 0 to 1
	 */
	public void runClimberMotors(double power) {
		left.set(Math.abs(power));
		right.set(Math.abs(power));
	}
	
	/**
	 * Stops all movement of the climber motors
	 */
	public void stop() {
		runClimberMotors(0);
	}
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

