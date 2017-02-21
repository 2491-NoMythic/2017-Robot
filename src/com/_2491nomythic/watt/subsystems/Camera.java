package com._2491nomythic.watt.subsystems;
import com._2491nomythic.watt.settings.CameraException;
import com._2491nomythic.watt.settings.CameraPacket;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
//Warning: if the pixy is plugged in through mini usb, this code WILL NOT WORK b/c the pixy is smart and detects where it should send data
public class Camera {
	SerialPort pixy;
	Port port = Port.kMXP;
	CameraPacket[] packets;
	CameraException pExc;
	String print;
	int Signature;
	public CameraPacket packet;
	private static Camera instance;
	public Camera() {
		pixy = new SerialPort(19200, port);
		pixy.setReadBufferSize(14);
		packets = new CameraPacket[7];
		pExc = new CameraException(print);
		Signature = 1;
		//ground signal(white, pin 10) rxd(red, pin 4) txd(black, pin 1)
	}
	//This method parses raw data from the pixy into readable integers
	
	public static Camera getInstance() {
		if (instance == null) {
			instance = new Camera();
		}
		return instance;
	}
	
	public int cvt(byte upper, byte lower) {
		return (((int)upper & 0xff) << 8) | ((int)lower & 0xff);
	}
	public void pixyReset(){
		pixy.reset();
	}
	//This method gathers data, then parses that data, and assigns the ints to global variables
	public CameraPacket readPacket() throws CameraException {
		int Checksum;
		int Sig;
		byte[] rawData = new byte[32];
		try{
			rawData = pixy.read(32);
		} catch (RuntimeException e){
		}
		if(rawData.length < 32){
			System.out.println("byte array length is broken");
			return null;
		}
		for (int i = 0; i <= 16; i++) {
			int syncWord = cvt(rawData[i+1], rawData[i+0]); //Parse first 2 bytes
			if (syncWord == 0xaa55) { //Check is first 2 bytes equal a "sync word", which indicates the start of a packet of valid data
				syncWord = cvt(rawData[i+3], rawData[i+2]); //Parse the next 2 bytes
				if (syncWord != 0xaa55){ //Shifts everything in the case that one syncword is sent
					i -= 2;
				}
				//This next block parses the rest of the data
				Checksum = cvt(rawData[i+5], rawData[i+4]);
				Sig = cvt(rawData[i+7], rawData[i+6]);
				if(Sig <= 0 || Sig > packets.length){
					break;
				}
				packets[Sig - 1] = new CameraPacket();
				packets[Sig - 1].pixX = cvt(rawData[i+9], rawData[i+8]);
				packets[Sig - 1].pixY = cvt(rawData[i+11], rawData[i+10]);
				packets[Sig - 1].pixWidth = cvt(rawData[i+13], rawData[i+12]);
				packets[Sig - 1].pixHeight = cvt(rawData[i+15], rawData[i+14]);
				//Checks whether the data is valid using the checksum *This if block should never be entered*
				if (Checksum != Sig + packets[Sig - 1].pixX + packets[Sig - 1].pixY + packets[Sig - 1].pixWidth + packets[Sig - 1].pixHeight) {
					packets[Sig - 1] = null;
					throw pExc;
				}
				break;
			}
		}
		//Assigns our packet to a temp packet, then deletes data so that we dont return old data
		CameraPacket pkt = packets[Signature - 1];
		packet = packets[Signature - 1];
		packets[Signature - 1] = null;
		return pkt;
	}
}	