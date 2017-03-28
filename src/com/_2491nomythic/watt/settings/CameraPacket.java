package com._2491nomythic.watt.settings;

/**
 * Groups multiple pixy outputs into a single class
 */
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