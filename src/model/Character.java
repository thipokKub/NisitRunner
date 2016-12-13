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
	
	public void reset() {
		frameCounter = 0;
		isJump = false;
		isJumpDead = false;
		allowOffScreen = false;
		
		z=100;
		frameCounterJump = 0;
		deltaTime = 0;
	}
	
	public void setImage(ArrayList<String> frameDisplay) {
		for(int i = 0; i < frameAmount; i++) {
			try {
				this.frameDisplay.add(new Image(ClassLoader.getSystemResource(frameDisplay.get(i)).toString()));
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setUpImage(ArrayList<Image> frameDisplay) {
		this.frameDisplay = frameDisplay;
	}
	
	@Override
	public void draw(GraphicsContext gc) {
		gc.drawImage(frameDisplay.get(frameCounter), x, y);
	}
	
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

	@Override
	public boolean isDestroy() {
		return destroy;
	}
	
	public void setDestroy(boolean destroy) {
		this.destroy = destroy;
	}
	
	public void resize(int height, int width) {
		ArrayList<Image> temporary = new ArrayList<>();
		for(int i = 0; i < frameAmount; i++) {
			temporary.add(Entity.scale(frameDisplay.get(i), width, height, false));
		}
		frameDisplay = temporary;
	}
	
	public void resizeByHeight(int height) {
		double ratio = frameDisplay.get(0).getWidth()/frameDisplay.get(0).getHeight();
		ArrayList<Image> temporary = new ArrayList<>();
		for(int i = 0; i < frameAmount; i++) {
			temporary.add(Entity.scale(frameDisplay.get(i), (int)(ratio*height), height, false));
		}
		frameDisplay = temporary;
	}
	
	public void resizeByWidth(int width) {
		double ratio = frameDisplay.get(0).getHeight()/frameDisplay.get(0).getWidth();
		ArrayList<Image> temporary = new ArrayList<>();
		for(int i = 0; i < frameAmount; i++) {
			temporary.add(Entity.scale(frameDisplay.get(i), width, (int)(ratio*width), false));
		}
		frameDisplay = temporary;
	}
	
	public int getHeight() {
		return (int)frameDisplay.get(0).getHeight();
	}
	
	public int getWidth() {
		return (int)frameDisplay.get(0).getWidth();
	}

}
