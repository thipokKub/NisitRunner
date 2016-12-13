package gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Utility.AudioUtility;
import Utility.ProgressHolder;
import Utility.RenderableHolder;
import instance.BackButton;
import instance.SettingButton;
import instance.VolumeDown;
import instance.VolumeUp;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import logic.GameManager;
import model.ButtonImage;
import model.Entity;
import model.GameInput;
import model.GameText;
import model.IRenderable;
import model.MovingBackground;
import model.ResourceException;
import model.DrawImage;

public class SettingScene extends GameInput{
	
	private static ArrayList<Image> myImage = new ArrayList<>();
	public static SettingScene me;
	public static boolean drawFadeOut;
	private static boolean drawFadeOutDone;
	
	private static DrawImage background, foreground;
	private static MovingBackground midground;
	private static BackButton backButton;
	private static VolumeUp volumeUpButton;
	private static VolumeDown volumeDownButton;
	private static GameText warning;
	private static double volumeLevel;
	
	private static ArrayList<Image> numberImage;
	
	public SettingScene() throws ResourceException {
		me = this;
		drawFadeOut = false;
		drawFadeOutDone = false;
		ProgressHolder.frameCounter = 0;
		
		volumeLevel = AudioUtility.getVolume();
		if(numberImage == null) {
			ProgressHolder.checkAvalible("resource/text/0.png");
			ProgressHolder.checkAvalible("resource/text/1.png");
			ProgressHolder.checkAvalible("resource/text/2.png");
			ProgressHolder.checkAvalible("resource/text/3.png");
			ProgressHolder.checkAvalible("resource/text/4.png");
			ProgressHolder.checkAvalible("resource/text/5.png");
			ProgressHolder.checkAvalible("resource/text/6.png");
			ProgressHolder.checkAvalible("resource/text/7.png");
			ProgressHolder.checkAvalible("resource/text/8.png");
			ProgressHolder.checkAvalible("resource/text/9.png");
			
			numberImage = new ArrayList<Image>() {
				{
					add(new Image(ClassLoader.getSystemResource("resource/text/0.png").toString()));
					add(new Image(ClassLoader.getSystemResource("resource/text/1.png").toString()));
					add(new Image(ClassLoader.getSystemResource("resource/text/2.png").toString()));
					add(new Image(ClassLoader.getSystemResource("resource/text/3.png").toString()));
					add(new Image(ClassLoader.getSystemResource("resource/text/4.png").toString()));
					add(new Image(ClassLoader.getSystemResource("resource/text/5.png").toString()));
					add(new Image(ClassLoader.getSystemResource("resource/text/6.png").toString()));
					add(new Image(ClassLoader.getSystemResource("resource/text/7.png").toString()));
					add(new Image(ClassLoader.getSystemResource("resource/text/8.png").toString()));
					add(new Image(ClassLoader.getSystemResource("resource/text/9.png").toString()));
				}};
		}
		
		System.out.println("New Setting Scene");
		
		this.setUpCanvas();
		this.addEventListener();
		this.setUpThreadAndAnimation();
		
	}
	
