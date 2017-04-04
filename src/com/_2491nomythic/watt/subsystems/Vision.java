package com._2491nomythic.watt.subsystems;

import com._2491nomythic.util.CameraI2C;
import com._2491nomythic.util.GearTarget;
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
	public CameraI2C pixy;
	private Port port = Port.kOnboard;
	private String print;
	private CameraPacket[] packet = new CameraPacket[7];
	public static Vision instance;
	
	/**
	 * The system that controls the pixy and its outputs
	 */
	private Vision() {
		pixy = new CameraI2C("Pixy", new I2C(port, 0x55), packet, new CameraException(print));
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
	
	/**
	 * @return The camera packets from the pixy
	 */
	public CameraPacket[] FindGearTarget() {
		CameraPacket[] blocks = pixy.readBlocks();
		if (blocks == null)
			return null;
		SmartDashboard.putString("Gear Target 0", (blocks[0] == null) ? "null" : blocks[0].toString());
		SmartDashboard.putString("Gear Target 1", (blocks[1] == null) ? "null" : blocks[1].toString());
		return blocks;
	}
	
	/**
	 * @return The vision target for lining up a gear
	 */
	public GearTarget GearTargetFeed() {
		CameraPacket[] tacket = FindGearTarget();
		if (tacket == null || (tacket[0] == null && tacket[1] == null)) {
			return null;
		}
		return new GearTarget(tacket[0], tacket[1]);
	}

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new CameraFeed());
    }
    
}

