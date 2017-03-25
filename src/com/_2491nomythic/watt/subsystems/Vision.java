package com._2491nomythic.watt.subsystems;

import com._2491nomythic.util.CameraI2CType;
import com._2491nomythic.watt.commands.vision.InitCameraFeed;
import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class Vision extends Subsystem {
	public CameraI2CType pixy;
	private Port port = Port.kOnboard;
	private String print;
	private CameraPacket[] packet = new CameraPacket[7];
	public static Vision instance;
	private Vision() {
		pixy = new CameraI2CType("Pixy", new I2C(port, 0x55), packet, new CameraException(print));
	}
	
	public static Vision getInstance() {
    	if(instance == null) {
    		instance = new Vision();
    	}
    	return instance;
    }
	
	public void cameraFeed() {
		for (int i = 1; i < 8; i++) {
			try {
				packet[i - 1] = pixy.readPacket(i);
			} catch (CameraException e) {
				SmartDashboard.putString("Pixy error: " + i, "Exception");
			}
			if (packet[i - 1] == null) {
				SmartDashboard.putString("Pixy error: " + i, "Absent Data");
			} else {
				SmartDashboard.putString("Pixy error: " + i, "None");
				Variables.hasTarget = true;
			}
		}
		try {
			Variables.x = packet[0].camX;
			Variables.y = packet[0].camY;
			Variables.height = packet[0].camHeight;
			Variables.width = packet[0].camWidth;
			SmartDashboard.putNumber("X Value: ", packet[0].camX);
			SmartDashboard.putNumber("Y Value: ", packet[0].camY);
			SmartDashboard.putNumber("Height: ", packet[0].camHeight);
			SmartDashboard.putNumber("Width: ", packet[0].camWidth);
		}
		catch (NullPointerException e) {
			Variables.hasTarget = false;
			SmartDashboard.putNumber("X Value: ", -1);
			SmartDashboard.putNumber("Y Value: ", -1);
			SmartDashboard.putNumber("Height: ", -1);
			SmartDashboard.putNumber("Width: ", -1);
		}
	}
	

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new InitCameraFeed());
    }
    
}

