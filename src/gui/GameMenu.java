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
	
	public static GameMenu me;
	public static boolean drawFadeOut;
	public static boolean showCredits;
	private static boolean drawFadeOutDone;
	public static boolean startCalled;
	public static boolean settingCalled;
	public static boolean helpCalled;
	
	private static MovingBackground sky;
	private static MovingBackground buildings;
	private static GameTitle myGameTitle;
	private static StartButton startButton;
	private static SettingButton settingButton;
	private static CreditsButton creditsButton;
	private static ExitButton exitButton;
	private static HelpButton helpButton;
	public static ArrayList<DrawImage> helpFrameTmp;
	public static DrawImage logo;
	
	public GameMenu() {
		System.out.println("New Game Menu");
		
		me = this;
		drawFadeOut = false;
		drawFadeOutDone = false;
		showCredits = false;
		startCalled = false;
		settingCalled = false;
		helpCalled = false;
		helpFrameTmp = new ArrayList<DrawImage>();
		
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
		helpFrameTmp.clear();
		for(IRenderable e: RenderableHolder.instance.getEntities()) {
			for(int i = 0; i < 4; i++) {
				if(e.equals(helpButton.getHelpFrame()[i])) {
			 		helpFrameTmp.add((DrawImage) e);
			  	}
			}
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
					sky = new MovingBackground(0, 0, 10, "resource/background/sky_home.png", -1, false);
					sky.fitHeight(GameManager.gc);
					sky.setZ(1);
				} catch (ResourceException e) {
					e.printStackTrace();
				}
				
				try {
					GameScene.spotlightForeground = new MovingBackground(0, 0, 20, "resource/background/foreground.png", 1, false);
					GameScene.spotlightForeground.fitHeight(GameManager.gc);
					GameScene.spotlightForeground.setZ(1499);
				} catch (ResourceException e) {
					e.printStackTrace();
				}
				
				try {
					buildings = new MovingBackground(0, 0, -3, "resource/background/building_home.png", -1, false);
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
				helpButton = new HelpButton(ProgressHolder.windowWidth-80, ProgressHolder.windowHeight-80, 50, 50);
				
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
			RenderableHolder.instance.add(helpButton);
			
			GameManager.addCallBackToHolder(myGameTitle);
			GameManager.addCallBackToHolder(startButton);
			GameManager.addCallBackToHolder(settingButton);
			GameManager.addCallBackToHolder(creditsButton);
			GameManager.addCallBackToHolder(exitButton);
			GameManager.addCallBackToHolder(helpButton);
			
			ProgressHolder.updateOnKeyPressed.add(ProgressHolder.mainCharacter);
			
		});
	}
	
	@Override
	public void addEventListener() {
		GameManager.root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				me.updateOnMouseClick(event);
			}
		});
		
		GameManager.root.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Platform.runLater(() -> {
					me.updateOnMouseRelease(event);
				});
			}
		});
		
		GameManager.root.setOnMouseMoved(new EventHandler<MouseEvent>() {
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
		
		Utility.ProgressHolder.AllThreads.add(new Thread(() -> {
			try {
				if(ProgressHolder.isFirstTime) {
					logo.draw(GameManager.gc);
					Thread.sleep(500);
					while(ProgressHolder.frameCounter <= 2*ProgressHolder.frameRate) {
						Platform.runLater(() -> {
							GameManager.gc.clearRect(0, 0, ProgressHolder.windowWidth, ProgressHolder.windowHeight);
							GameManager.gc.setFill(Color.BLACK);
							GameManager.gc.fillRect(0, 0, ProgressHolder.windowWidth, ProgressHolder.windowHeight);
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
					updateMove();
					updateValue();
					Platform.runLater(() -> {
						
						GameManager.gc.clearRect(0, 0, ProgressHolder.windowWidth, ProgressHolder.windowHeight);
						
						updateDraw();
						
						ProgressHolder.BlackScreen.drawFadeInHoldFadeOut(0, 0, 500, GameManager.gc);
						ProgressHolder.frameCounter++;
					});
				}
			} catch(Exception e) {
				
			}
		}));
		Utility.ProgressHolder.AllThreads.get(Utility.ProgressHolder.AllThreads.size()-1).start();
		ProgressHolder.BlackScreen.resetDrawDissolveHold();
		
		Utility.ProgressHolder.AllThreads.add(new Thread(() -> {
			try {
				if(ProgressHolder.isFirstTime) Thread.sleep(3000);
				else Thread.sleep(1000);
				while(!drawFadeOutDone) {
					updateMove();
					updateValue();
					Platform.runLater(() -> {
						Utility.AudioUtility.playBackgroundMusic();
						
						updateDraw();
						
						if(showCredits) {
							ProgressHolder.GameCredits.drawFadeInHoldFadeOut(500, 2000, 500, GameManager.gc);
						}
						
						if(helpCalled) {
							ProgressHolder.BlackScreenTransparent.drawFadeInHoldFadeOut(500, 6000, 500, GameManager.gc);
							for(DrawImage e: helpFrameTmp) {
								e.draw(GameManager.gc);
							}
							helpButton.draw(GameManager.gc);
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
		
		Utility.ProgressHolder.AllThreads.get(Utility.ProgressHolder.AllThreads.size()-1).start();
		
		new Thread(() -> {
			try {
				Utility.ProgressHolder.AllThreads.get(Utility.ProgressHolder.AllThreads.size()-1).join();
				if(settingCalled) {
					System.out.println("Setting Called");
					ProgressHolder.BlackScreen.resetDrawDissolveHold();
					
					GameManager.changeScene();
					helpCalled = false;
					showCredits = false;
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
