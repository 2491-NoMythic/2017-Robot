package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.commands.dustpan.RunIntake;
import com._2491nomythic.watt.settings.Constants;
import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The system that allow us to pick up a gear
 */
public class Dustpan extends Subsystem {
	private CANTalon intakeMotor;
	private Solenoid turner1;

	private static Dustpan instance;

	public static Dustpan getInstance() {
		if (instance == null) {
			instance = new Dustpan();
		}
		return instance;
	}

	/**
	 * The system that allow us to pick up a gear
	 */
	private Dustpan() {
		intakeMotor = new CANTalon(Constants.dustpanTalonChannel);
		turner1 = new Solenoid(Constants.dustpanSolenoidChannel);
	}

	/**
	 * Runs the motor to pull the gear into the dustpan
	 * @param power The power fed to the motor, ranging from -1 to 1, where negative values run the motor backwards
	 */
	public void runMotor(double power) {
		intakeMotor.set(power);
	}

	/**
	 * Toggles whether or not the dustpan is flipped up or down
	 */
	public void runLeverAxis() {
		if (turner1.get() == false) flipDown();
		else if (turner1.get() == true) flipUp();
		else flipUp();
	}

	/**
	 * Flips the dustpan up
	 */
	public void flipUp() {
		turner1.set(false);
	}

	/**
	 * Flips the dustpan down
	 */
	public void flipDown() {
		turner1.set(true);
	}

	/**
	 * Stops the intake motor
	 */
	public void stop() {
		intakeMotor.set(0);
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new RunIntake());
	}
}
