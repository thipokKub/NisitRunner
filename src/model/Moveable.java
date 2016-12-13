package model;

import Utility.ProgressHolder;
import javafx.scene.canvas.GraphicsContext;

public abstract class Moveable extends Entity {
	private int frameCounter;
	private int loopCount;
	private int frameCounter2;
	private int frameCounter3;
	private boolean isReset;
	
	public Moveable(int x, int y) {
		super(x, y);
		frameCounter = 0;
		frameCounter2 = 0;
		frameCounter3 = 0;
		loopCount = 0;
	}

	public void translateX(int amount) {
		x += amount;
	}
	
	public void translateY(int amount) {
		y += amount;
	}
	
	public boolean drawShakeX(int period, int amount, GraphicsContext gc) {
		//period in mills
		int frameLoop = period*ProgressHolder.frameRate/1000;
		if(frameLoop%2 == 1) {
			if(frameCounter < (int)(frameLoop/2)) {
				if(frameCounter +1 >= (int)(frameLoop/2)) {
					translateX(amount-frameCounter*(int)(2*amount/frameLoop));
				}
			}
			else {
				translateX(-1*(int)(2*amount/frameLoop));
			}
		}
		else {
			if(frameCounter < frameLoop/2) {
				translateX(2*amount/frameLoop);
			}
			else {
				translateX(-2*amount/frameLoop);
			}
		}
		frameCounter = (frameCounter + 1)%frameLoop;
		this.draw(gc);
		if(frameCounter +1 == frameLoop) loopCount += 1;
		return (frameCounter + 1 == frameLoop);
	}
	
	public boolean drawDissolve(boolean isFadeIn, int period, GraphicsContext gc) {
		int frameLoop = ProgressHolder.frameRate*period/1000;
		double opacity = isFadeIn ? 0.0 : 1.0;
		double amount = isFadeIn ? 1.0/frameLoop : -1.0/frameLoop;
		
		gc.setGlobalAlpha(opacity + frameCounter*amount);
		frameCounter = (frameCounter + 1)%frameLoop;
		this.draw(gc);
		gc.setGlobalAlpha(1.0);
		if(frameCounter + 1 == frameLoop) loopCount += 1;
		return (frameCounter + 2 == frameLoop);
	}
	
	public boolean drawBlink(int period, GraphicsContext gc) {
		//Buggy
		boolean tmp = false;
		if(period%2 == 1) {
			int periodOne = (int)(period/2);
			int periodTwo = period - periodOne;
			if(loopCount%2 == 0) drawDissolve(true, periodOne, gc);
			else tmp = drawDissolve(false, periodTwo, gc);
		}
		else {
			int periodOne = period/2;
			if(loopCount%2 == 0) drawDissolve(true, periodOne, gc);
			else tmp = drawDissolve(false, periodOne, gc);
		}
		return ((loopCount%2==1)&&tmp);
	}
	
	public boolean drawDissolveHold(boolean isFadeIn, int period, GraphicsContext gc) {
		int frameLoop = ProgressHolder.frameRate*period/1000;
		double opacity = isFadeIn ? 0.0 : 1.0;
		double amount = isFadeIn ? 1.0/frameLoop : -1.0/frameLoop;
		
		gc.setGlobalAlpha(opacity + frameCounter2*amount);
		frameCounter2++;
		this.draw(gc);
		gc.setGlobalAlpha(1.0);
		boolean tmp = frameCounter2 >= frameLoop;
		
		if(isReset) {
			frameCounter2 = 0;
			isReset = false;
		}
		return tmp;
	}
	
	public boolean drawFadeInHoldFadeOut(int fadeIn, int hold, int fadeOut, GraphicsContext gc) {
		double amountIn = (fadeIn != 0) ? 1.0/((fadeIn/1000.0)*ProgressHolder.frameRate): 0;
		double amountOut = (fadeOut != 0) ? 1.0/((fadeOut/1000.0)*ProgressHolder.frameRate): 0;
		int frameIn = (int)((fadeIn/1000.0)*ProgressHolder.frameRate);
		int frameHold = (int)((hold/1000.0)*ProgressHolder.frameRate);
		int frameOut = (int)((fadeOut/1000.0)*ProgressHolder.frameRate);
		
		if(frameCounter3 <= frameIn) {
			gc.setGlobalAlpha(frameCounter3*amountIn);
			this.draw(gc);
			gc.setGlobalAlpha(1.0);
		} else if (frameCounter3 <= frameIn + frameHold) {
			gc.setGlobalAlpha(1.0);
			this.draw(gc);
		} else if (frameCounter3 <= frameIn + frameHold + frameOut) {
			gc.setGlobalAlpha(1.0 - (frameCounter3 - frameIn - frameHold)*amountOut);
			this.draw(gc);
			gc.setGlobalAlpha(1.0);
		}
		
		frameCounter3++;
		return frameCounter3 >= fadeIn + hold + fadeOut;
	}
	
	public void resetDrawDissolveHold() {
		isReset = true;
		frameCounter2 = 0;
		frameCounter3 = 0;
	}
	
	public abstract void move();
	//Calculate Next Frame Position
	
}
