package Utility;

import java.io.File;
import java.util.ArrayList;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import gui.GameMenu;
import instance.MainCharacterMale;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.ButtonImage;
import model.GameText;
import model.IKeyPressable;
import model.MovingBackground;
import model.ResourceException;
import model.DrawImage;

public class ProgressHolder {
	
	public static FontLoader fontLoader = Toolkit.getToolkit().getFontLoader();
	public static final Font f = Font.font("Time New Roman", FontWeight.LIGHT, 30);
	
	public static ArrayList<Thread> AllThreads = new ArrayList<>();
	
	//Holder for myCallBack, not draw -> already handle
	public static ArrayList<ButtonImage> updateOnReleased = new ArrayList<>();
	public static ArrayList<ButtonImage> updateOnClicked = new ArrayList<>();
	public static ArrayList<ButtonImage> updateOnHover = new ArrayList<>();
	public static ArrayList<IKeyPressable> updateOnKeyPressed = new ArrayList<>();
	public static ArrayList<IKeyPressable> updateOnKeyReleased = new ArrayList<>();
	
	public static int windowHeight = 500;
	public static double windowRatio = 16.0/9.0;
	public static int windowWidth = (int)(windowRatio*windowHeight);
	public static int characterSize = (int)(120.0*windowHeight/500.0);
	public static final int frameRate = 60;
	public static final int frameSkip = 5;
	public static int frameCounter;
	public static final double frameTime = 1000/frameRate; //unit in milisecs
	
	public static final int roadSpeed = 10;
	
	public static int health;
	public static int scoreCompEngEss;
	public static int scoreDiscrete;
	public static int scoreProgMeth;
	
	public static boolean isFirstTime;
	public static boolean isGodMode;
	
	public static DrawImage BlackScreen;
	public static MainCharacterMale mainCharacter;
	
	public static DrawImage GameCredits;
	
	public static final int monsterPositionX = ProgressHolder.windowWidth-ProgressHolder.characterSize+10;
	public static final int monsterPositionY = ProgressHolder.windowHeight-ProgressHolder.characterSize-30;
	
	public static final KeyCode CharacterRunKeyCodeOne = KeyCode.Z;
	public static final KeyCode CharacterRunKeyCodeTwo = KeyCode.X;
	
	public static final KeyCode ObstacleDestroyedCodeOne = KeyCode.O;
	public static final KeyCode ObstacleDestroyedCodeTwo = KeyCode.P;
	public static final KeyCode ObstacleDestroyedCodeThree = KeyCode.L;
	public static final KeyCode ObstacleDestroyedCodeFour = KeyCode.K;
	
	public static boolean checkAvalible(String filePath) throws ResourceException {
		try {
			ClassLoader.getSystemResource(filePath).toString();
		} catch(Exception e) {
			throw new ResourceException(filePath);
		}
			
		return true;
	}
}
