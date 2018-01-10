package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.settings.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The cylinder on the front of the robot that it uses to climb a rope
 */
public class Climber extends Subsystem {
	private TalonSRX left, right;
	
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
		left = new TalonSRX(Constants.climberTalonLeftChannel);
		right = new TalonSRX(Constants.climberTalonRightChannel);
	}
	
	/**
	 * Runs the climber motors at a specified power
	 * @param power The amount of power sent to the motors, from 0 to 1
	 */
	public void runClimberMotors(double power) {
		left.set(ControlMode.PercentOutput, Math.abs(power));
		right.set(ControlMode.PercentOutput, Math.abs(power));
	}
	
	public double getLeftCurrent() {
		return left.getOutputCurrent();
	}
	
	public double getEncoderVelocity() {
		return left.getSelectedSensorVelocity(0);
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

