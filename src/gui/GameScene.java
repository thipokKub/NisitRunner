package gui;

import java.util.ArrayList;

import Utility.AudioUtility;
import Utility.ProgressHolder;
import Utility.RenderableHolder;
import instance.BackButton;
import instance.CompEngEss;
import instance.Discrete;
import instance.MainCharacterMale;
import instance.PleaseBeKind;
import instance.ProgMeth;
import instance.SettingButton;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import logic.GameManager;
import model.Bar;
import model.ButtonImage;
import model.Entity;
import model.GameInput;
import model.GameText;
import model.IRenderable;
import model.MovingBackground;
import model.Obstacle;
import model.ResourceException;
import model.DrawImage;

public class GameScene extends GameInput{
	
	public static GameScene me;
	public static boolean drawFadeOut;
	private static boolean drawFadeOutDone;
	private static boolean isGameOver;
	private static final int totalTimeCountDown = (int)(89.5*1000);
	private static double timeCountDown;
	
	private static MovingBackground blueSky, buildings, cloud, road, spotlight;
	public static MovingBackground spotlightForeground;
	private static Bar timeBar, healthBar, enemyBar;
	private static PleaseBeKind badGuy;
	private static CompEngEss monsterOne;
	private static Discrete monsterTwo;
	private static ProgMeth monsterThree;
	private static GameText score;
	private static DrawImage gameOver = null, start = null;
	public static ArrayList<Obstacle> onScreenObstacle = new ArrayList<>();
	
	public GameScene() {
		me = this;
		drawFadeOut = false;
		drawFadeOutDone = false;
		isGameOver = false;
		timeCountDown = totalTimeCountDown;
		
		ProgressHolder.scoreCompEngEss = 0;
		ProgressHolder.scoreDiscrete = 0;
		ProgressHolder.scoreProgMeth = 0;
		
		ProgressHolder.frameCounter = 0;
		
		System.out.println("New Setting Scene");
		
		this.setUpCanvas();
		this.addEventListener();
		RenderableHolder.instance.sort();
		this.setUpThreadAndAnimation();
		
	}
	
