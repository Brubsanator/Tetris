package Main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Random;

import Mino.*;


public class PlayManager {
	
	// Main Play Area
	final int WIDTH = 360;
	final int HEIGHT = 600;
	public static int left_x, right_x, top_y, bottom_y; // dimensions of the play area
	
	// Mino
	Mino currentMino;
	final int MINO_START_X;
	final int MINO_START_Y; // starting x and y of the mino
	Mino nextMino;
	final int NEXTMINO_X;
	final int NEXTMINO_Y;
	public static ArrayList<Block> staticBlocks = new ArrayList<>(); // Inactive minos go in here
	public static ArrayList<Integer> bag = new ArrayList<Integer>(); // Used for the bag of 7 random for new minos
	
	// Others
	public static int dropInterval; // mino drops in every 60 frames
	public boolean gameOver;
	
	// Effect
	boolean effectCounterOn;
	int effectCounter;
	ArrayList<Integer> effectY = new ArrayList<>();
	
	// Score
	int level = 5;
	int lines;
	int score;
	public int highScore;
	
	public PlayManager() {
		
		// Main Play Area Frame
		left_x = (GamePanel.WIDTH/2) - (WIDTH/2); // 1280/2 - 360/2 = 460
		right_x = left_x + WIDTH;
		top_y = 50;
		bottom_y = top_y + HEIGHT;
		
		MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
		MINO_START_Y = top_y + Block.SIZE; // The starting positon of the mino is the top middle of the play area
		
		NEXTMINO_X = right_x + 175;
		NEXTMINO_Y = top_y + 500;
		
		// Set the starting Mino
		currentMino = pickMino(); // Calls the pick mino method
		currentMino.setXY(MINO_START_X, MINO_START_Y);
		nextMino = pickMino();
		nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
	}
	private Mino pickMino() {
		
		// Pick a random mino
		Mino mino = null;
		int i; // picks a random number between 0 - 6
		
		
		
		if(bag.size() <= 0) {
			for(int j = 0; j <= 6; j++) {
				bag.add(j);
			}
		}
		
		boolean bool = true;
		do{
			i = new Random().nextInt(7);
			for(int j = 0; j < bag.size(); j++) {
				
				if(i == bag.get(j)) {
					bool = false;
					bag.remove(j);
				}
			}
		}while(bool);
		
		switch(i) {
		case 0: mino = new Mino_L();break;
		case 1: mino = new Mino_J();break;
		case 2: mino = new Mino_O();break;
		case 3: mino = new Mino_I();break;
		case 4: mino = new Mino_T();break;
		case 5: mino = new Mino_Z();break;
		case 6: mino = new Mino_S();break;
		}
		
		
		/*
		switch(i) {
		case 0: mino = new Mino_L();break;
		case 1: mino = new Mino_J();break;
		case 2: mino = new Mino_O();break;
		case 3: mino = new Mino_I();break;
		case 4: mino = new Mino_T();break;
		case 5: mino = new Mino_Z();break;
		case 6: mino = new Mino_S();break;
		}
		*/
		return mino;
	}
	public void update() {
		
		checkLevel(); // Checks the level and adjusts drop rate accordingly.
		
		// Check if the currentMino is active
		if(currentMino.active == false) {
			
			// If the mino is not active, put it into the staticBlocks
			staticBlocks.add(currentMino.b[0]);
			staticBlocks.add(currentMino.b[1]);
			staticBlocks.add(currentMino.b[2]);
			staticBlocks.add(currentMino.b[3]);
			
			// check if the game is over 
			if(currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
				// this means the currentMino immediately collided a block and couldn't move at all
				// so it's xy are the same with the nextMino's
				gameOver = true;
				GamePanel.music.stop();
				GamePanel.se.play(2, false); // game over sound
			}
			
			currentMino.deactivating = false;
			GamePanel.se.play(4, false);  // Hits floor sound
			
			// replace the currentMino with the nextMino
			currentMino = nextMino;
			currentMino.setXY(MINO_START_X, MINO_START_Y);
			nextMino = pickMino();
			nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
			
			
			// When a mino becomes inactive, check if line(s) can be deleted
			checkDelete();
		}
		else {
			currentMino.update(); // Calls the update method from mino
		}
	}
	private void checkLevel() {
		
		switch(level) {
			case 0:
				dropInterval = 48;
				break;
			case 1: 
				dropInterval = 43;
				break;
			case 2:
				dropInterval = 38;
				break;
			case 3:
				dropInterval = 33;
				break;
			case 4:
				dropInterval = 28;
				break;
			case 5:
				dropInterval = 23;
				break;
			case 6:
				dropInterval = 18;
				break;
			case 7:
				dropInterval = 13;
				break;
			case 8:
				dropInterval = 8;
				break;
			case 9:
				dropInterval = 6;
				break;
			case 10:
				dropInterval = 5;
				break;
			case 11:
				dropInterval = 5;
				break;
			case 12:
				dropInterval = 5;
				break;
			case 13:
				dropInterval = 4;
				break;
			case 14:
				dropInterval = 4;
				break;
			case 15:
				dropInterval = 4;
				break;
			case 16:
				dropInterval = 3;
				break;
			case 17:
				dropInterval = 3;
				break;
			case 18:
				dropInterval = 3;
				break;
			case 19:
				dropInterval = 2;
				break;
			case 20:
				dropInterval = 2;
				break;
			case 21:
				dropInterval = 2;
				break;
			case 22:
				dropInterval = 2;
				break;
			case 23:
				dropInterval = 2;
				break;
			case 24:
				dropInterval = 2;
				break;
			case 25:
				dropInterval = 2;
				break;
			case 26:
				dropInterval = 2;
				break;
			case 27:
				dropInterval = 2;
				break;
			case 28:
				dropInterval = 2;
				break;
			default:
				dropInterval = 1; 
				
		}
	}
	
