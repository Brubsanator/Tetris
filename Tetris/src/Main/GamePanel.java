package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	final int FPS = 60;
	Thread gameThread; // Thread class to run the game loop
	PlayManager pm;
	public static Sound music = new Sound();
	public static Sound se = new Sound(); // Sound effect
	
	public GamePanel() {
		
		// Panel settings
		this.setPreferredSize(new Dimension(WIDTH,HEIGHT));
		this.setBackground(Color.blue);
		this.setLayout(null);
		// Implements KeyListener
		this.addKeyListener(new KeyHandler());
		this.setFocusable(true);
		
		pm = new PlayManager();
		
		
	}
	public void launchGame() {
		gameThread = new Thread(this);
		gameThread.start(); // Calls the run method
		
		music.play(0, true);
		music.loop();
	}

	@Override
	public void run() {
		
		// Game loops update and draw
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) { // Checks to see if the game thread exists
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			lastTime = currentTime;
			
			if(delta >= 1) { 
				// Once delta has reached 1 it will reset to continue the game loop and update/repaint the page
				update(); 
				repaint(); // Calls the paintComponent method
				delta--;
			}
			
		}
		
	}
	private void update() { // Updates information 
		
		if(KeyHandler.pausePressed == false && pm.gameOver == false) { // If not paused will update the PlayManager
			pm.update(); // Calls the update method from PlayManager
		}
	}
	public void paintComponent(Graphics g) { // Draws objects on the screen
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g; // Converts graphics to graphics 2d 
		pm.draw(g2); // Calls the draw method from PlayManager
	}
}
