package com._2491nomythic.watt.commands;

/**
 * Interrupts all commands on the drivetrain, climber, gearslot, shooter, and lights subsystems
 */
public class KillSwitch extends CommandBase {

	/**
	 * Interrupts all commands on the drivetrain, climber, gearslot, shooter, and lights subsystems
	 */
    public KillSwitch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(drivetrain);
    	requires(climber);
    	requires(gearslot);
    	requires(shooter);
    	requires(lights);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
