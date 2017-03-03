package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.commands.InitCameraFeed;
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
public class Camera extends Subsystem {
	private CameraPacket block1, block2;
	private CameraPacket[] packets;
	private I2C pixy;
	private Port port = Port.kOnboard;
	private CameraException exc;
	private String print;
	private String name;
	private static Camera instance;
	private int bSyncWord = 0xaa55;
	private int bSyncWord2 = 0xaa5;
	
	private Camera() {
		pixy = new I2C(port, 0x55);
		packets = new CameraPacket[7];
		exc = new CameraException(print);
		name = "Pixy";
	}
	
	public static Camera getInstance() {
		if (instance == null) {
			instance = new Camera();
		}
		return instance;
	}
	
	public void cameraTest() {
		for (int i = 0; i < packets.length; i++) 
			packets[i] = null;
		SmartDashboard.putString("Hello, Pixy! ", "Working!");
		for (int i = 1; i < 8; i++) {
			try {
				packets[i-1] = decodePacket(i);
			} catch (CameraException e) {
				SmartDashboard.putString("Error: " + i, "Exception");
			}
			if (packets[i-1] == null) {
				SmartDashboard.putString("Error: " + i, "Bad/Absent Data");
				continue;
			}
			SmartDashboard.putNumber("X Value: " + i, packets[i-1].camX);
			SmartDashboard.putNumber("Y Value: " + i, packets[i-1].camY);
			SmartDashboard.putNumber("Height: " + i, packets[i-1].camHeight);
			SmartDashboard.putNumber("Width: " + i, packets[i-1].camWidth);
			SmartDashboard.putString("Pixy error" + i, "False");
		}
	}
	
	public int rawToInt(byte high, byte low) {
		return (((int) high & 0xff) >> 8) | ((int) low & 0xff);
	}
	
	public CameraPacket decodePacket(int signature) throws CameraException {
		int checkSum;
		int sig;
		byte rawData[] = new byte[32];
		
		try {
			pixy.readOnly(rawData, 32);
		} catch (RuntimeException e) {
			SmartDashboard.putString(name + "Status", e.toString());
			System.out.println(name + "  " + e);
		}
		if (rawData.length < 32) {
			SmartDashboard.putString(name + "Status", "raw data length " + rawData.length);
			System.out.println("byte array length is broken length=" + rawData.length);
			return null;
		}
		for (int i = 0; i <= 16; i++) {
			int syncWord = rawToInt(rawData[i + 1], rawData[i + 0]);
			if (syncWord == 0xaa55) {
				syncWord = rawToInt(rawData[i + 3], rawData[i + 2]);
				if (syncWord != 0xaa55) {
					i -= 2;
				}
				checkSum = rawToInt(rawData[i + 5], rawData[i + 4]);
				sig = rawToInt(rawData[i + 7], rawData[i + 6]);
				if (sig <= 0 || sig > packets.length) {
					break;
				}

				packets[sig - 1] = new CameraPacket();
				packets[sig - 1].camX = rawToInt(rawData[i + 9], rawData[i + 8]);
				packets[sig - 1].camY = rawToInt(rawData[i + 11], rawData[i + 10]);
				packets[sig - 1].camWidth = rawToInt(rawData[i + 13], rawData[i + 12]);
				packets[sig - 1].camHeight = rawToInt(rawData[i + 15], rawData[i + 14]);
				// Checks whether the data is valid using the checksum *This if
				// block should never be entered*
				if (checkSum != sig + packets[sig - 1].camX + packets[sig - 1].camY + packets[sig - 1].camHeight + packets[sig - 1].camWidth) {
					packets[sig - 1] = null;
					throw exc;
				}
				break;
			} else
				SmartDashboard.putNumber("syncword: ", syncWord);
		}
		CameraPacket endPacket = packets[signature - 1];
		packets[signature - 1] = null;
		return endPacket;
	}
	
	public void cameraFeed() {
		for (int i = 0; i < 8; i++) {
			try {
				packets[i - 1] = decodePacket(i);
			}
			catch (CameraException e) {
				SmartDashboard.putString("Error: ", "Exception");
			}
			if (packets[i - 1] == null) {
				SmartDashboard.putString("Error: ", "Bad/Absent Data");
				continue;
			}
			Variables.x = packets[i - 1].camX;
			Variables.y = packets[i - 1].camY;
			Variables.height = packets[i - 1].camHeight;
			Variables.width = packets[i - 1].camWidth;
		}
	}
	
	private byte[] readData(int length) {
		byte[] data = new byte[length];
		try {
			pixy.readOnly(data, length);
		}
		catch (RuntimeException e) {
			SmartDashboard.putString(name + "Status: ", e.toString());
		}
		if (data.length < length) {
			SmartDashboard.putString("RIP ","Raw Data Length: " + data.length);
			return null;
		}
		return data;
	}
	
	private int readWord() {
		byte[] data = readData(2);
		if (data == null) {
			return 0;
		}
		return rawToInt(data[1], data[0]);
	}
	
	private CameraPacket readBlock(int checkSum) { //an individual one...
		byte[] data = readData(12);
		if (data == null) {
			return null;
		}
		CameraPacket block = new CameraPacket();
		block.objectSignature = rawToInt(data[1], data[0]);
		if (block.objectSignature <= 0 || block.objectSignature > 7) {
			return null;
		}
		block.camX = rawToInt(data[3], data[2]);
		block.camY = rawToInt(data[5], data[4]);
		block.camWidth = rawToInt(data[7], data[6]);
		block.camHeight = rawToInt(data[9], data[8]);

		int sum = block.objectSignature + block.camX + block.camY + block.camWidth + block.camHeight;
		if (sum != checkSum) {
			return null;
		}
		return block;
	}
	
	public CameraPacket[] readBlocks() {
		int maxBlocks = 2;
		CameraPacket[] blocks = new CameraPacket[maxBlocks];

		for (int i = 0; i < maxBlocks; i++) {
			blocks[i] = null;
			int checkSum = readWord();
			if (checkSum == bSyncWord) {
				return blocks;
			} else if (checkSum == bSyncWord2) {
				return blocks;
			} else if (checkSum == 0) {
				return blocks;
			}
			blocks[i] = readBlock(checkSum);
		}
		return blocks;
	}
	
	public void findAverages() {
		Variables.avgHeight = (block1.camHeight + block2.camHeight)/2;
		Variables.avgWidth = (block1.camWidth + block2.camWidth)/2;
		Variables.avgX = (block1.camX + block2.camX)/2;
	}

    public void initDefaultCommand() {
    	setDefaultCommand(new InitCameraFeed());
    }
}

