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
	private CameraPacket[] packets;
	public CameraPacket packet;
	
	private Camera() {
		packets = new CameraPacket[7];
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
	public CameraPacket readPacket(int signature) throws CameraException {
		int checkSum;
		int sig;
		byte[] rawData = new byte[32];
		try {//this first bit makes sure the packet is long enough to be valid
			rawData = pixy.read(32);	
		}
		catch (RuntimeException e) {
		}
		if (rawData.length < 32) {
			System.out.println("Invalid packet length");
			return null;
		}
		//the following bit makes sure a packet is valid and readable by checking the
		//first portion of the packet, which indicates that the rest is ok
		for (int i = 0; i <=16;) {
			i++;
			int syncWord = datToInt(rawData[i+0], rawData[i+1]);
			if (syncWord == 0xaa55) {
				syncWord = datToInt(rawData[i+2], rawData[i+3]);
				if (syncWord != 0xaa55) {
					i-=2;
				}
			}
			checkSum = datToInt(rawData[i+4], rawData[i+5]);
			sig = datToInt(rawData[i+6], rawData[i+7]);
			if (sig <= 0 || sig > packets.length) {
				break;
			}
			//after verifying that a valid packet has been detected, this assigns
			//the packet's data to globally accessible variables
			packets[sig - 1].cameraX = datToInt(rawData[i+7], rawData[i+8]);
			packets[sig - 1].cameraY = datToInt(rawData[i+9], rawData[i+10]);
			packets[sig - 1].cameraWidth = datToInt(rawData[i+11], rawData[i+12]);
			packets[sig - 1].cameraHeight = datToInt(rawData[i+13], rawData[i+14]);
			if (checkSum != sig + packets[sig - 1].cameraX + packets[sig - 1].cameraY + packets[sig - 1].cameraWidth + packets[sig - 1].cameraHeight) {
				packets[sig - 1] = null;
				throw camExc;
			}
			break;
	}
		packet = packets[signature - 1];
		packets[signature - 1] = null;
		return packet;
}
	
	
	//because of the nature of this method, we're probably gonna wanna run it
	//constantly in a command and then continue checking the updated global variables


    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

