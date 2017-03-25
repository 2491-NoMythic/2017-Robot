package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.settings.Constants;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

import com._2491nomythic.watt.commands.lights.ClimbFlash;

/**
 * The system that controls the lights
 * @deprecated
 */
public class Lights extends Subsystem {
	private PWM red, green, blue;
	private static Solenoid activator;
	
	private static Lights instance;
	
	public static Lights getInstance() {
		if(instance == null){
			instance = new Lights();
		}
		return instance;
	}
	
	/**
	 * The system that controls the lights
	 * @deprecated
	 */
	private Lights() {
		red = new PWM(Constants.redLightChannel);
		green = new PWM(Constants.greenLightChannel);
		blue = new PWM(Constants.blueLightChannel);
		
		activator = new Solenoid(Constants.lightSolenoidChannel);
	}
	
	/**
	 * Activates the lights
	 */
	public static void activateLights() {
		activator.set(true);
	}
	
	/**
	 * Deactivates the lights
	 */
	public static void deactivateLights() {
		activator.set(false);
	}
	
	/**
	 * Changes the colors on the lights
	 * @param redColor Sets the amount of red, ranging from 0 to 255
	 * @param blueColor Sets the amount of blue, ranging from 0 to 255
	 * @param greenColor Sets the amount of green, ranging from 0 to 255
	 */
	public void setColors(int redColor, int blueColor, int greenColor) {
		red.setRaw(redColor);
		blue.setRaw(greenColor);
		green.setRaw(blueColor);
	}
	
	/**
	 * Disables red color in the lights
	 */
	public void shutOffRed(){
		red.setDisabled();
	}
	
	/**
	 * Disables blue color in the lights
	 */
	public void shutOffBlue(){
		blue.setDisabled();
	}
	
	/**
	 * Disables green color in the lights
	 */
	public void shutOffGreen(){
		green.setDisabled();
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    	//setDefaultCommand(new ClimbFlash());
    }
}

