package model;

import java.util.ArrayList;

import Utility.AudioUtility;
import Utility.ProgressHolder;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public abstract class Character extends Moveable implements IKeyPressable{
	protected int frameCounter;
	protected ArrayList<Image> frameDisplay = new ArrayList<>();
	private final int frameAmount;
	protected int frameCounterJump;
	protected boolean destroy;
	protected int HealthPoint;
	protected int gravity = 30;
	private double initialVelocity;
	private boolean isJump;
	private boolean isJumpDead;
	private int posYBefore;
	private int deltaTime;
	public boolean allowOffScreen;
	
	/*
	 * Constructor for Character object
	 * x, y is the position on the screen with reference point on top-left of the image
	 * frameDisplay is the list of String contain all the frame that character will loop through
	 * 
	 */

	public Character(int x, int y, ArrayList<String> frameDisplay) {
		super(x, y);
		frameCounter = 0;
		frameAmount = frameDisplay.size();
		isJump = false;
		isJumpDead = false;
		allowOffScreen = false;
		for(int i = 0; i < frameAmount; i++) {
			try {
				this.frameDisplay.add(new Image(ClassLoader.getSystemResource(frameDisplay.get(i)).toString()));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		z=100;
		frameCounterJump = 0;
		deltaTime = 0;
	}
	
	/*
	 * Constructor for character but without initializing frameDisplay
	 * instead were given frameAmount for how many frameDisplay's frame will be
	 * Need to initialize frameDisplay before using
	 */
	
	public Character (int x, int y, int frameAmount) {
		super(x, y);
		frameCounter = 0;
		this.frameAmount = frameAmount;
		isJump = false;
		isJumpDead = false;
		z=100;
		frameCounterJump = 0;
		deltaTime = 0;
	}
	
	//Reset all the important parameter
	
	public void reset() {
		frameCounter = 0;
		isJump = false;
		isJumpDead = false;
		allowOffScreen = false;
		
		z=100;
		frameCounterJump = 0;
		deltaTime = 0;
	}
	
	//Setter for frameDisplay with input as ArrayList of String that is the locaton of the file
	
	public void setImage(ArrayList<String> frameDisplay) {
		for(int i = 0; i < frameAmount; i++) {
			try {
				this.frameDisplay.add(new Image(ClassLoader.getSystemResource(frameDisplay.get(i)).toString()));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//Setter for frameDisplay with input as ArrayList of Image
	
	public void setUpImage(ArrayList<Image> frameDisplay) {
		this.frameDisplay = frameDisplay;
	}
	
	//Method handle with drawing frame on screen
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(frameDisplay.get(frameCounter), x, y);
	}
	
	//Method that handle the frame of character to be change and also its position
	
	public void updateFrame() {
		if(ProgressHolder.frameCounter%ProgressHolder.frameSkip==0) {
			frameCounter = (frameCounter + 1)%frameAmount;
		}
		if(!allowOffScreen) {
			if(x > (int)(ProgressHolder.windowWidth - frameDisplay.get(0).getWidth())) {
				x = (int)(ProgressHolder.windowWidth - frameDisplay.get(0).getWidth());
			}
			else if(x < 0) x = 0;
		}
		
		if(frameCounterJump >= 0 && (isJump||isJumpDead)) {
			int deltaX = (int)((initialVelocity/(double)ProgressHolder.frameSkip));
			initialVelocity -= ((double)gravity)*(1.0)/ProgressHolder.frameSkip;
			
			if((ProgressHolder.frameCounter)%ProgressHolder.frameSkip == 0) {
				frameCounterJump--;
				if(frameCounterJump < 0) frameCounterJump = 0;
			}
			translateY(-deltaX);
			if(isJumpDead) translateX(7);
			if(frameCounterJump <= 0 && ProgressHolder.frameCounter%ProgressHolder.frameSkip == deltaTime) {
				isJump = false;
				if(!isJumpDead) {
					y = posYBefore;
				}
				else {
					gravity = 30;
					translateY(-deltaX);
				}
			}
			
		}
	}
	
	//Called when character jump
	
	public void jump() {
		if(!isJump) {
			frameCounterJump = frameAmount;
			initialVelocity = gravity*frameAmount/2;
			isJump = true;
			posYBefore = y;
			deltaTime = ProgressHolder.frameCounter%ProgressHolder.frameSkip;
			AudioUtility.playJump();
		}
	}
	
	//Called when character hit the obstacle and then forced to jump
	
	public void jumpHurt() {
		if(!isJump) {
			frameCounterJump = frameAmount;
			initialVelocity = gravity*frameAmount/2;
			isJump = true;
			posYBefore = y;
			deltaTime = ProgressHolder.frameCounter%ProgressHolder.frameSkip;
			AudioUtility.playGotHit();
		}
	}
	
	//Called when character was consider dead, will rest below the screen
	
	public void jumpDead() {
		if(!isJumpDead) {
			gravity = 40;
			frameCounterJump = frameAmount;
			initialVelocity = gravity*frameAmount/2;
			isJumpDead = true;
			posYBefore = 3000;
			deltaTime = ProgressHolder.frameCounter%ProgressHolder.frameSkip;
			AudioUtility.playGameOver();
		}
	}
	
	//Setter for destroy

	@Override
	public boolean isDestroy() {
		return destroy;
	}
	
	//Getter for destroy
	
	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}
	
	//Resize all the frameDisplay to the correct height and width
	
	public void resize(int height, int width) {
		ArrayList<Image> temporary = new ArrayList<>();
		for(int i = 0; i < frameAmount; i++) {
			temporary.add(Entity.scale(frameDisplay.get(i), width, height, false));
		}
		frameDisplay = temporary;
	}
	
	//Resize all the frameDisplay to the correct height with the same aspect ratio as the original image
	
	public void resizeByHeight(int height) {
		double ratio = frameDisplay.get(0).getWidth()/frameDisplay.get(0).getHeight();
		ArrayList<Image> temporary = new ArrayList<>();
		for(int i = 0; i < frameAmount; i++) {
			temporary.add(Entity.scale(frameDisplay.get(i), (int)(ratio*height), height, false));
		}
		frameDisplay = temporary;
	}
	
	//Resize all the frameDisplay to the correct width with the same aspect ratio as the original image
	
	public void resizeByWidth(int width) {
		double ratio = frameDisplay.get(0).getHeight()/frameDisplay.get(0).getWidth();
		ArrayList<Image> temporary = new ArrayList<>();
		for(int i = 0; i < frameAmount; i++) {
			temporary.add(Entity.scale(frameDisplay.get(i), width, (int)(ratio*width), false));
		}
		frameDisplay = temporary;
	}
	
	//Get the frameDisplay height
	
	public int getHeight() {
		return (int)frameDisplay.get(0).getHeight();
	}
	
	//Get frameDisplay width
	
	public int getWidth() {
		return (int)frameDisplay.get(0).getWidth();
	}

}