	private void checkDelete() {
		
		int x = left_x;
		int y = top_y;
		int blockCount = 0;
		int lineCount = 0;
		
		while(x < right_x && y < bottom_y) {
			
			for(int i = 0; i < staticBlocks.size(); i++) {
				if(staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
					// increase the count if there is a static block
					blockCount++;
				}
			}
			
			x += Block.SIZE;
			
			if(x == right_x) {
				
				// if the blockcount hits 12, that means the current y line is all filled with blocks
				// so we can delete them
				if(blockCount == 12) {
					
					effectCounterOn = true;
					effectY.add(y);
					
					for(int i = staticBlocks.size()-1; i > - 1; i--) {
						// remove all the blocks in the current y line
						if(staticBlocks.get(i).y == y) {
							staticBlocks.remove(i);
						}
					}
					
					lineCount++;
					lines++;
					// Drop speed
					// If the line score hits a certain number, increase the drop speed
					// 1 is the fastest
					if(lines % 10  == 0 && dropInterval > 1) {
						
						level++;
						/*
						if(dropInterval > 10) {
							dropInterval -= 10;
						}
						else {
							dropInterval -= 1;
						}
						*/
					}
					
					
					// a line has been deleted so need to slide down blocks that are above it
					for(int i = 0; i < staticBlocks.size(); i++) {
						// if a block is above the current y, move it down by the block size
						if(staticBlocks.get(i).y < y) {
							staticBlocks.get(i).y += Block.SIZE;
						}
					}
				}
				
				blockCount = 0;
				x = left_x;
				y += Block.SIZE;
			}
		}
		
		// Add score
		if(lineCount > 0 && lineCount >= 4) {
			GamePanel.se.play(5, false);
			score += 1200*(level + 1);
			//System.out.println("Tetris!");
		}
		else if(lineCount > 0 && lineCount >= 3) {
			GamePanel.se.play(1, false);
			score += 300*(level + 1);
			//System.out.println("Triple!");
			
		}
		else if(lineCount > 0 && lineCount >= 2) {
			GamePanel.se.play(1, false);
			score += 100*(level + 1);
			//System.out.println("Double!");
		}
		else if(lineCount > 0) {
			GamePanel.se.play(1, false);
			score += 40*(level + 1);
			//System.out.println("Single!");
		}

	}
	public void draw(Graphics2D g2) {
		
		// Draws the play Area
		g2.setColor(Color.black);
		g2.fillRect(left_x, top_y, WIDTH, HEIGHT);
		
		// Draws the Play Area Frame
		g2.setColor(Color.white);
		g2.setStroke(new BasicStroke(4f)); // Border 4 pixels wide
		g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8); // Makes it a rectangle sightly adjusted values to account for the stroke width
		
		// Frame for the next Tetromino
		int x = right_x + 100;
		int y = bottom_y - 200;
		
		// Next mino frame
		g2.setColor(Color.black); // Sets next mino frame colour
		g2.fillRect(x+4, y+4, 200-8, 200-8); 
		
		// Next mino area
		g2.setColor(Color.white); // Sets next mino area colour
		g2.drawRect(x, y, 200, 200);
		g2.setFont(new Font("Arial", Font.PLAIN, 30));
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // Renders the text
		g2.drawString("NEXT", x+60, y+60);
		
		// Draw Score 
		
		//Draw Score Area
		g2.setColor(Color.black); // Sets score area colour
		g2.fillRect(x+4, top_y+4, 250-8, 300-8);
		
		// Draw score frame
		g2.setColor(Color.white); // Sets score frame colour and score font colour
		g2.drawRect(x, top_y, 250, 300);
		x += 40;
		y = top_y + 90;
		g2.drawString("LEVEL: " + level, x, y); y+= 70;
		g2.drawString("LINES: " + lines, x, y); y+= 70;
		g2.drawString("SCORE: " + score, x, y);
		
		// Draw the currentMino
		if(currentMino != null) { // Checks if current mino exists
			currentMino.draw(g2); // Calls the draw method from the mino class
		}
		// Draw the nextMino
		nextMino.draw(g2);
		
		// Draw Static Blocks
		for(int i = 0; i < staticBlocks.size(); i++) {
			staticBlocks.get(i).draw(g2);
		}
		
		// Draw effect
		if(effectCounterOn) {
			effectCounter++;
			
			g2.setColor(Color.red);
			for(int i = 0; i < effectY.size(); i++) {
				g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
				
			}
			
			if(effectCounter == 10) {
				effectCounterOn = false;
				effectCounter = 0;
				effectY.clear();
			}
			
		}
		
		// Draw Pause or game over
		g2.setColor(Color.yellow);
		g2.setFont(g2.getFont().deriveFont(50f));
		if(gameOver) {
			x = left_x + 25;
			y = top_y + 320;
			g2.drawString("GAME OVER", x, y);
		}
		else if(KeyHandler.pausePressed) {
			x = left_x + 70;
			y = top_y + 320;
			g2.drawString("PAUSED", x, y);
		}
		
		// Game title
		x = 55;
		y = top_y + 320;
		g2.setColor(Color.white);
		g2.setFont(new Font("Times New Roman", Font.BOLD, 60));
		g2.drawString("TETRIS", x, y); 
		
	}
}
