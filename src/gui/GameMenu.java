package gui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import Utility.AudioUtility;
import Utility.ProgressHolder;
import Utility.RenderableHolder;
import instance.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import logic.GameManager;
import model.Bar;
import model.ButtonImage;
import model.Entity;
import model.GameInput;
import model.GameText;
import model.IRenderable;
import model.MovingBackground;
import model.ResourceException;
import model.DrawImage;

public class GameMenu extends GameInput{
	
	private static ArrayList<Thread> allThread = Utility.ProgressHolder.AllThreads;
	public static Pane index;
	
	public static GameMenu me;
	public static boolean drawFadeOut;
	public static boolean showCredits;
	private static boolean drawFadeOutDone;
	public static boolean startCalled;
	public static boolean settingCalled;
	
	private static MovingBackground sky;
	private static MovingBackground buildings;
	private static GameTitle myGameTitle;
	private static StartButton startButton;
	private static SettingButton settingButton;
	private static CreditsButton creditsButton;
	private static ExitButton exitButton;
	public static DrawImage logo;
	
	public GameMenu() {
		System.out.println("New Game Menu");
		
		me = this;
		drawFadeOut = false;
		index = GameManager.root;
		drawFadeOutDone = false;
		showCredits = false;
		startCalled = false;
		settingCalled = false;
		
		Platform.runLater(() -> {
			try {
				logo = new DrawImage(ProgressHolder.windowWidth/2 - (int)(120.0*2735.0/673.0)/2, ProgressHolder.windowHeight/2 - 60, "resource/text/logo.png");
				logo.setSize((int)(120.0*2735.0/673.0), 120);
			} catch (ResourceException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		this.setUpCanvas();
		this.addEventListener();
		this.setUpThreadAndAnimation();
	}

	@Override
	public void updateDraw() {
		GameManager.gc.clearRect(0, 0, GameManager.gc.getCanvas().getWidth(), GameManager.gc.getCanvas().getHeight());
		for(IRenderable e: RenderableHolder.instance.getEntities()) {
			e.draw(GameManager.gc);
		}
	}

	@Override
	public void updateMove() {
		ProgressHolder.mainCharacter.updateFrame();
		if(ProgressHolder.frameCounter%ProgressHolder.frameSkip==0){
			//mainCharacter.translateX(ThreadLocalRandom.current().nextInt(-3, 0));
			ProgressHolder.mainCharacter.updateAtGameMenu();
		}
	}
	
	@Override
	public void updateValue() {
		//Update Bar value
	}
	
	public boolean drawFade() {
		boolean tmp = ProgressHolder.BlackScreen.drawDissolveHold(true, 500, GameManager.gc);
		drawFadeOutDone = tmp;
		return tmp;
	}
	
	@Override
	public void setUpCanvas() {
		
		Platform.runLater(() -> {
			if(sky == null) {
				
				try {
					sky = new MovingBackground(0, 0, 10, "resource/Background/sky_home.png", -1, false);
					sky.fitHeight(GameManager.gc);
					sky.setZ(1);
				} catch (ResourceException e) {
					e.printStackTrace();
				}
				
				try {
					GameScene.spotlightForeground = new MovingBackground(0, 0, 20, "resource/Background/foreground.png", 1, false);
					GameScene.spotlightForeground.fitHeight(GameManager.gc);
					GameScene.spotlightForeground.setZ(1499);
				} catch (ResourceException e) {
					e.printStackTrace();
				}
				
				try {
					buildings = new MovingBackground(0, 0, -3, "resource/Background/building_home.png", -1, false);
					buildings.fitHeight(GameManager.gc);
					buildings.setZ(2);
				} catch (ResourceException e) {
					e.printStackTrace();
				}
				
				
				ProgressHolder.mainCharacter = new MainCharacterMale(100, ProgressHolder.windowHeight-ProgressHolder.characterSize-10, "Steve");
				ProgressHolder.mainCharacter.resizeByHeight(ProgressHolder.characterSize);
				
				myGameTitle = new GameTitle(50, 70, 390, 225);
				startButton = new StartButton(ProgressHolder.windowWidth-200-10-150, 100, 141, 50);
				settingButton = new SettingButton(ProgressHolder.windowWidth-200-10-100, 160, 141, 50);
				creditsButton = new CreditsButton(ProgressHolder.windowWidth-200-10-50, 220, 141, 50);
				exitButton = new ExitButton(ProgressHolder.windowWidth-200-10, 280, 141, 50);
				
				ProgressHolder.CreditsOne = new GameText(80, 360, "Thipok Thammakulangkul", GameManager.gc, Color.BEIGE);
				ProgressHolder.CreditsTwo = new GameText(110, 400, "Nitipad Jaidee", GameManager.gc, Color.WHITE);
				
				try {
					ProgressHolder.GameCredits = new DrawImage(80, 270, "resource/text/credit.png");
					ProgressHolder.GameCredits.setSize((int)((2548.0/478.0)*70),70);
				} catch (ResourceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			settingButton.setX(ProgressHolder.windowWidth-200-10-100);
			settingButton.resetFrameStart();
			
			RenderableHolder.instance.add(sky);
			RenderableHolder.instance.add(buildings);
			RenderableHolder.instance.add(ProgressHolder.mainCharacter);
			RenderableHolder.instance.add(myGameTitle);
			RenderableHolder.instance.add(startButton);
			RenderableHolder.instance.add(settingButton);
			RenderableHolder.instance.add(creditsButton);
			RenderableHolder.instance.add(exitButton);
			
			GameManager.addCallBackToHolder(myGameTitle);
			GameManager.addCallBackToHolder(startButton);
			GameManager.addCallBackToHolder(settingButton);
			GameManager.addCallBackToHolder(creditsButton);
			GameManager.addCallBackToHolder(exitButton);
			
			ProgressHolder.updateOnKeyPressed.add(ProgressHolder.mainCharacter);
			
		});
	}
	
	@Override
	public void addEventListener() {
		index.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				me.updateOnMouseClick(event);
			}
		});
		
		index.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Platform.runLater(() -> {
					me.updateOnMouseRelease(event);
				});
			}
		});
		