	@Override
	public void setUpCanvas() {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			if(blueSky == null) {
				try {
					blueSky = new MovingBackground(0, 0, -1, "resource/background/blue_bg.png", -1, false);
					blueSky.fitHeight(GameManager.gc);
					blueSky.setZ(1);
				} catch (ResourceException e) {
					e.printStackTrace();
				}

				try {
					spotlight = new MovingBackground(0, 0, -1, "resource/background/spotlight.png", 1, false);
					spotlight.fitHeight(GameManager.gc);
					spotlight.setZ(1500);
				} catch (ResourceException e) {
					e.printStackTrace();
				}
				
				//
				
				try {
					buildings = new MovingBackground(0, 0, -3, "resource/background/building.png", -1, false);
					buildings.fitHeight(GameManager.gc);
					buildings.setZ(2);
				} catch (ResourceException e) {
					e.printStackTrace();
				}
				
				try {
					cloud = new MovingBackground(0, 0, -2, "resource/background/cloud.png", -1, false);
					cloud.fitHeight(GameManager.gc);
					cloud.setZ(3);
				} catch (ResourceException e) {
					e.printStackTrace();
				}
				try {
					road = new MovingBackground(0, 0, -10, "resource/background/road.png", -1, false);
					road.fitHeight(GameManager.gc);
					road.setZ(4);
				} catch (ResourceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				timeBar = new Bar(10, 10, 150, "Time   ", 100, Color.GREEN);
				healthBar = new Bar(13, 60, 150, "Health", 100, Color.GREEN);
				enemyBar = new Bar(350, 10, 150, "Enemy", 100, Color.GREEN);
				
				score = new GameText(10, 220, "Score 0 0 0", GameManager.gc, Color.WHITE);
				
				badGuy = new PleaseBeKind(20, ProgressHolder.windowHeight-ProgressHolder.characterSize-30);
				badGuy.resizeByHeight(ProgressHolder.characterSize);
				badGuy.setZ(1010);
				
				try {
					monsterOne = new CompEngEss(2000, ProgressHolder.monsterPositionY);
					monsterOne.resizeByHeight(ProgressHolder.characterSize);
					monsterOne.allowOffScreen = true;
					monsterOne.setZ(1015);
				} catch (ResourceException e) {
					e.printStackTrace();
				}
				
				try {
					monsterTwo = new Discrete(2000, ProgressHolder.monsterPositionY);
					monsterTwo.resizeByHeight(ProgressHolder.characterSize);
					monsterTwo.allowOffScreen = true;
					monsterTwo.setZ(1015);
				} catch (ResourceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				try {
					monsterThree = new ProgMeth(2000, ProgressHolder.monsterPositionY - ProgressHolder.characterSize/2);
					monsterThree.resizeByHeight((int)(ProgressHolder.characterSize*1.5));
					monsterThree.allowOffScreen = true;
					monsterThree.setZ(1015);
				} catch (ResourceException e) {
					e.printStackTrace();
				}
				
				if(ProgressHolder.mainCharacter == null) {
					ProgressHolder.mainCharacter = new MainCharacterMale(300, ProgressHolder.windowHeight-ProgressHolder.characterSize-30, "Steve");
					ProgressHolder.mainCharacter.resizeByHeight(ProgressHolder.characterSize);
				}
			}
			
			try {
				if(gameOver == null) {
					gameOver = new DrawImage(0, 0, "resource/text/gameover.png");
					gameOver.setSizeByHeight(ProgressHolder.windowHeight);
				} else {
					gameOver.setX(0);
					gameOver.setY(0);
				}
				
				if(start == null) {
					start = new DrawImage(0, 0, "resource/text/start.png");
					start.setSizeByHeight(ProgressHolder.windowHeight);
				} else {
					start.setX(0);
					start.setY(0);
				}
				
				
			} catch (ResourceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			score.setY(220);
			
			ProgressHolder.health = 100;
			ProgressHolder.mainCharacter.setY(ProgressHolder.windowHeight-ProgressHolder.characterSize-30);
			ProgressHolder.mainCharacter.setX(300);
			
			RenderableHolder.instance.add(blueSky);
			RenderableHolder.instance.add(buildings);
			RenderableHolder.instance.add(cloud);
			RenderableHolder.instance.add(road);
			RenderableHolder.instance.add(ProgressHolder.mainCharacter);
			
			if(!ProgressHolder.isGodMode) {
				RenderableHolder.instance.add(healthBar);
				RenderableHolder.instance.add(enemyBar);
				RenderableHolder.instance.add(score);
			} else {
				timeBar.setZ(Integer.MAX_VALUE-1);
			}
			RenderableHolder.instance.add(timeBar);
			RenderableHolder.instance.add(badGuy);
			RenderableHolder.instance.add(monsterOne);
			RenderableHolder.instance.add(monsterTwo);
			RenderableHolder.instance.add(monsterThree);
		});
	
	}
	
	@Override
	public void addEventListener() {}
	
	@Override
	public void setUpThreadAndAnimation() {
		// TODO Auto-generated method stub
		ProgressHolder.frameCounter = 0;
		ProgressHolder.BlackScreen.resetDrawDissolveHold();
		
		Thread animation = new Thread(() -> {
			try {
				ProgressHolder.BlackScreen.resetDrawDissolveHold();
				while(ProgressHolder.frameCounter < (int)(0.5*ProgressHolder.frameRate)) {
					Platform.runLater(()-> {
						GameManager.gc.clearRect(0, 0, ProgressHolder.windowWidth, ProgressHolder.windowHeight);
						updateMove();
						updateValue();
						updateDraw();
						
						ProgressHolder.BlackScreen.drawFadeInHoldFadeOut(0, 0, 500, GameManager.gc);
					});
					
					ProgressHolder.frameCounter++;
					Thread.sleep((long)ProgressHolder.frameTime);
				}
				
				ProgressHolder.BlackScreen.resetDrawDissolveHold();
				
				while(!drawFadeOutDone) {
					Utility.AudioUtility.playGameMusic();
					Platform.runLater(() -> {
						updateMove();
						updateValue();
						updateDraw();
						if(timeCountDown <= 0) drawFadeOut = true;
						
						if(ProgressHolder.frameCounter > 100 && ProgressHolder.frameCounter <= 100 + ProgressHolder.frameRate) {
							start.drawFadeInHoldFadeOut(0, 500, 500, GameManager.gc);
						}
						
						if(drawFadeOut && !drawFadeOutDone) {
							if(drawFade()) {
								ProgressHolder.BlackScreen.draw(GameManager.gc);
								drawFadeOut = false;
							};
						}
						
						if(drawFadeOutDone) {
							ProgressHolder.BlackScreen.draw(GameManager.gc);
							RenderableHolder.instance.getEntities().clear();
							Utility.AudioUtility.stopGameMusic();
						}
						
					});
					
					ProgressHolder.frameCounter++;
					Thread.sleep((long)ProgressHolder.frameTime);
				}
				
				
			} catch(Exception e) {
				
			}
			
			GameManager.changeScene();
			System.out.println("Done");
		});
		
		ProgressHolder.AllThreads.add(animation);
		animation.start();
		
		new Thread(() -> {
			try {
				synchronized(ProgressHolder.AllThreads) {
					System.out.println(ProgressHolder.AllThreads);
					for(Thread x: ProgressHolder.AllThreads) {
						x.join();
					}
				}
//				System.out.println(drawFadeOutDone);
				if(drawFadeOutDone) {
					ProgressHolder.mainCharacter.reset();
					//GameMenu gameMenu = new GameMenu();
					if(isGameOver) {
						new Thread(() -> {
							GameText gameOverText = new GameText((ProgressHolder.windowWidth - GameText.getWidth("Game Over"))/2, ProgressHolder.windowHeight/2 + GameText.getLineHeight(), "Game Over", GameManager.gc, Color.LIGHTYELLOW);
							
							try {
								for(int i = 0; i < 1.5*ProgressHolder.frameRate; i++) {
									GameManager.gc.clearRect(0, 0, ProgressHolder.windowWidth, ProgressHolder.windowHeight);
									
									ProgressHolder.BlackScreen.draw(GameManager.gc);
									if(gameOver != null) {
										gameOver.drawFadeInHoldFadeOut(500, 500, 500, GameManager.gc);
									}
									else {
										gameOverText.drawFadeInHoldFadeOut(500, 500, 500, GameManager.gc);
									}
									Thread.sleep((long)ProgressHolder.frameTime);
								}
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}).start();
					}
					
					new Thread(() -> {
						try {
							Thread.sleep(1500);
							GameManager.changeScene();
							ResultScene.reset();
							ResultScene resultScene = new ResultScene();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}).start();
					//Load Next Scene
					
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	@Override
	public void updateDraw() {
		// TODO Auto-generated method stub
		GameManager.gc.clearRect(0, 0, GameManager.gc.getCanvas().getWidth(), GameManager.gc.getCanvas().getHeight());
		
		for(IRenderable e: RenderableHolder.instance.getEntities()) {
			if(e instanceof Bar) {
				if((((Bar) e).getText() == "Health" || ((Bar) e).getText() == "Time   ")) {
					e.draw(GameManager.gc);
				}
				else if(onScreenObstacle.size() > 0){
					e.draw(GameManager.gc);
				}
			} else {
				e.draw(GameManager.gc);
			}
		}
		
		for(int i = onScreenObstacle.size()-1; i >= 0; i--) {
			onScreenObstacle.get(i).draw(GameManager.gc);
		}
		
		if(ProgressHolder.isGodMode) {
			spotlight.draw(GameManager.gc);
			spotlightForeground.draw(GameManager.gc);
			timeBar.draw(GameManager.gc);
		}
		
	}

	@Override
	public void updateMove() {
		// TODO Auto-generated method stub
		ProgressHolder.mainCharacter.updateFrame();
		ProgressHolder.mainCharacter.move();
		ProgressHolder.mainCharacter.translateX(-2);
		
		badGuy.updateFrame();
		monsterOne.updateFrame();
		monsterOne.move();
		monsterTwo.updateFrame();
		monsterTwo.move();
		monsterThree.updateFrame();
		monsterThree.move();
		
		//monster one
		if(ProgressHolder.frameCounter == 5*ProgressHolder.frameRate) {
			monsterOne.setX(ProgressHolder.windowWidth + 30);
		}
		if((5*ProgressHolder.frameRate <= ProgressHolder.frameCounter) && (ProgressHolder.frameCounter <= 7*ProgressHolder.frameRate)) {
			monsterOne.translateX(-1);
		}
		
		if(ProgressHolder.frameCounter >= 8*ProgressHolder.frameRate) {
			if(ProgressHolder.frameCounter%(5*ProgressHolder.frameRate) == 5*ProgressHolder.frameRate-1) {
				Obstacle tmp = monsterOne.releaseObstacle();
				if(tmp != null) {
					onScreenObstacle.add(tmp);
				}	
			}
		}
		
		//monster two
		if(ProgressHolder.frameCounter == 35*ProgressHolder.frameRate) {
			monsterTwo.setX(ProgressHolder.windowWidth + 30);
		}
		if((35*ProgressHolder.frameRate <= ProgressHolder.frameCounter) && (ProgressHolder.frameCounter <= 37*ProgressHolder.frameRate)) {
			monsterTwo.translateX(-1);
		}
		
		if(ProgressHolder.frameCounter >= 38*ProgressHolder.frameRate) {
			if(ProgressHolder.frameCounter%(10*ProgressHolder.frameRate) == 10*ProgressHolder.frameRate-1) {
				Obstacle tmp = monsterTwo.releaseObstacle();
				if(tmp != null) {
					tmp.setSpeed(1);
					onScreenObstacle.add(tmp);
				}	
			}
		}
		
		//monster three
		if(ProgressHolder.frameCounter == 57*ProgressHolder.frameRate) {
			monsterThree.setX(ProgressHolder.windowWidth + 60);
		}
		if((57*ProgressHolder.frameRate <= ProgressHolder.frameCounter) && (ProgressHolder.frameCounter <= 58.5*ProgressHolder.frameRate)) {
			monsterThree.translateX(-2);
		}
		
		if(ProgressHolder.frameCounter >= 60*ProgressHolder.frameRate) {
			if(ProgressHolder.frameCounter%(3*ProgressHolder.frameRate) == 3*ProgressHolder.frameRate-1) {
				Obstacle tmp = monsterThree.releaseObstacle();
				if(tmp != null) {
					tmp.setSpeed(1);
					onScreenObstacle.add(tmp);
				}	
			}
		}
		
		
		for(int i = onScreenObstacle.size()-1; i >= 0; i--) {
			onScreenObstacle.get(i).setZ(1200);
			if(onScreenObstacle.get(i).isDestroy() || (onScreenObstacle.get(i).getX()+onScreenObstacle.get(i).getWidth()) <= 0 ) {
				RenderableHolder.instance.getEntities().remove(onScreenObstacle.get(i));
				onScreenObstacle.remove(i);
			}
		}
		
		for(int i = onScreenObstacle.size()-1; i >= 0; i--) {
			onScreenObstacle.get(i).move();
		}
		
		int offset = 20;
		
		if(Entity.isCollide(badGuy.getX(), badGuy.getY(), badGuy.getHeight(), badGuy.getWidth(), ProgressHolder.mainCharacter.getX()+offset, ProgressHolder.mainCharacter.getY(), ProgressHolder.mainCharacter.getHeight(), ProgressHolder.mainCharacter.getWidth()-offset)) {
			ProgressHolder.health -= 1; //Test purpose -= 5
			if(ProgressHolder.scoreCompEngEss > 0 && ProgressHolder.frameCounter < 38*ProgressHolder.frameRate) ProgressHolder.scoreCompEngEss -= 1;
			if(ProgressHolder.scoreDiscrete > 0 && ProgressHolder.frameCounter < 60*ProgressHolder.frameRate) ProgressHolder.scoreDiscrete -= 1;
			if(ProgressHolder.scoreProgMeth > 0) ProgressHolder.scoreProgMeth -= 1;
			ProgressHolder.mainCharacter.gotHit(false, false);
		}
		
		if(Entity.isCollide(monsterOne.getX(), monsterOne.getY(), monsterOne.getHeight(), monsterOne.getWidth(), ProgressHolder.mainCharacter.getX()+offset, ProgressHolder.mainCharacter.getY(), ProgressHolder.mainCharacter.getHeight(), ProgressHolder.mainCharacter.getWidth()-offset)) {
			ProgressHolder.health -= 1; //Test purpose -= 5
			ProgressHolder.mainCharacter.gotHit(true, false);
		}
		
		if(Entity.isCollide(monsterTwo.getX(), monsterTwo.getY(), monsterTwo.getHeight(), monsterTwo.getWidth(), ProgressHolder.mainCharacter.getX()+offset, ProgressHolder.mainCharacter.getY(), ProgressHolder.mainCharacter.getHeight(), ProgressHolder.mainCharacter.getWidth()-offset)) {
			ProgressHolder.health -= 5; //Test purpose -= 5
			ProgressHolder.mainCharacter.gotHit(true, false);
		}
		
		if(Entity.isCollide(monsterThree.getX(), monsterThree.getY(), monsterThree.getHeight(), monsterThree.getWidth(), ProgressHolder.mainCharacter.getX()+offset, ProgressHolder.mainCharacter.getY(), ProgressHolder.mainCharacter.getHeight(), ProgressHolder.mainCharacter.getWidth()-offset)) {
			ProgressHolder.health -= 10; //Test purpose -= 5
			ProgressHolder.mainCharacter.gotHit(true, false);
		}
		
		if(ProgressHolder.health <= 0) {
			ProgressHolder.mainCharacter.jumpDead();
			isGameOver = true;
			Utility.AudioUtility.stopGameMusic();
			drawFadeOut = true;
		}
	}

	@Override
	public void updateValue() {
		// TODO Auto-generated method stub
		
		//God mode
		if(ProgressHolder.isGodMode) {
			healthBar.setProgress(100);
			ProgressHolder.health = 100;
			ProgressHolder.scoreCompEngEss = 100;
			ProgressHolder.scoreDiscrete = 100;
			ProgressHolder.scoreProgMeth = 100;
		} else {
			healthBar.setProgress(ProgressHolder.health);
		}
		
		score.setText("Score " + ProgressHolder.scoreCompEngEss + " " + ProgressHolder.scoreDiscrete + " " + ProgressHolder.scoreProgMeth);
		
		timeCountDown -= ProgressHolder.frameTime;
		timeBar.setProgress((int)(100*timeCountDown/(double)totalTimeCountDown));
		if(timeBar.getProgress() <= 90) {
			timeBar.setColor(Color.LAWNGREEN);
		}
		if(timeBar.getProgress() <= 80) {
			timeBar.setColor(Color.GREENYELLOW);
		}
		if(timeBar.getProgress() <= 70) {
			timeBar.setColor(Color.YELLOW);
		}
		if(timeBar.getProgress() <= 50) {
			timeBar.setColor(Color.DARKORANGE);
		}
		if(timeBar.getProgress() <= 40) {
			timeBar.setColor(Color.CORAL);
		}
		if(timeBar.getProgress() <= 30) {
			timeBar.setColor(Color.INDIANRED);
		}
		if(timeBar.getProgress() <= 20) {
			timeBar.setColor(Color.RED);
		}
		
		
		if(healthBar.getProgress() <= 70) {
			healthBar.setColor(Color.YELLOW);
		}
		if(healthBar.getProgress() <= 30) {
			healthBar.setColor(Color.RED);
		}
		
		if(onScreenObstacle.size() > 0) {
			if(onScreenObstacle.get(0).getPercentHealth() <= 0) {
				onScreenObstacle.remove(0);
			}
			if(onScreenObstacle.size() > 0) {
				enemyBar.setText(onScreenObstacle.get(0).getType());
				enemyBar.setProgress(onScreenObstacle.get(0).getPercentHealth());
				if(onScreenObstacle.get(0).getPercentHealth() == 100) {
					onScreenObstacle.get(0).resetStateTrack();
				}
				
				//System.out.println(onScreenObstacle.get(0).getPercentHealth());
				
				enemyBar.setColor(Color.GREEN);
				if(enemyBar.getProgress() <= 70) enemyBar.setColor(Color.YELLOW);
				if(enemyBar.getProgress() <= 30) enemyBar.setColor(Color.RED);
				
				GameManager.addCallBackToHolder(onScreenObstacle.get(0));
			}
		}
	}
	
	public static void reset() throws ResourceException {
		ProgressHolder.frameCounter = 0;
		RenderableHolder.instance.getEntities().clear();
		
		ProgressHolder.health = 100;
		
		if(onScreenObstacle != null) onScreenObstacle.clear();
		if(timeBar != null) timeBar.setColor(Color.GREEN);
		if(healthBar != null) healthBar.setColor(Color.GREEN);
		if(enemyBar != null) enemyBar.setColor(Color.GREEN);
		if(score != null) score.setY(220);
		
		if(monsterOne != null) {
			monsterOne.resetToo();
			monsterOne.allowOffScreen = true;
			monsterOne.setX(2000);
			monsterOne.setY(ProgressHolder.monsterPositionY);
		}
		
		if(monsterTwo != null) {
			monsterTwo.resetToo();
			monsterTwo.allowOffScreen = true;
			monsterTwo.setX(2000);
			monsterTwo.setY(ProgressHolder.monsterPositionY);
		}
		
		if(monsterThree != null) {
			monsterThree.resetToo();
			monsterThree.allowOffScreen = true;
			monsterThree.setX(2000);
			monsterThree.setY(ProgressHolder.monsterPositionY - ProgressHolder.characterSize/2);
		}
		
	}
	
	public boolean drawFade() {
		boolean tmp = ProgressHolder.BlackScreen.drawDissolveHold(true, 500, GameManager.gc);
		drawFadeOutDone = tmp;
		return tmp;
	}
	
}
