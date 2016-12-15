package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import Utility.AudioUtility;
import Utility.ProgressHolder;
import Utility.RenderableHolder;
import gui.GameMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.ButtonImage;
import model.GameInput;
import model.IKeyPressable;
import model.ResourceException;
import model.DrawImage;

public class GameManager extends Application{

	public static Canvas canvas;
	public static GraphicsContext gc;
	public static Scene scene;
	public static StackPane root;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		ProgressHolder.isFirstTime = true;
		ProgressHolder.isGodMode = false;
		
		primaryStage.setResizable(false);
		
		root = new StackPane();
		ProgressHolder.frameCounter = 0;
		scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.setWidth(ProgressHolder.windowWidth);
		primaryStage.setHeight(ProgressHolder.windowHeight);
		
		canvas = new Canvas(ProgressHolder.windowWidth, ProgressHolder.windowHeight);
		gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, ProgressHolder.windowWidth, ProgressHolder.windowHeight);
		
		try {
			ProgressHolder.BlackScreen = new DrawImage(0, 0, "resource/background/BlackScreen.png");
			ProgressHolder.BlackScreenTransparent = new DrawImage(0, 0, "resource/background/BlackScreenTransparent.png");
		} catch (ResourceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			GameMenu.logo = new DrawImage(ProgressHolder.windowWidth/2 - (int)(120.0*2735.0/673.0)/2, ProgressHolder.windowHeight/2 - 60, "resource/text/logo.png");
			GameMenu.logo.setSize((int)(120.0*2735.0/673.0), 120);
		} catch (ResourceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		File configFile = new File("configFile.txt");
		if(configFile.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(configFile));
			 
			String line = null;
			while ((line = br.readLine()) != null) {
				AudioUtility.setVolume(Integer.parseInt(line));
			}
		 
			br.close();
		}
		
		GameMenu GameMenu = new GameMenu();
		
		primaryStage.show();
	}

	
	public static void changeScene() {
		ProgressHolder.updateOnClicked.clear();
		ProgressHolder.updateOnHover.clear();
		ProgressHolder.updateOnReleased.clear();
		RenderableHolder.getInstance().getEntities().clear();
	}
	
	@Override
	public void stop() {
		for(Thread e: ProgressHolder.AllThreads) {
			e.interrupt();
		}
	}
	
	public static void addCallBackToHolder(ButtonImage x) {
		ProgressHolder.updateOnClicked.add(x);
		ProgressHolder.updateOnHover.add(x);
		ProgressHolder.updateOnReleased.add(x);
	}
	
	public static void addCallBackToHolder(IKeyPressable x) {
		ProgressHolder.updateOnKeyPressed.add(x);
		ProgressHolder.updateOnKeyReleased.add(x);
	}
}
