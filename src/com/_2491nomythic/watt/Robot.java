	
package com._2491nomythic.watt;

import com._2491nomythic.watt.commands.drivetrain.DriveStraightToPositionGyro;
import com._2491nomythic.watt.commands.AyyyyyMacarena;
import com._2491nomythic.watt.commands.CommandBase;
import com._2491nomythic.watt.commands.autonomous.AngledCenter;
import com._2491nomythic.watt.commands.autonomous.BlueLeft;
import com._2491nomythic.watt.commands.autonomous.BlueRight;
import com._2491nomythic.watt.commands.autonomous.DoNothing;
import com._2491nomythic.watt.commands.autonomous.MariucciBlueLeft;
import com._2491nomythic.watt.commands.autonomous.MariucciBlueRight;
import com._2491nomythic.watt.commands.autonomous.MariucciRedLeft;
import com._2491nomythic.watt.commands.autonomous.MariucciRedRight;
import com._2491nomythic.watt.commands.autonomous.RedLeft;
import com._2491nomythic.watt.commands.autonomous.RedRight;
import com._2491nomythic.watt.commands.drivetrain.EnableCoastMode;
import com._2491nomythic.watt.commands.drivetrain.ResetEncoders;
import com._2491nomythic.watt.commands.drivetrain.ResetGyro;
import com._2491nomythic.watt.commands.drivetrain.RotateDrivetrainWithGyroPID;
import com._2491nomythic.watt.commands.dustpan.PushOut;
import com._2491nomythic.watt.commands.lights.ActivateLights;
import com._2491nomythic.watt.commands.vision.AngleOnPeg;
import com._2491nomythic.watt.commands.vision.CenterOnPeg;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;

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
    ActivateLights activateLights;
    SendableChooser<Command> chooser;
	Preferences pref;
	double P;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	//Base
		oi = new OI();
		CommandBase.init();
		activateLights = new ActivateLights();
        activateLights.start();
		//Auto Modes
        chooser = new SendableChooser<Command>(); 
        chooser.addDefault("Do Nothing", new DoNothing());
        chooser.addObject("Mariucci Center", new AngledCenter());
        chooser.addObject("Mariucci RedLeft", new MariucciRedLeft());
        chooser.addObject("Mariucci BlueLeft", new MariucciBlueLeft());
        chooser.addObject("Mariucci RedRight", new MariucciRedRight());
        chooser.addObject("Mariucci BlueRight", new MariucciBlueRight());
        chooser.addObject("Fast Red Left", new RedLeft());
        chooser.addObject("Fast Red Right NEVER TESTED", new RedRight());
        chooser.addObject("Fast Blue Left", new BlueLeft());
        chooser.addObject("Fast Blue Right NEVER TESTED", new BlueRight());
        
        
        
        //SmartDashboard Buttons and Data
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putData("Test program please ignore", new AyyyyyMacarena());
        SmartDashboard.putData("PID Turn", new RotateDrivetrainWithGyroPID(180, true));
        SmartDashboard.putData("Line Up To Peg", new CenterOnPeg(0.25));
        SmartDashboard.putData("Angle on Peg", new AngleOnPeg(0.25));
        SmartDashboard.putData("Reset Gyro", new ResetGyro());
        SmartDashboard.putData("Reset Encoders", new ResetEncoders());
        SmartDashboard.putData("Push Gear From Dustpan", new PushOut());
		SmartDashboard.putData("Drive Straight with Gyro", new DriveStraightToPositionGyro(0.8, 5));
        SmartDashboard.putNumber("PID P Value", 1.0);
        SmartDashboard.putNumber("Shift Up Speed", Variables.shiftUpPercentage);
        SmartDashboard.putNumber("Time To Engage Pneumatics For Shifter (in seconds)", Variables.shiftEngagePneumaticsTime);
        SmartDashboard.putNumber("Total Shift Time (in seconds)", Variables.shiftTotalTime);
        SmartDashboard.putData("Coast Mode", new EnableCoastMode());
        SmartDashboard.putBoolean("Automatic Transmission", Variables.useAutomaticTransmission);
        SmartDashboard.putBoolean("Use Linear Acceleration",Variables.useLinearAcceleration);
        SmartDashboard.putNumber("Slide Drive Acceleration Value", Variables.centerAccelerationSpeed);
        SmartDashboard.putNumber("Dustpan Eject Motor Power", Variables.gearEjectPower);
        SmartDashboard.putNumber("Dustpan Eject Motor Timing", Variables.gearEjectTiming);
        SmartDashboard.putNumber("Drivetrain PID P", Variables.drivetrainPID_P);
        SmartDashboard.putNumber("Drivetrain PID I", Variables.drivetrainPID_I);
        SmartDashboard.putNumber("Drivetrain PID D", Variables.drivetrainPID_D);
        SmartDashboard.putNumber("Climber Amp Threshold", Variables.climberAmpThreshold);
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
        
//        Lights.activateLights();
        
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
//        System.out.println(Variables.shiftUpPercentage);
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        SmartDashboard.putData("PID Turn", new RotateDrivetrainWithGyroPID(-90, false));
        
    }
    
    
}
