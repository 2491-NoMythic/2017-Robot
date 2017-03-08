package com._2491nomythic.watt.subsystems;

import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;
import com._2491nomythic.watt.settings.Variables;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Camera {
	String name;
	I2C pixy;
	Port port = Port.kOnboard;
	CameraPacket[] packets;
	CameraException exc;
	String print;
	private static Camera instance;
	

	public Camera() {
		pixy = new I2C(port, 0x55);
		packets = new CameraPacket[7];
		exc = new CameraException(print);
		name = "Pixy";
	}
	
	public static Camera getInstance() {
    	if(instance == null) {
    		instance = new Camera();
    	}
    	return instance;
    }
	
	public void cameraFeed() {
		for (int i = 0; i < 7; i++) {
			packets[i - 1] = null;
			try {
				packets[i] = readPacket(i);
			}
			catch (CameraException e) {
				SmartDashboard.putString("Pixy Error: ", "Exception");
			}
			if (packets[i] == null) {
				SmartDashboard.putString("Pixy Error: ", "Bad/Absent Data");
				continue;
			}
			Variables.x = packets[i].camX;
			Variables.y = packets[i].camY;
			Variables.height = packets[i].camHeight;
			Variables.width = packets[i].camWidth;
		}
	}
	
	public void testCamera() {
		for (int i = 0; i < packets.length; i++) 
			packets[i - 1] = null;
		SmartDashboard.putString("hello pixy ", "working");
		for (int i = 0; i < 7; i++) {
			try {
				packets[i] = readPacket(i);
			} catch (CameraException e) {
				SmartDashboard.putString("Pixy error: " + i, "exception");
			}
			if (packets[i] == null) {
				SmartDashboard.putString("Pixy error: " + i, "True");
				continue;
			}
			SmartDashboard.putNumber("X Value: " + i, packets[i].camX);
			SmartDashboard.putNumber("Y Value: " + i, packets[i].camY);
			SmartDashboard.putNumber("Height: " + i, packets[i].camHeight);
			SmartDashboard.putNumber("Width: " + i, packets[i].camWidth);
			SmartDashboard.putString("Pixy error" + i, "False");
		}
	}

	// This method parses raw data from the pixy into readable integers
	public int datToInt(byte upper, byte lower) {
		return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
	}
	
	public CameraPacket readPacket(int objectSignature) throws CameraException {
		int checkSum;
		int sig;
		byte[] rawData = new byte[32];
		// SmartDashboard.putString("rawData", rawData[0] + " " + rawData[1] + "
		// " + rawData[15] + " " + rawData[31]);
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
			int syncWord = datToInt(rawData[i + 1], rawData[i + 0]); // Parse first 2
																// bytes
			if (syncWord == 0xaa55) { // Check is first 2 bytes equal a "sync
										// word", which indicates the start of a
										// packet of valid data
				syncWord = datToInt(rawData[i + 3], rawData[i + 2]); // Parse the
																// next 2 bytes
				if (syncWord != 0xaa55) { // Shifts everything in the case that
											// one syncword is sent
					i -= 2;
				}
				// This next block parses the rest of the data
				checkSum = datToInt(rawData[i + 5], rawData[i + 4]);
				sig = datToInt(rawData[i + 7], rawData[i + 6]);
				if (sig <= 0 || sig > packets.length) {
					break;
				}

				packets[sig - 1] = new CameraPacket();
				packets[sig - 1].camX = datToInt(rawData[i + 9], rawData[i + 8]);
				packets[sig - 1].camY = datToInt(rawData[i + 11], rawData[i + 10]);
				packets[sig - 1].camWidth = datToInt(rawData[i + 13], rawData[i + 12]);
				packets[sig - 1].camHeight = datToInt(rawData[i + 15], rawData[i + 14]);
				// Checks whether the data is valid using the checksum *This if
				// block should never be entered*
				if (checkSum != sig + packets[sig - 1].camX + packets[sig - 1].camY + packets[sig - 1].camWidth + packets[sig - 1].camHeight) {
					packets[sig - 1] = null;
					throw exc;
				}
				break;
			} else
				SmartDashboard.putNumber("syncword: ", syncWord);
		}
		// Assigns our packet to a temp packet, then deletes data so that we
		// dont return old data
		CameraPacket pkt = packets[objectSignature - 1];
		packets[objectSignature - 1] = null;
		return pkt;
	}

	private byte[] readData(int len) {
		byte[] rawData = new byte[len];
		try {
			pixy.readOnly(rawData, len);
		} catch (RuntimeException e) {
			SmartDashboard.putString(name + "Status", e.toString());
			System.out.println(name + "  " + e);
		}
		if (rawData.length < len) {
			SmartDashboard.putString(name + "Status", "raw data length " + rawData.length);
			System.out.println("byte array length is broken length=" + rawData.length);
			return null;
		}
		return rawData;
	}

	private int readWord() {
		byte[] data = readData(2);
		if (data == null) {
			return 0;
		}
		return datToInt(data[1], data[0]);
	}

	private CameraPacket readBlock(int checkSum) {

		byte[] data = readData(12);
		if (data == null) {
			return null;
		}
		CameraPacket block = new CameraPacket();
		block.objectSignature = datToInt(data[1], data[0]);
		if (block.objectSignature <= 0 || block.objectSignature > 7) {
			return null;
		}
		block.camX = datToInt(data[3], data[2]);
		block.camY = datToInt(data[5], data[4]);
		block.camWidth = datToInt(data[7], data[6]);
		block.camHeight = datToInt(data[9], data[8]);

		int sum = block.objectSignature + block.camX + block.camY + block.camWidth + block.camHeight;
		if (sum != checkSum) {
			return null;
		}
		return block;
	}

	private final int MAX_SIGNATURES = 7;
	private final int OBJECT_SIZE = 14;
	private final int START_WORD = 0xaa55;
	private final int START_WORD_CC = 0xaa5;
	private final int START_WORD_X = 0x55aa;

	public boolean getStart() {
		int numBytesRead = 0;
		int lastWord = 0xffff;
		// This while condition was originally true.. may not be a good idea if
		// something goes wrong robot will be stuck in this loop forever. So
		// let's read some number of bytes and give up so other stuff on robot
		// can have a chance to run. What number of bytes to choose? Maybe size
		// of a block * max number of signatures that can be detected? Or how
		// about size of block and max number of blocks we are looking for?
		while (numBytesRead < (OBJECT_SIZE * MAX_SIGNATURES)) {
			int word = readWord();
			numBytesRead += 2;
			if (word == 0 && lastWord == 0) {
				return false;
			} else if (word == START_WORD && lastWord == START_WORD) {
				return true;
			} else if (word == START_WORD_CC && lastWord == START_WORD) {
				return true;
			} else if (word == START_WORD_X) {
				@SuppressWarnings("unused")
				byte[] data = readData(1);
				numBytesRead += 1;
			}
			lastWord = word;
		}
		return false;
	}

	private boolean skipStart = false;

	public CameraPacket[] readBlocks() {
		// This has to match the max block setting in pixymon?
		int maxBlocks = 2;
		CameraPacket[] blocks = new CameraPacket[maxBlocks];

		if (!skipStart) {
			if (getStart() == false) {
				return null;
			}
		} else {
			skipStart = false;
		}
		for (int i = 0; i < maxBlocks; i++) {
			// Should we set to empty PixyPacket? To avoid having to check for
			// null in callers?
			blocks[i] = null;
			int checkSum = readWord();
			if (checkSum == START_WORD) {
				// we've reached the beginning of the next frame
				skipStart = true;
				return blocks;
			} else if (checkSum == START_WORD_CC) {
				// we've reached the beginning of the next frame
				skipStart = true;
				return blocks;
			} else if (checkSum == 0) {
				return blocks;
			}
			blocks[i] = readBlock(checkSum);
		}
		return blocks;
	}
}
