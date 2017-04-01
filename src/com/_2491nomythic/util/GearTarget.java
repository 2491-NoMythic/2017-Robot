package com._2491nomythic.util;

import com._2491nomythic.watt.settings.CameraPacket;
import com._2491nomythic.watt.settings.Variables;

public class GearTarget {
	CameraPacket block1, block2;
	
	public GearTarget(CameraPacket b1, CameraPacket b2) {
		block1 = b1;
		block2 = b2;
		if (block1 == null && block2 != null) {
			block1 = block2;
		}
		else if (block2 == null && block1 != null) {
			block2 = block1;
		}
		findAvgs();
	}
	
	public void findAvgs() {
		Variables.avgX = (block1.camX + block2.camX)/2;
		Variables.avgY = (block1.camY + block2.camY)/2;
		Variables.avgHeight = (block1.camHeight + block2.camHeight)/2;
		Variables.avgWidth = (block1.camWidth + block2. camWidth)/2;
		Variables.avgArea = Variables.avgHeight * Variables.avgWidth;
	}
}
