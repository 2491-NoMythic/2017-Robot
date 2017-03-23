package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.settings.Constants;
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
	private Solenoid turner1, turner2;

	private static Dustpan instance;

	public static Dustpan getInstance() {
		if (instance == null) {
			instance = new Dustpan();
		}
		return instance;
	}

	private Dustpan() {
		limitSwitch = new DigitalInput(Constants.dustpanLimitSwitchChannel);
		intakeMotor = new CANTalon(Constants.dustpanTalonChannel);
		turner1 = new Solenoid(Constants.dustpanSolenoid1Channel);
		turner2 = new Solenoid(Constants.dustpanSolenoid2Channel);
	}

	public void runMotor(double power) {
		intakeMotor.set(power);
	}

	public boolean getLimitSwitch() {
		return limitSwitch.get();
	}

	public void runLeverAxis() {
		if (turner1.get() == true && turner2.get() == true) {
			flipDown();
		}
		else if (turner1.get() == false && turner2.get() == false) {
			flipUp();
		}
		else {
			flipUp();
		}
	}

	public void flipUp() {
		turner1.set(true);
		turner2.set(true);
	}

	public void flipDown() {
		turner1.set(false);
		turner2.set(false);
	}

	public void stop() {
		intakeMotor.set(0);
	}

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
