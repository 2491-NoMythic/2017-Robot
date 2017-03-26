package com._2491nomythic.watt.settings;

public class CameraPacket {
	public int objectSignature;
	public int camX;
	public int camY;
	public int camHeight;
	public int camWidth;

	public String toString() {
		return "" +
				" S:" + objectSignature +
				" X:" + camX + 
				" Y:" + camY +
				" W:" + camWidth + 
				" H:" + camHeight;
		}
}