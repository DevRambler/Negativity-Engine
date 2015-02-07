package com.saois.nengine;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.saois.pulse.graphics.Render;
import com.saois.pulse.graphics.Screen;


public class Display extends Canvas implements Runnable{
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Negativity Engine Development Revision 6";
	
	private Thread thread;
	private boolean running = false;
	private Render render;
	private Screen screen;
	private BufferedImage img;
	private int[] pixels;
	private Game game;
	
	public Display() {
		screen = new Screen(WIDTH, HEIGHT);
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
	}
	
	private void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
		
		System.out.println("Core Engine Running...");
	}
	
	private void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void run() {
		int fps = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		while (running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds += passedTime / 1000000000.0;
			
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println("Rendered Frames: " + fps);
					previousTime += 1000;
					fps = 0;
				}
			}
			if (ticked) {
				render();
				fps++;
			}
			render();
			fps++;
		}
		
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.render(game);
		
		for (int i = 0; i<WIDTH * HEIGHT; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.dispose();
		bs.show();
		
	}

	private void tick() {
		
	}

	public static void main(String[] args) {
		Display game = new Display();
		JFrame frame = new JFrame();
		JLabel textLabel = new JLabel("Development Copy - (c) Saois Technologies",SwingConstants.CENTER); textLabel.setPreferredSize(new Dimension(300, 100));
		frame.add(game);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle("Negativity Engine");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		System.out.println(TITLE);
		
		game.start();
		
	}
}