		index.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				me.updateOnMouseHover(event);
			}
		});
		
		GameManager.scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				receiveKey(event.getCode());
			}
		});
		
		GameManager.scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				dropKey(event.getCode());
			}
		});
	}
	
	@Override
	public void setUpThreadAndAnimation() {
		GameManager.gc.setFill(Color.BLACK);
		GameManager.gc.fillRect(0, 0, ProgressHolder.windowWidth, ProgressHolder.windowHeight);
		GameManager.gc.setFont(Font.font("Time New Roman", FontWeight.LIGHT, 70));
		
		String titleString = "TMC & SMK Present";
		int stringWidth = (int)ProgressHolder.fontLoader.computeStringWidth(titleString, GameManager.gc.getFont());
		int stringHeight = (int)ProgressHolder.fontLoader.getFontMetrics(GameManager.gc.getFont()).getLineHeight();
		GameText myText = new GameText((ProgressHolder.windowWidth - stringWidth)/2, (ProgressHolder.windowHeight)/2 + stringHeight, titleString, GameManager.gc, Color.WHITE);
		myText.setFont(Font.font("Time New Roman", FontWeight.LIGHT, 70));
		
		allThread.add(new Thread(() -> {
			try {
				if(ProgressHolder.isFirstTime) {
					logo.draw(GameManager.gc);
					Thread.sleep(500);
					while(ProgressHolder.frameCounter <= 2*ProgressHolder.frameRate) {
						Platform.runLater(() -> {
							GameManager.gc.clearRect(0, 0, ProgressHolder.windowWidth, ProgressHolder.windowHeight);
							GameManager.gc.setFill(Color.BLACK);
							GameManager.gc.fillRect(0, 0, ProgressHolder.windowWidth, ProgressHolder.windowHeight);
							//myText.drawFadeInHoldFadeOut(0, 0, 2000, GameManager.gc);
							logo.drawFadeInHoldFadeOut(0, 0, 2000, GameManager.gc);
							ProgressHolder.frameCounter++;
						});
						Thread.sleep((long)ProgressHolder.frameTime);
						ProgressHolder.isFirstTime = false;
					}
				}
				else {
					ProgressHolder.frameCounter = 120;
				}
				while(ProgressHolder.frameCounter <= 3*ProgressHolder.frameRate) {
					Thread.sleep((long)ProgressHolder.frameTime);
					Platform.runLater(() -> {
						
						GameManager.gc.clearRect(0, 0, ProgressHolder.windowWidth, ProgressHolder.windowHeight);
						
						updateMove();
						updateValue();
						updateDraw();
						
						ProgressHolder.BlackScreen.drawFadeInHoldFadeOut(0, 0, 500, GameManager.gc);
						ProgressHolder.frameCounter++;
					});
				}
			} catch(Exception e) {
				
			}
		}));
		allThread.get(allThread.size()-1).start();
		ProgressHolder.BlackScreen.resetDrawDissolveHold();
		
		allThread.add(new Thread(() -> {
			try {
				if(ProgressHolder.isFirstTime) Thread.sleep(3000);
				else Thread.sleep(1000);
				while(!drawFadeOutDone) {
					Platform.runLater(() -> {
						Utility.AudioUtility.playBackgroundMusic();
						
						updateMove();
						updateValue();
						updateDraw();
						
						if(showCredits) {
//							ProgressHolder.CreditsOne.drawFadeInHoldFadeOut(500, 2000, 500, GameManager.gc);
//							ProgressHolder.CreditsTwo.drawFadeInHoldFadeOut(500, 2000, 500, GameManager.gc);
							ProgressHolder.GameCredits.drawFadeInHoldFadeOut(500, 2000, 500, GameManager.gc);
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
							//Utility.AudioUtility.getBackgroundMusic().stop();
						}
					});
					ProgressHolder.frameCounter += 1;
					Thread.sleep((int)(1000/ProgressHolder.frameRate));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			GameManager.changeScene();
			
			System.out.println("Done");
		}));
		
		allThread.get(allThread.size()-1).start();
		
		new Thread(() -> {
			try {
				allThread.get(allThread.size()-1).join();
				if(settingCalled) {
					System.out.println("Setting Called");
					ProgressHolder.BlackScreen.resetDrawDissolveHold();
					
					GameManager.changeScene();
					//SettingScene.r
					SettingScene settingScene = new SettingScene();
				}
				if(startCalled) {
					System.out.println("Start Called");
					AudioUtility.stopBackgroundMusic();
					ProgressHolder.BlackScreen.resetDrawDissolveHold();
					GameManager.changeScene();
					GameScene.reset();
					GameScene gameScene = new GameScene();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ResourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}).start();
		
	}
	
	public static void reset() {
		ProgressHolder.frameCounter = 0;
		if(ProgressHolder.mainCharacter != null) {
			ProgressHolder.mainCharacter.resetToo();
			ProgressHolder.mainCharacter.setX(100);
			ProgressHolder.mainCharacter.setY(ProgressHolder.windowHeight-ProgressHolder.characterSize-10);
		}
		if(startButton != null) {
			startButton.setX(ProgressHolder.windowWidth-200-10-150);
			startButton.reset();
		}
		
		if(settingButton != null) {
			settingButton.setX(ProgressHolder.windowWidth-200-10-100);
			settingButton.reset();
		}
		
	}
}
