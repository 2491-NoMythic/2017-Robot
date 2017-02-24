package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.settings.Constants;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.command.Subsystem;
import com._2491nomythic.watt.commands.lights.LightsDefault;

/**
 *
 */
public class Lights extends Subsystem {
	private PWM red;//, green, blue;
	
	private static Lights instance;
	
	public static Lights getInstance() {
		if(instance == null){
			instance = new Lights();
		}
		return instance;
	}
	private Lights() {
		red = new PWM(Constants.redLightChannel);
		//green = new PWM(Constants.greenLightChannel);
		//blue = new PWM(Constants.blueLightChannel);
	}
	public void setColors(int redColor){//,int blueColor, int greenColor){
		red.setRaw(redColor);
		//blue.setRaw(greenColor);
		//green.setRaw(blueColor);
	}
	public void shutOffRed(){
		red.setDisabled();
	}
		
	

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new LightsDefault());
    }
}

