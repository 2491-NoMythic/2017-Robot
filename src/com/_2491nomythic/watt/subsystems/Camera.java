package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Camera extends Subsystem {
	public static Camera instance;
	private SerialPort pixy;
	private Port port = Port.kMXP;
	private String print;
	private CameraException camExc;
	
	private Camera() {
		pixy = new SerialPort(19200, port);
		pixy.setReadBufferSize(14);
		camExc = new CameraException(print);	
		
	}
	
	public static Camera getInstance() {
    	if(instance == null) {
    		instance = new Camera();
    	}
    	return instance;
    }
	
	public void camReset() {
		pixy.reset();
	}
	//the following method turns unprocessed data from packets into integers
	//this thing is used in the larger, more important method and i don't think
	//anyone should be using this in a command. ever.
	public int datToInt(byte upper, byte lower) {
		return (((int)upper & 0xff) << 8) | ((int)lower & 0xff);
	}
	//this method reads packets, how nice.
	public void readPacket(int signature) throws CameraException {
		int check;
		int sig;
		byte[] rawData = new byte[32];
		try {//this first bit makes sure the packet is long enough to be valid
			rawData = pixy.read(32);	
		}
		catch (RuntimeException e) {
		}
		if(rawData.length < 32) {
			System.out.println("invalid packet length");
		}//the following bit makes sure a packet is valid and readable by checking the
		//first portion of the packet, which indicates that the rest is ok
		for (int i = 0; i <= 16;) {
			int startVal = datToInt(rawData[i+1], rawData[i+0]);
			if (startVal == 0xaa55) {
				startVal = datToInt(rawData[i+3], rawData[i+2]);
			}
			if (startVal != 0xaa55) {
				i -= 2;
			}
			check = datToInt(rawData[i+5], rawData[i+4]);
			sig = datToInt(rawData[i+7], rawData[i+6]);
			if (sig <= 0 || sig > CameraPacket.cameraX) {
				break;	
			}
			if (sig <= 0 || sig > CameraPacket.cameraY) {
				break;
			}
			if (sig <= 0 || sig > CameraPacket.cameraHeight) {
				break;
			}
			if (sig <= 0 || sig > CameraPacket.cameraWidth) {
				break;
			}
			
			//after verifying that a valid packet has been detected, this assigns
			//the packet's data to globally accessible variables
			CameraPacket.cameraX = datToInt(rawData[i+9], rawData[i+8]);
			CameraPacket.cameraY = datToInt(rawData[i+11], rawData[i+10]);
			CameraPacket.cameraHeight = datToInt(rawData[i+13], rawData[i+12]);
			CameraPacket.cameraWidth = datToInt(rawData[i+15], rawData[i+14]);
			if (check != sig + CameraPacket.cameraX + CameraPacket.cameraY + CameraPacket.cameraHeight + CameraPacket.cameraWidth) {
				throw camExc;
			}
			break;
		}
	}
	//because of the nature of this method, we're probably gonna wanna run it
	//constantly in a command and then continue checking the updated global variables


    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

