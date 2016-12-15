package model;

import java.util.ArrayList;

import Utility.ProgressHolder;
import Utility.RenderableHolder;
import gui.GameScene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Obstacle extends Character{
	
	public static ArrayList<Image> Activity;
	public static ArrayList<Image> Final;
	public static ArrayList<Image> Lab;
	public static ArrayList<Image> Midterm;
	public static ArrayList<Image> Quiz;
	private String typeOfObstacle;
	public int health;
	private int fullHealth;
	private int speed;
	private int stateTrack; //Track state of key pressed
	private static ArrayList<KeyCode> codeList;
	private static KeyCode previousKey;
	
	/* Constructor for obstacle where
	 * x, y is the position on the screen with reference point on top-left of the image
	 * typeOfObstacle is refer to kind of obstacle to be set onto the object
	 */
	
	public Obstacle(int x, int y, String typeOfObstacle) throws ResourceException {
		super(x, y, 1);
		destroy = false;
		speed = ProgressHolder.roadSpeed/4;
		
		if(!isDefine(codeList)) {
			codeList = new ArrayList<KeyCode>() {
				{
					add(ProgressHolder.ObstacleDestroyedCodeOne);
					add(ProgressHolder.ObstacleDestroyedCodeTwo);
					add(ProgressHolder.ObstacleDestroyedCodeThree);
					add(ProgressHolder.ObstacleDestroyedCodeFour);
				}};
		}
		if(!isDefine(Activity)) {
			ProgressHolder.checkAvalible("resource/character/danger/object/activity.png");
			Activity = new ArrayList<Image>() {
				{
					add(new Image(ClassLoader.getSystemResource("resource/character/danger/object/activity.png").toString()));
				}};
		}
		if(!isDefine(Final)) {
			ProgressHolder.checkAvalible("resource/character/danger/object/final.png");
			Final = new ArrayList<Image>() {
				{
					add(new Image(ClassLoader.getSystemResource("resource/character/danger/object/final.png").toString()));
				}};
		}
		if(!isDefine(Lab)) {
			ProgressHolder.checkAvalible("resource/character/danger/object/lab.png");
			Lab = new ArrayList<Image>() {
				{
					add(new Image(ClassLoader.getSystemResource("resource/character/danger/object/lab.png").toString()));
				}};
		}
		if(!isDefine(Midterm)) {
			ProgressHolder.checkAvalible("resource/character/danger/object/midterm.png");
			Midterm = new ArrayList<Image>() {
				{
					add(new Image(ClassLoader.getSystemResource("resource/character/danger/object/midterm.png").toString()));
				}};
		}
		if(!isDefine(Quiz)) {
			ProgressHolder.checkAvalible("resource/character/danger/object/quiz.png");
			Quiz = new ArrayList<Image>() {
				{
					add(new Image(ClassLoader.getSystemResource("resource/character/danger/object/quiz.png").toString()));
				}};
		}
		
		this.stateTrack = 0;
		
		if(typeOfObstacle.equals("Activity")) {
			setUpImage(Activity);
			this.typeOfObstacle = "Activity";
			health = 30;
			fullHealth = 30;
		} else if(typeOfObstacle.equals("Final")) {
			setUpImage(Final);
			this.typeOfObstacle = "Final";
			health = 100;
			fullHealth = 100;
		} else if(typeOfObstacle.equals("Lab")) {
			setUpImage(Lab);
			this.typeOfObstacle = "Lab";
			health = 30;
			fullHealth = 30;
		} else if(typeOfObstacle.equals("Midterm")) {
			setUpImage(Midterm);
			this.typeOfObstacle = "Midterm";
			health = 60;
			fullHealth = 60;
		} else {
			setUpImage(Quiz);
			this.typeOfObstacle = "Quiz";
			health = 30;
			fullHealth = 30;
		}
		z = 1200;
	}
	
	//Method to decrease health of obstacle
	
	public void decreaseHealth(int amount) {
		if(amount < 0) amount = - amount;
		health -= amount;
	}
	
	//getter for typeOfObstacle
	
	public String getType() {
		return typeOfObstacle;
	}
	
	//Method to check if the given ArrayList had already define or not
	
	public <T> boolean isDefine(ArrayList<T> x) {
		boolean tmp = !((x == null) || (x.size() == 0));
		return tmp;
	}

	@Override
	public void myCallBackOnReleaseKeyBoard(KeyCode e) {}
	
	//Setter for speed
	
	public void setSpeed(int speed) {
		if(speed <0) speed = -speed;
		this.speed = speed;
	}
	
	//Reset state track (state of which key to be pressed next)
	
	public void resetStateTrack() {
		this.stateTrack = 0;
	}
	
	//Method hanlde movement of obstacle
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		if(Entity.isCollide(getX(), getY(), 2*getHeight(), getWidth(), ProgressHolder.mainCharacter.getX(), ProgressHolder.mainCharacter.getY(), ProgressHolder.mainCharacter.getHeight(), ProgressHolder.mainCharacter.getWidth())) {
			if(!destroy) {
				ProgressHolder.health -= 10;
				ProgressHolder.mainCharacter.gotHit(false, true);
			}
			destroy = true;
		};
		
		if(health <= 0 || x <= 0 || destroy) {
			if(RenderableHolder.instance.getEntities().contains(this)) {
				RenderableHolder.instance.getEntities().remove(this);
			}
			//Calculate score
			
			if(ProgressHolder.frameCounter <= 38*ProgressHolder.frameRate) { //Comp Eng Ess
				if(this.getType().equals("Activity")) {
					ProgressHolder.scoreCompEngEss += (int)((100 - this.getPercentHealth())*0.15);
				}
				else if(this.getType().equals("Quiz")) {
					ProgressHolder.scoreCompEngEss += (int)((100 - this.getPercentHealth())*0.20);
				}
			} else if(ProgressHolder.frameCounter <= 60*ProgressHolder.frameRate) { //Discrete
				if(this.getType().equals("Activity")) {
					ProgressHolder.scoreCompEngEss += (int)((100 - this.getPercentHealth())*0.15);
				}
				else if(this.getType().equals("Quiz")) {
					ProgressHolder.scoreCompEngEss += (int)((100 - this.getPercentHealth())*0.20);
				}
				else if(this.getType().equals("Midterm")) {
					ProgressHolder.scoreDiscrete += (int)((100 - this.getPercentHealth())*0.40);
				}
				else if(this.getType().equals("Final")) {
					ProgressHolder.scoreDiscrete += (int)((100 - this.getPercentHealth())*0.60);
				}
			} else { //Prog Meth
				if(this.getType().equals("Lab")) {
					ProgressHolder.scoreProgMeth += (int)((100 - this.getPercentHealth())*0.10);
				}
				else if(this.getType().equals("Final") || this.getType().equals("Midterm")) {
					ProgressHolder.scoreProgMeth += (int)((100 - this.getPercentHealth())*0.30);
				}
			}
		}
		
		//Move obstacle
		
		translateX(-speed);
	}
	
	public int getPercentHealth() { //unit in percent
		return (int)(((double)health/(double)fullHealth)*100);
	}
	
	@Override
	public void myCallBackOnPressedKeyBoard(KeyCode e) {
		
		if(GameScene.onScreenObstacle.size() > 0) {
			if(e == codeList.get(GameScene.onScreenObstacle.get(0).stateTrack)) {
				//System.out.println(GameScene.onScreenObstacle.size() + " " + (e != previousKey));
				if(GameScene.onScreenObstacle.size() > 0 && e != previousKey) {
					if(GameScene.onScreenObstacle.get(0) != null) {
						GameScene.onScreenObstacle.get(0).decreaseHealth(1);
					}
					if(GameScene.onScreenObstacle.get(0).getType().equals("Final") || GameScene.onScreenObstacle.get(0).getType().equals("Midterm")) {
						GameScene.onScreenObstacle.get(0).stateTrack = (GameScene.onScreenObstacle.get(0).stateTrack +1)%4;
					}
					else {
						GameScene.onScreenObstacle.get(0).stateTrack = (GameScene.onScreenObstacle.get(0).stateTrack +1)%2;
					}	
				}
				
			}
			
		}
		
		previousKey = e;
	}

}
