	
package com._2491nomythic.watt;

import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.InitCameraFeed;
import com._2491nomythic.watt.commands.PrintCameraValues;
import com._2491nomythic.watt.commands.autonomous.ActiveCenter;
import com._2491nomythic.watt.commands.autonomous.DoNothing;
import com._2491nomythic.watt.commands.autonomous.HighlyExperimentalCenter;
import com._2491nomythic.watt.commands.autonomous.HighlyExperimentalLeft;
import com._2491nomythic.watt.commands.autonomous.HighlyExperimentalRight;
import com._2491nomythic.watt.commands.autonomous.PassiveCenter;
import com._2491nomythic.watt.commands.autonomous.PassiveLeft;
import com._2491nomythic.watt.commands.autonomous.PassiveRight;
import com._2491nomythic.watt.commands.autonomous.ActiveLeft;
import com._2491nomythic.watt.commands.autonomous.ActiveRight;
//import com._2491nomythic.watt.commands.drivetrain.DriveGyroPID;
import com._2491nomythic.watt.commands.drivetrain.DriveSpeedTime;
import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPosition;
import com._2491nomythic.watt.commands.drivetrain.EnableCoastMode;
import com._2491nomythic.watt.commands.drivetrain.PivotFrontAUTOONLY;
import com._2491nomythic.watt.commands.drivetrain.ResetEncoders;
import com._2491nomythic.watt.commands.drivetrain.ResetGyro;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.watt.settings.Constants;
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
        SmartDashboard.putData("Init Camera Feed", new InitCameraFeed());
        SmartDashboard.putData("Reset Gyro", new ResetGyro());
        SmartDashboard.putData("Reset Encoders", new ResetEncoders());
        SmartDashboard.putData("Print Camera Values", new PrintCameraValues());
//        chooser.addObject("My Auto", new MyAutoCommand());
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putNumber("PID P Value", 1.0);
        SmartDashboard.putNumber("Time to open GearSlot doors",Variables.timeToOpenGearSlot);
        SmartDashboard.putNumber("Time to eject Gear",Variables.timeToEjectGear);
        SmartDashboard.putNumber("Shift Up Speed", Variables.shiftUpPercentage);
        SmartDashboard.putData("Turn 90 Degrees", new RotateDrivetrainWithGyroPID(90));
        SmartDashboard.putData("Coast Mode", new EnableCoastMode());
        chooser.addDefault("Do Nothing", new DoNothing());
        chooser.addObject("Active Right GearSlot", new ActiveRight());
        chooser.addObject("Active Left GearSlot", new ActiveLeft());
        chooser.addObject("Active Center GearSlot", new ActiveCenter());
        chooser.addObject("Passive Right GearSlot", new PassiveRight());
        chooser.addObject("Passive Center GearSlot", new PassiveCenter());
        chooser.addObject("Passive Left GearSlot", new PassiveLeft());
        chooser.addObject("Experimental Center", new HighlyExperimentalCenter());
        chooser.addObject("Experimental Left", new HighlyExperimentalLeft());
        chooser.addObject("Experimental Right", new HighlyExperimentalRight());
        chooser.addObject("Experimental Auto Pivot", new PivotFrontAUTOONLY(0.5, 0.5, 10));
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
        System.out.println(Variables.shiftUpPercentage);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
    
    
}
