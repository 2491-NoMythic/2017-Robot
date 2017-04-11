package com._2491nomythic.util;

import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The class that allows a variable to be defined as a camera
 */
public class CameraI2C {
	String name;
	I2C camera;
	CameraPacket[] packets;
	CameraException exc;
	
	/**
	 * The class that allows a variable to be defined as a camera
	 */
	public CameraI2C(String id, I2C pixyI2C, CameraPacket[] pixyPacket, CameraException pixyException) {
		camera = pixyI2C;
		packets = pixyPacket;
		exc = pixyException;
		name = id;
	}
	/**
	 *  Turns two bytes of data into more useful integers
	 * @param upper
	 * 				the greater of two bytes to be parsed
	 * @param lower
	 * 				the smaller of two bytes to be parsed
	 * @return the bytes of data converted to integers
	 */

	public int datToInt(byte upper, byte lower) {
		return (((int) upper & 0xff) << 8) | ((int) lower & 0xff);
	}
	/**
	 * 
	 * @param objectSignature
	 * 							The defined signature of the object being tracked
	 * @return a parsed packet of data from a Pixy camera
	 * @throws CameraException
	 * 							a user-defined exception to catch errors produced by the camera
	 */
	
	public CameraPacket readPacket(int objectSignature) throws CameraException {
		int checkSum;
		int sig;
		byte[] rawData = new byte[32];
		try {
			camera.readOnly(rawData, 32);
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
			int syncWord = datToInt(rawData[i + 1], rawData[i + 0]);
			if (syncWord == 0xaa55) { 
				syncWord = datToInt(rawData[i + 3], rawData[i + 2]);
				if (syncWord != 0xaa55) { 
					i -= 2;
				}
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

				if (checkSum != sig + packets[sig - 1].camX + packets[sig - 1].camY + packets[sig - 1].camWidth + packets[sig - 1].camHeight) {
					packets[sig - 1] = null;
					throw exc;
				}
				break;
			} else
				SmartDashboard.putNumber("syncword: ", syncWord);
		}

		CameraPacket packet = packets[objectSignature - 1];
		packets[objectSignature - 1] = null;
		return packet;
	}
	/**
	 * a method needed to allow readWord to work 
	 * @param len
	 * 				the amount of the data or bytes being read
	 * @return organized data, prepared for readWord
	 */

	private byte[] readData(int len) {
		byte[] rawData = new byte[len];
		try {
			camera.readOnly(rawData, len);
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
	/**
	 * 
	 * @return takes the organized, packeted data from readData and makes it useful integers
	 */

	private int readWord() {
		byte[] data = readData(2);
		if (data == null) {
			return 0;
		}
		return datToInt(data[1], data[0]);
	}
	/**
	 * reads a set of data referring to the information of one object on a Pixy Camera, called a block
	 * @param checkSum the data sent in a packet used to check if the data is valid
	 * @return a parsed/prepared block of data about a pixy object
	 */

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

	private final int maxSignatures = 7;
	private final int objectSize = 14;
	private final int startWord = 0xaa55;
	private final int startWordCC = 0xaa5;
	private final int startWordX = 0x55aa;
	/**
	 * regulates the block-reading process
	 * @return whether to continue the process of reading blocks or halt due to bad/absent data
	 */

	public boolean getStart() {
		int numBytesRead = 0;
		int lastWord = 0xffff;
		while (numBytesRead < (objectSize * maxSignatures)) {
			int word = readWord();
			numBytesRead += 2;
			if (word == 0 && lastWord == 0) {
				return false;
			} else if (word == startWord && lastWord == startWord) {
				return true;
			} else if (word == startWordCC && lastWord == startWord) {
				return true;
			} else if (word == startWordX) {
				@SuppressWarnings("unused")
				byte[] data = readData(1);
				numBytesRead += 1;
			}
			lastWord = word;
		}
		return false;
	}

	private boolean skipStart = false;
	/**
	 * the method used to read multiple blocks of data from the Pixy
	 * @return parsed and prepared data for the amount of blocks specified in the Pixy's settings
	 */

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
			blocks[i] = null;
			int checkSum = readWord();
			if (checkSum == startWord) {
				skipStart = true;
				return blocks;
			} else if (checkSum == startWordCC) {
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