	@Override
	public void setUpCanvas() {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			if(background == null) {
				try {
					background = new DrawImage(0, 0, "resource/Background/setting_background.png");
					background.setZ(10);
					
					foreground = new DrawImage(0, 0, "resource/Background/setting_foreground.png");
					foreground.setZ(12);
					
					midground = new MovingBackground(0, 0, -1, "resource/Background/setting_midground.png", 1, false);
					midground.fitHeight(GameManager.gc);
					midground.setZ(11);
					
					int volumeUpPosX = (int)((40.92/69.85)*ProgressHolder.windowWidth);
					int volumeDownPosX = (int)((46.85/69.85)*ProgressHolder.windowWidth);
					int volumePosY = (int)((11.64/38.1)*ProgressHolder.windowHeight);
					int volumeHeight = (int)((4.3/38.10)*ProgressHolder.windowHeight);
					
					volumeUpButton = new VolumeUp(volumeUpPosX, volumePosY, volumeHeight, volumeHeight);
					volumeDownButton = new VolumeDown(volumeDownPosX, volumePosY, volumeHeight, volumeHeight);
					
					backButton = new BackButton(30, 250, 40, 35);
					
				} catch (ResourceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				int backPosX = (int)((2.43/69.85)*ProgressHolder.windowWidth);
				int backPosY = (int)((2.05/38.1)*ProgressHolder.windowHeight);
				int backHeight = (int)((4.23/38.10)*ProgressHolder.windowHeight);
				int backWidth = (int)((1866.0/607.0)*backHeight);
				
				backButton = new BackButton(backPosX, backPosY, backWidth, backHeight);
			}
			else {
				background.setX(0); background.setY(0);
			}
			
			String warningText = "If volume value was change this will restart the game music";
			warning = new GameText(ProgressHolder.windowWidth/2 - GameText.getWidth(warningText, Font.font("Tahoma", 20))/2, ProgressHolder.windowHeight, warningText, GameManager.gc, Color.RED);
			warning.setFont(Font.font("Tahoma", 20));
			
			backButton.reset();
			
			System.out.println(volumeLevel);
			
			RenderableHolder.instance.add(background);
			RenderableHolder.instance.add(midground);
			RenderableHolder.instance.add(foreground);
			RenderableHolder.instance.add(warning);
			RenderableHolder.instance.add(backButton);
			RenderableHolder.instance.add(volumeUpButton);
			RenderableHolder.instance.add(volumeDownButton);
			
			ProgressHolder.updateOnClicked.add(backButton);
			ProgressHolder.updateOnClicked.add(volumeUpButton);
			ProgressHolder.updateOnClicked.add(volumeDownButton);
		});
	
	}
	
	@Override
	public void addEventListener() {
		// TODO Auto-generated method stub
		GameManager.root.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				me.updateOnMouseClick(event);
			}
		});
	}
	
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
					Platform.runLater(() -> {
						me.updateDraw();
						
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
					GameManager.changeScene();
					GameMenu.reset();
					GameMenu gameMenu = new GameMenu();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}
	
	public void reset() {
		backButton.reset();
	}
	
	public ArrayList<Image> getNumberImage(int number) {
		int numberWidth = (int)((5.68/69.85)*ProgressHolder.windowWidth);
		int numberHeight = (int)((240.0/160.0)*numberWidth);
		
		ArrayList<Image> tmp = new ArrayList<>();
		if(number < 0) number = 0;
		if(number >= 100) number = 99;
		
		tmp.add(Entity.scale(numberImage.get(number/10), numberWidth, numberHeight, false));
		tmp.add(Entity.scale(numberImage.get(number%10), numberWidth, numberHeight, false));
		return tmp;
	}
	
	
	@Override
	public void updateDraw() {
		// TODO Auto-generated method stub
		GameManager.gc.clearRect(0, 0, GameManager.gc.getCanvas().getWidth(), GameManager.gc.getCanvas().getHeight());
		
		for(Image x: myImage) {
			GameManager.gc.drawImage(x, 10, 10);
		}
		
		RenderableHolder.instance.sort();
		
		for(IRenderable e: RenderableHolder.instance.getEntities()) {
			e.draw(GameManager.gc);
		}
		
		int firstNumberPosX = (int)((25.75/69.85)*ProgressHolder.windowWidth);
		int secondNumberPosX = (int)((31.93/69.85)*ProgressHolder.windowWidth);
		int numberPosY = (int)((10.2/38.1)*ProgressHolder.windowHeight);
		
		GameManager.gc.drawImage(getNumberImage(AudioUtility.getVolume()).get(0), firstNumberPosX, numberPosY);
		GameManager.gc.drawImage(getNumberImage(AudioUtility.getVolume()).get(1), secondNumberPosX, numberPosY);

	}

	@Override
	public void updateMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateValue() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean drawFade() {
		boolean tmp = ProgressHolder.BlackScreen.drawDissolveHold(true, 500, GameManager.gc);
		drawFadeOutDone = tmp;
		return tmp;
	}
	
}
