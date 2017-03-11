package com._2491nomythic.watt.subsystems;

import com._2491nomythic.util.CameraI2CType;
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
	
	public void testVision() {
		for (int i = 0; i < packet.length; i++) 
			packet[i] = null;
		SmartDashboard.putString("hello pixy ", "working");
		for (int i = 1; i < 8; i++) {
			try {
				packet[i - 1] = pixy.readPacket(i);
			} catch (CameraException e) {
				SmartDashboard.putString("Pixy error: " + i, "exception");
			}
			if (packet[i - 1] == null) {
				SmartDashboard.putString("Pixy error: " + i, "True");
				continue;
			}
			SmartDashboard.putNumber("X Value: " + i, packet[i - 1].camX);
			SmartDashboard.putNumber("Y Value: " + i, packet[i - 1].camY);
			SmartDashboard.putNumber("Height: " + i, packet[i - 1].camHeight);
			SmartDashboard.putNumber("Width: " + i, packet[i - 1].camWidth);
			SmartDashboard.putString("Pixy error" + i, "False");
		}
	}
	
	public void cameraFeed() {
		for (int i = 1; i < 8; i++) {
			packet[i] = null;
			try {
				packet[i - 1] = pixy.readPacket(i);
			}
			catch (CameraException e) {
				SmartDashboard.putString("Pixy Error: ", "Exception");
			}
			if (packet[i - 1] == null) {
				SmartDashboard.putString("Pixy Error: ", "Bad/Absent Data");
				continue;
			}
			Variables.x = packet[i - 1].camX;
			Variables.y = packet[i - 1].camY;
			Variables.height = packet[i - 1].camHeight;
			Variables.width = packet[i - 1].camWidth;
		}
	}
	

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
}

