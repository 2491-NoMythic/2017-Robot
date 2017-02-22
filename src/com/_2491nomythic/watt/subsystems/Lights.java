package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.settings.Constants;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Lights extends Subsystem {
	private PWM red, green, blue;
	
	private static Lights instance;
	
	public static Lights getInstance() {
		if(instance == null){
			instance = new Lights();
		}
		return instance;
	}
	private Lights() {
		red = new PWM(Constants.redLightChannel);
		green = new PWM(Constants.greenLightChannel);
		blue = new PWM(Constants.blueLightChannel);
	}
	public void setColors(int redColor,int blueColor, int greenColor){
		red.setRaw(205);
		green.setRaw(47);
		blue.setRaw(205);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}
