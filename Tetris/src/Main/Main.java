package Main;

import javax.swing.JFrame;

public class Main {
	
	public static void main(String[] args) {
		
		
		JFrame window = new JFrame("Tetris");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		
		// Add GamePanel to the window
		GamePanel gp = new GamePanel();
		window.add(gp);
		window.pack(); // The size of the GamePanel becomes the size of the Window
		
		window.setLocationRelativeTo(null); // window sets to middle of screen
		window.setVisible(true); 
		
		gp.launchGame(); // Starts the game
		
		/* if(gp.pm.gameOver) {
			
		} */
	}
}
