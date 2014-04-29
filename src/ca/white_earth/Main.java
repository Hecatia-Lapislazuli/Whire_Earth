/**
 * 
 */
package ca.white_earth;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

/**
 * @author Catman3304
 *
 */
public class Main extends Canvas implements Runnable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Thread thread;
	boolean running = false;
	
	public int width = 1080, height = width / 16 * 9;
	
	JFrame frame;
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	/**
	 * 
	 */
	public Main() {
		frame = new JFrame();
	}
	
	public synchronized void start() {
		if(running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		int frames = 0;
		double unprocesedSeconds = 0;
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;
		boolean ticked = false;
		while(running) {
			long currentTime = System.nanoTime();
			long passedTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocesedSeconds += passedTime / 1000000000.0;
			while(unprocesedSeconds > secondsPerTick) {
				tick();
				unprocesedSeconds -= secondsPerTick;
				ticked = true;
				tickCount++;
				if(tickCount % 60 == 0) {
					System.out.println("Frames Per Second: " + frames + " | Ticks: " + tickCount);
					previousTime += 1000;
					frames = 0;
				}
			}
			if(ticked) {
				render();
				frames++;
			}
			render();
		}
	}
	
	private void tick() {
		
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		//screen.render();
		
		for(int i = 0; i < width * height; i++) {
			pixels[i] = 0xffafafaf;
					//screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		g.setColor(new Color(0, 0, 0));
		g.fillRect(0, 0, width, height);
		int scale = 1;
		g.drawImage(image, 0, 0, width*scale, height*scale, null);
		g.dispose();
		bs.show();
	}
	
	/**
	 * 
	 */
	public static void main(String[] args) {
		Main main = new Main();
		main.frame.add(main);
		main.frame.pack();
		main.frame.setSize(main.width, main.height);
		main.frame.setLocationRelativeTo(null);
		main.frame.setVisible(true);
		main.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		main.start();
	}
}
