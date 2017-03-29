package com._2491nomythic.watt.subsystems;

import com._2491nomythic.util.CameraI2CType;
import com._2491nomythic.watt.commands.vision.CameraFeed;
import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The system that controls the pixy and its outputs
 */
public class Vision extends Subsystem {
	public CameraI2CType pixy;
	private Port port = Port.kOnboard;
	private String print;
	private CameraPacket[] packet = new CameraPacket[7];
	public static Vision instance;
	
	/**
	 * The system that controls the pixy and its outputs
	 */
	private Vision() {
		pixy = new CameraI2CType("Pixy", new I2C(port, 0x55), packet, new CameraException(print));
	}
	
	public static Vision getInstance() {
    	if(instance == null) {
    		instance = new Vision();
    	}
    	return instance;
    }
	
	/**
	 * Puts values from the pixy in Variables.java and on the SmartDashboard
	 */
	public void oneTargetCameraFeed() {
			try {
				packet[0] = pixy.readPacket(1);
			} catch (CameraException e) {
				SmartDashboard.putString("Pixy error: ", "Exception");
			}
			if (packet[0] == null) {
				SmartDashboard.putString("Pixy error: ", "Absent Data");
			} else {
				SmartDashboard.putString("Pixy error: ", "None");
				Variables.hasTarget = true;
			}
		try {
			Variables.x1 = packet[0].camX;
			Variables.y1 = packet[0].camY;
			Variables.height1 = packet[0].camHeight;
			Variables.width1 = packet[0].camWidth;
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
	
	public CameraPacket[] getPegPosition() {
		CameraPacket[] blocks = pixy.readBlocks();
		if (blocks == null)
			return null;
		SmartDashboard.putString("Gear Target 0", (blocks[0] == null) ? "null" : blocks[0].toString());
		SmartDashboard.putString("Gear Target 1", (blocks[1] == null) ? "null" : blocks[1].toString());
		return blocks;
	}
	
	public CameraPacket getGearTarget(int desiredTarget) {
		CameraPacket[] packet = getPegPosition();
		if (packet == null || packet[0] == null && packet[1] == null) {
			return null;
		}
		if (packet[0] == null && packet[1] != null) {
			packet[0] = packet[1];
		}
		if (packet[1] == null && packet[0] != null) {
			packet[1] = packet[0];
		}
		return packet[desiredTarget];
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new CameraFeed());
    }
    
}

