package com.saois.nengine.graphics;

public class Render3D extends Render{

	public Render3D(int width, int height) {
		super(width, height);
	}
	
	public void border() {
		for (int y = 0; y < height; y++) {
			double yDepth = y - height /2.4;
			double z = 100.0 / yDepth;
			
			for (int x = 0; x < width; x++) {
				double Depth = x - width / 2;
				Depth *= z;
				int xx = (int) (Depth) & 5;
				pixels[x + y * width] = xx * 128;
			}
		}
	}

}
