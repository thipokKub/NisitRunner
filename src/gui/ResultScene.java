package gui;

import java.util.ArrayList;

import Utility.ProgressHolder;
import Utility.RenderableHolder;
import instance.BackButton;
import instance.ContinueButton;
import instance.SettingButton;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import logic.GameManager;
import model.ButtonImage;
import model.GameInput;
import model.IRenderable;
import model.ResourceException;
import model.DrawImage;

public class ResultScene extends GameInput{
	
	public static ResultScene me;
	public static boolean drawFadeOut;
	private static boolean drawFadeOutDone;
	
	private static ContinueButton continueButton;
	
	private static DrawImage gradeCompEngEss;
	private static DrawImage gradeDiscrete;
	private static DrawImage gradeProgMeth;
	
	private static final int gradeSize = (int)((0.7)*(5.4/38.1)*ProgressHolder.windowHeight);
	
	private static final Image gradeA = new Image(ClassLoader.getSystemResource("resource/grade/A.png").toString());
	private static final Image gradeBPlus = new Image(ClassLoader.getSystemResource("resource/grade/B_Plus.png").toString());
	private static final Image gradeB = new Image(ClassLoader.getSystemResource("resource/grade/B.png").toString());
	private static final Image gradeCPlus = new Image(ClassLoader.getSystemResource("resource/grade/C_Plus.png").toString());
	private static final Image gradeC = new Image(ClassLoader.getSystemResource("resource/grade/C.png").toString());
	private static final Image gradeDPlus = new Image(ClassLoader.getSystemResource("resource/grade/D_Plus.png").toString());
	private static final Image gradeD = new Image(ClassLoader.getSystemResource("resource/grade/D.png").toString());
	private static final Image gradeF = new Image(ClassLoader.getSystemResource("resource/grade/F.png").toString());
	
	private static DrawImage Background;
	
	public ResultScene() {
		me = this;
		drawFadeOut = false;
		drawFadeOutDone = false;
		ProgressHolder.frameCounter = 0;
		
		if(gradeCompEngEss == null) gradeCompEngEss = new DrawImage((int)(ProgressHolder.windowWidth*((12.03/2.0 + 55.5)/69.85)) - this.gradeSize/2, (int)((15.56/38.1)*ProgressHolder.windowHeight) + 10);
		if(gradeDiscrete == null) gradeDiscrete = new DrawImage((int)(ProgressHolder.windowWidth*((12.03/2.0 + 55.5)/69.85)) - this.gradeSize/2, (int)(((15.56/38.1)*ProgressHolder.windowHeight) + (double)this.gradeSize/0.7) + 10);
		if(gradeProgMeth == null) gradeProgMeth = new DrawImage((int)(ProgressHolder.windowWidth*((12.03/2.0 + 55.5)/69.85)) - this.gradeSize/2, (int)(((15.56/38.1)*ProgressHolder.windowHeight) + 2*(double)this.gradeSize/0.7) + 10);
		
		System.out.println("New Result Scene");
		
		this.setUpCanvas();
		this.addEventListener();
		this.setUpThreadAndAnimation();
		
	}
	
	@Override
	public void setUpCanvas() {
		// TODO Auto-generated method stub
		Platform.runLater(() -> {
			if(continueButton == null) {
				continueButton = new ContinueButton(0, 0, 50);
			}
			if(Background == null) {
				//initialize and reset
				try {
					Background = new DrawImage(0, 0, "resource/background/reg.png");
					Background.setZ(10);
				} catch (ResourceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				//reset all value
				Background.setX(0); Background.setY(0);
			}
			
			continueButton.setX(ProgressHolder.windowWidth-continueButton.getWidth()-50);
			continueButton.setY((int)((6.91/38.1)*ProgressHolder.windowHeight));
			assignGrade(gradeCompEngEss, ProgressHolder.scoreCompEngEss);
			assignGrade(gradeDiscrete, ProgressHolder.scoreDiscrete);
			assignGrade(gradeProgMeth, ProgressHolder.scoreProgMeth);
			
			gradeCompEngEss.setSizeByHeight(gradeSize);
			gradeDiscrete.setSizeByHeight(gradeSize);
			gradeProgMeth.setSizeByHeight(gradeSize);
			
			RenderableHolder.instance.add(Background);
			RenderableHolder.instance.add(gradeCompEngEss);
			RenderableHolder.instance.add(gradeDiscrete);
			RenderableHolder.instance.add(gradeProgMeth);
			RenderableHolder.instance.add(continueButton);
			
			GameManager.addCallBackToHolder(continueButton);
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
	
	private void assignGrade(DrawImage image, int score) {
		if(score >= 95) {
			image.setImage(gradeA);
		}
		else if(score >= 90) {
			image.setImage(gradeBPlus);
		}
		else if(score >= 85) {
			image.setImage(gradeB);
		}
		else if(score >= 80) {
			image.setImage(gradeCPlus);
		}
		else if(score >= 70) {
			image.setImage(gradeC);
		}
		else if(score >= 60) {
			image.setImage(gradeDPlus);
		}
		else if(score >= 50) {
			image.setImage(gradeD);
		}
		else {
			image.setImage(gradeF);
		}
		
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
					updateMove();
					updateValue();
					Platform.runLater(()-> {
						GameManager.gc.clearRect(0, 0, ProgressHolder.windowWidth, ProgressHolder.windowHeight);
						
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
					GameMenu gameMenu = new GameMenu();
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
			e.draw(GameManager.gc);
		}
	}

	@Override
	public void updateMove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateValue() {
		// TODO Auto-generated method stub
		
	}
	
	public static void reset() {
		if(continueButton != null) {
			continueButton.reset();
		}
	}
	
	public boolean drawFade() {
		boolean tmp = ProgressHolder.BlackScreen.drawDissolveHold(true, 500, GameManager.gc);
		drawFadeOutDone = tmp;
		return tmp;
	}
	
}
