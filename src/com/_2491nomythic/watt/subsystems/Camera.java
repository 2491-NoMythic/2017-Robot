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
	public int datToInt(byte lower, byte upper) {
		return (((int)upper & 0xff) << 8) | ((int)lower & 0xff);
	}
	//this method reads packets, how nice.
	public void readPacket() throws CameraException {
		int checkSum;
		int sig;
		byte[] rawData = new byte[32];
		try {//this first bit makes sure the packet is long enough to be valid
			rawData = pixy.read(32);	
		}
		catch (RuntimeException e) {
		}
		//the following bit makes sure a packet is valid and readable by checking the
		//first portion of the packet, which indicates that the rest is ok
		for (int i = 0; i <= 16; i++) {
			if (rawData.length < 32) {
				System.out.println("Invalid packet length");
				CameraPacket.cameraX = 0;
				CameraPacket.cameraY = 0;
				CameraPacket.cameraHeight = 0;
				CameraPacket.cameraWidth = 0;
				break;
			}
			int syncWord = datToInt(rawData[i+0], rawData[i+1]);
			if (syncWord != 0xaa55) {
				i -= 2;
			}
			checkSum = datToInt(rawData[i+2], rawData[i+3]);
			sig = datToInt(rawData[i+4], rawData[i+5]);
			if (sig <= 0 || sig > 7) {
				break;
			}
			//after verifying that a valid packet has been detected, this assigns
			//the packet's data to globally accessible variables
			CameraPacket.cameraX = datToInt(rawData[i+6], rawData[i+7]);
			CameraPacket.cameraY = datToInt(rawData[i+8], rawData[i+9]);
			CameraPacket.cameraWidth = datToInt(rawData[i+10], rawData[i+11]);
			CameraPacket.cameraHeight = datToInt(rawData[i+12], rawData[i+13]);
			if (checkSum != sig + CameraPacket.cameraX + CameraPacket.cameraY + CameraPacket.cameraHeight + CameraPacket.cameraWidth) {
				throw camExc;
			}
		}
	}
	
	//because of the nature of this method, we're probably gonna wanna run it
	//constantly in a command and then continue checking the updated global variables


    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

