package com._2491nomythic.robot.subsystems;

import com._2491nomythic.robot.settings.CameraException;
import com._2491nomythic.robot.settings.CameraPacket;

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
	private CameraPacket[] packets;
	private String print;
	private CameraException camExc;
	
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
	//the following method turns raw data into integers
	public int datToInt(byte upper, byte lower) {
		return (((int)upper & 0xff) << 8) | ((int)lower & 0xff);
	}
	//this method reads packets, how nice. Assigns packet data to relevant global variables.
	public CameraPacket readPacket(int signature) throws CameraException {
		int checkSum;
		int sig;
		byte[] rawData = new byte[32];
		try {
			rawData = pixy.read(32);	
		}
		catch (RuntimeException e) {
		}
		if(rawData.length < 32) {
			return null;
		}
		for (int i = 0; i <= 16;) {//the following bit makes sure a packet is valid and readable
			int syncWord = datToInt(rawData[i+1], rawData[i+0]);
			if (syncWord == 0xaa55) {
				syncWord = datToInt(rawData[i+3], rawData[i+2]);
			}
			if (syncWord != 0xaa55) {
				i -= 2;
			}
			checkSum = datToInt(rawData[i+5], rawData[i+4]);
			sig = datToInt(rawData[i+7], rawData[i+6]);
			if (sig <= 0 || sig > packets.length) {
				break;
			}
			packets[sig - 1] = new CameraPacket();
			packets[sig - 1].cameraX = datToInt(rawData[i+9], rawData[i+8]);
			packets[sig - 1].cameraY = datToInt(rawData[i+11], rawData[i+10]);
			packets[sig - 1].cameraHeight = datToInt(rawData[i+13], rawData[i+12]);
			packets[sig - 1].cameraWidth = datToInt(rawData[i+15], rawData[i+14]);
			if (checkSum != sig + packets[sig - 1].cameraX + packets[sig - 1].cameraY + packets[sig - 1].cameraHeight + packets[sig - 1].cameraWidth) {
				packets[sig - 1] = null;
				throw camExc;
			}
			break;
		}
		CameraPacket packet = packets[signature - 1];
		packets[signature - 1] = null;
		return packet;
	}
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

