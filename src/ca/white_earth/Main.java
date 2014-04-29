/**
 * 
 */
package ca.white_earth;

import java.awt.Canvas;

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
	
	JFrame frame;
	
	public int width = 1080, height = width / 16 * 9;
	
	/**
	 * 
	 */
	public Main() {
		frame = new JFrame();
	}
	
	private synchronized void start() {
		running = true;
		thread = new Thread(this, "Main");
		thread.start();
	}
	
	public void run() {
		while(running) {
			
		}
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
