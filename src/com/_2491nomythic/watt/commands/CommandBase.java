package com._2491nomythic.watt.commands;

import com._2491nomythic.watt.OI;
import com._2491nomythic.watt.subsystems.*;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The base for all commands. All atomic commands should subclass CommandBase. CommandBase stores creates and stores each control system. To access a subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
 */
public abstract class CommandBase extends Command {
	protected static OI oi;
	protected static Drivetrain drivetrain;
	protected static Climber climber;
	protected static GearSlot gearslot;
	protected static UpdateDriverstationSubsystem updateDriverstationSubsystem;
	protected static Lights lights;
	protected static Vision vision;
	protected static Dustpan dustpan;
	
	public static void init() {
		drivetrain = Drivetrain.getInstance();
		climber = Climber.getInstance();
		gearslot = GearSlot.getInstance();
		updateDriverstationSubsystem = UpdateDriverstationSubsystem.getInstance();
		lights = Lights.getInstance();
		vision = Vision.getInstance();
		dustpan = Dustpan.getInstance();
		// This MUST be here. If the OI creates Commands (which it very likely
		// will), constructing it during the construction of CommandBase (from
		// which commands extend), subsystems are not guaranteed to be
		// yet. Thus, their requires() statements may grab null pointers. Bad
		// news. Don't move it.
		oi = new OI();
		oi.init();
		
		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(drivetrain);
		SmartDashboard.putData(climber);
		SmartDashboard.putData(gearslot);
		SmartDashboard.putData(vision);
	}
	
	/**
	 * The base for all commands. All atomic commands should subclass CommandBase. CommandBase stores creates and stores each control system. To access a subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
	 * 
	 * @param name
	 *            The name that shows up on the SmartDashboard in association with any command created using this parameter.
	 */
	public CommandBase(String name) {
		super(name);
	}
	
	/**
	 * The base for all commands. All atomic commands should subclass CommandBase. CommandBase stores creates and stores each control system. To access a subsystem elsewhere in your code in your code use CommandBase.exampleSubsystem
	 */
	public CommandBase() {
		super();
	}
}
