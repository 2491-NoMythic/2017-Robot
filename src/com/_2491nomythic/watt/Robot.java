
package com._2491nomythic.watt;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.PrintCameraValues;
import com._2491nomythic.watt.commands.autonomous.CenterGearSlot;
import com._2491nomythic.watt.commands.autonomous.DoNothing;
import com._2491nomythic.watt.commands.autonomous.LeftGearSlot;
import com._2491nomythic.watt.commands.autonomous.RightGearSlot;
import com._2491nomythic.watt.commands.climber.Climb;
import com._2491nomythic.watt.commands.drivetrain.DriveGyroPID;
import com._2491nomythic.watt.commands.drivetrain.DriveSpeedTime;
import com._2491nomythic.watt.commands.drivetrain.DriveToPosition;
import com._2491nomythic.watt.commands.drivetrain.ResetEncoders;
import com._2491nomythic.watt.commands.drivetrain.ResetGyro;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 * 
 * 
 */
public class Robot extends IterativeRobot {

	public static OI oi;

    Command autonomousCommand;
    SendableChooser<Command> chooser;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
		oi = new OI();
		CommandBase.init();
        chooser = new SendableChooser<Command>();
        SmartDashboard.putData("Reset Gyro", new ResetGyro());
        SmartDashboard.putData("Reset Encoders", new ResetEncoders());
        SmartDashboard.putData("Print Climber Values", new PrintCameraValues());
//        chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putNumber("Time to open GearSlot doors",Variables.timeToOpenGearSlot);
        SmartDashboard.putNumber("Time to eject Gear",Variables.timeToEjectGear);
        SmartDashboard.putData("Turn 90 Degrees", new DriveGyroPID(90));
        chooser.addObject("Do Nothing", new DoNothing());
        chooser.addObject("Drive 1 Foot",new DriveToPosition(1,1));
        chooser.addObject("Right GearSlot", new RightGearSlot());
        chooser.addObject("Left GearSlot", new LeftGearSlot());
        chooser.addObject("Center GearSlot", new CenterGearSlot());
        SmartDashboard.putBoolean("Use Linear Acceleration",Variables.useLinearAcceleration);
        SmartDashboard.putData("Drive with speed for 2 secs", new DriveSpeedTime(30, 2));
        
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    
}
