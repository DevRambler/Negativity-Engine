package com.saois.nengine.graphics;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.saois.pulse.Game;

public class Screen extends Render {

	private Render test;
	private Render3D render;

	public Screen(int width, int height) {
		super(width, height);
		System.out.println("Rendering Engine Running...");
		Random random = new Random();
		render = new Render3D(width, height);
		test = new Render(256, 256);
		for (int i = 0; i < 256 * 256; i++) {
			test.pixels[i] = random.nextInt();
		}
	}

	public void render(Game game) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = 0;
		}
		
		for (int i = 0; i < 20; i++) {
			int anim2 = (int) (Math.cos((System.currentTimeMillis()+i*20)%2000.0/2000*Math.PI *10)*50);
			int anim = (int) (Math.sin((System.currentTimeMillis()+i*20)%2000.0/2000*Math.PI * 10) * 50);
			draw(test, (width-256)/2+anim, (height-256)/2+anim2);
			draw(render, 0, 0);
		}
	}
}
